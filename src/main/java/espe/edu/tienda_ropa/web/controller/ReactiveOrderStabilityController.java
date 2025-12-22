package espe.edu.tienda_ropa.web.controller;

import espe.edu.tienda_ropa.reactive.ReactiveOrderStabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador para probar estabilidad, degradación y resiliencia
 * usando herramientas como Postman.
 */
@RestController
@RequestMapping("/reactive/stability")
public class ReactiveOrderStabilityController {

    @Autowired
    private ReactiveOrderStabilityService stabilityService;

    // ================================
    // PROBAR TIMEOUT Y RETRY
    // ================================
    @GetMapping("/external-check")
    public Mono<String> validateExternalService() {
        return stabilityService.validateExternalService();
    }

    // ================================
    // PROBAR DEGRADACIÓN CONTROLADA
    // ================================
    @PostMapping("/order")
    public Mono<Map<String, Object>> processOrder(
            @RequestParam double amount) {

        return stabilityService.processOrderWithDegradation(amount);
    }

    // ================================
    // PROBAR BACKPRESSURE
    // ================================
    @GetMapping("/stream")
    public Flux<Map<String, Object>> orderStream() {
        return stabilityService.protectedOrderStream();
    }
}