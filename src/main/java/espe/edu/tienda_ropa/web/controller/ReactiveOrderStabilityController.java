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

    /**
     * Simula una dependencia externa real
     * PRUEBA TÉCNICA:
     * - Timeout: qué pasa si el servicio externo tarda demasiado
     * - Retry: cuántas veces el sistema intenta reconectarse
     * - Fallback: cómo responde el sistema si el servicio no está disponibl
     * VALIDACIÓN EN LA PRÁCTICA:
     * - En Postman se observa:
     *   - A veces responde normal
     *   - A veces reintenta
     *   - A veces entra en modo degradado
     * Esto demuestra tolerancia a fallos de dependencias externas.
     */
    @GetMapping("/external-check")
    public Mono<String> validateExternalService() {
        return stabilityService.validateExternalService();
    }
    /**
     * Prueba DEGRADACIÓN CONTROLADA.
     * Cuando el sistema detecta sobrecarga:
     * - No colapsa
     * - No se cae
     * - Reduce funcionalidad
     * - Mantiene operación mínima
     * VALIDACIÓN PRÁCTICA:
     * - Monto bajo → procesamiento normal
     * - Monto alto → respuesta degradada
     * Esto demuestra:
     * - Alta disponibilidad
     * - Continuidad operativa
     */
    @PostMapping("/order")
    public Mono<Map<String, Object>> processOrder(
            @RequestParam double amount) {

        return stabilityService.processOrderWithDegradation(amount);
    }

    /**
     * Prueba BACKPRESSURE
     * PROBLEMA REAL:
     * Sistemas reales colapsan cuando reciben más eventos
     * de los que pueden procesar.
     * SOLUCIÓN:
     * Backpressure controla el flujo y descarta eventos.
     * VALIDACIÓN:
     * - Se observa en consola:
     *   "Pedido descartado por sobrecarga"
     * Esto demuestra protección de memoria y estabilidad del sistema.
     */
    @GetMapping("/stream")
    public Flux<Map<String, Object>> orderStream() {
        return stabilityService.protectedOrderStream();
    }
}