# Fincas API — laboratorio Spring Boot 3

Proyecto base de la Guía SENA ADSO. Está pensado para que puedas leer una petición completa y luego repetir el patrón con una entidad propia.

## Requisitos

- JDK 21 o superior.
- PostgreSQL 16 o superior en `localhost:5434`.
- API Spring Boot en `http://localhost:31026` (puerto registrado para esta guía).
- PowerShell 7.

## Arranque reproducible

1. Copia `.env.example` como `.env` y revisa usuario, contraseña y base `adso_fincas`.
2. Abre PowerShell en esta carpeta.
3. Ejecuta:

```powershell
.\mvnw spring-boot:run
```

El proyecto usa `spring-boot-devtools`. El IDE debe compilar automáticamente los `.java`; DevTools detecta los `.class` compilados y reinicia la aplicación. La consola debe mostrar `LiveReload server is running on port 35729`.

En desarrollo `app.security.enabled=false` para que puedas practicar CRUD con cURL sin fabricar un token. En un entorno protegido usa el perfil de producción, donde la cadena de seguridad exige autenticación JWT.

## Recorrido recomendado

1. `GET /api/hello` confirma que la aplicación vive.
2. Revisa `controller/FincaController.java` y localiza las cinco rutas.
3. Sigue `FincaService` → `FincaRepository` → `Finca` → tabla `fincas`.
4. Prueba el CRUD en `requests.http`.
5. Provoca 400, 404 y 422; después revisa `GlobalExceptionHandler`.
6. Repite el patrón para `Producto` usando `..\generar-crud.ps1` desde la raíz de la guía.

## Contrato de Fincas

| Método | Ruta | Resultado |
|---|---|---|
| GET | `/api/fincas` | Lista y 200 |
| GET | `/api/fincas/{id}` | DTO y 200; si no existe, 404 |
| POST | `/api/fincas` | DTO creado y 201 |
| PUT | `/api/fincas/{id}` | DTO actualizado y 200 |
| DELETE | `/api/fincas/{id}` | Sin cuerpo y 204 |

## Asociaciones y reglas

`FincaCultivo` modela la relación N:M como una entidad intermedia. La asociación guarda `areaSembradaHa`, `fechaSiembra`, `temporada` y `estado`; si el área supera las hectáreas de la finca, el Service lanza `BusinessException` y la API responde 422.

## Calidad

```powershell
.\mvnw test
```

Los tests del proyecto muestran dos niveles: unitario del Service con Mockito y web del Controller con MockMvc.
