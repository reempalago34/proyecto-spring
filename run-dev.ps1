# Script de inicio para desarrollo - fincas-api-spring-boot
# Carga variables desde el archivo .env y arranca la aplicación con Maven Wrapper

Set-Location $PSScriptRoot

$AppPort = 31026
$portLoaderPath = Join-Path $env:USERPROFILE "Documents\Aplicaciones\_infrastructure\devbraind\config\ports-loader.ps1"
if (Test-Path -LiteralPath $portLoaderPath) {
    . $portLoaderPath
    try { $AppPort = [int](Get-Port "guia-spring-api") } catch { Write-Host "⚠️  No se encontró guia-spring-api; usando $AppPort." -ForegroundColor Yellow }
}

$EnvFile = Join-Path $PSScriptRoot ".env"

if (Test-Path $EnvFile) {
    Write-Host "🔑 Cargando variables de entorno desde .env..." -ForegroundColor Cyan
    Get-Content $EnvFile | ForEach-Object {
        $line = $_.Trim()
        if ($line -and -not $line.StartsWith("#") -and $line.Contains("=")) {
            $key, $value = $line.Split("=", 2)
            $key = $key.Trim()
            $value = $value.Trim()
            
            # Remover comillas si las hay
            if ($value.StartsWith('"') -and $value.EndsWith('"')) { $value = $value.Substring(1, $value.Length - 2) }
            if ($value.StartsWith("'") -and $value.EndsWith("'")) { $value = $value.Substring(1, $value.Length - 2) }
            
            Set-Item -Path "env:$key" -Value $value
            [System.Environment]::SetEnvironmentVariable($key, $value, [System.EnvironmentVariableTarget]::Process)
        }
    }
} else {
    Write-Host "⚠️  Archivo .env no encontrado. Asegúrate de crearlo a partir de .env.example" -ForegroundColor Yellow
}

Set-Item -Path "env:SERVER_PORT" -Value $AppPort

Write-Host "☕ Iniciando API Spring Boot en http://localhost:$AppPort..." -ForegroundColor Green
.\mvnw.cmd spring-boot:run
