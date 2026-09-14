package co.sena.adso.fincasapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of(
            "mensaje", "Hola desde el Monolito de Spring Boot 3",
            "colegio", "SENA Regional Santander",
            "programa", "ADSO 2026"
        );
    }
}
