package espe.edu.tienda_ropa.reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Servicio reactivo enfocado en la ESTABILIDAD del sistema.
 * Este servicio NO valida datos funcionales (precio, stock),
 * sino problemas de infraestructura y rendimiento.
 */
@Service
public class ReactiveOrderStabilityService {

    /**
     * Simula la comunicación con un servicio externo (pagos, inventario remoto).
     *
     * POSIBLES ERRORES:
     * - El servicio tarda demasiado (timeout)
     * - El servicio falla de forma intermitente
     *
     */
    public Mono<String> validateExternalService() {

        return Mono.fromCallable(() -> {

                    // Simula el tiempo de respuesta de un servicio externo
                    int delay = ThreadLocalRandom.current().nextInt(100, 1200);
                    System.out.println(" Tiempo de respuesta externo: " + delay + " ms");

                    // Error intencional por lentitud
                    if (delay > 900) {
                        throw new RuntimeException(
                                "Servicio externo lento | CAUSA: Alta latencia | " +
                                        "ACCIÓN: Intente nuevamente o contacte soporte"
                        );
                    }

                    return "Servicio externo respondió correctamente";
                })

                //  Tiempo máximo de espera permitido
                .timeout(Duration.ofMillis(800))

                // Reintentos automáticos ante fallos temporales
                .retry(2)

                //  Recuperación: el sistema sigue funcionando
                .onErrorResume(error ->
                        Mono.just(
                                "Servicio externo no disponible | " +
                                        "ACCIÓN: El sistema continúa en modo limitado"
                        )
                );
    }

    /**
     * Procesa un pedido aplicando DEGRADACIÓN CONTROLADA.
     *
     * CONCEPTO:
     * - Si el sistema está sobrecargado, se reduce funcionalidad
     * - Se evita una caída total
     *
     * ¿QUÉ DEBERÍA HACER EL USUARIO?
     * - Intentar más tarde
     * - Reducir el monto del pedido
     */
    public Mono<Map<String, Object>> processOrderWithDegradation(double amount) {

        return Mono.just(amount)

                // Validación básica
                .map(value -> {
                    if (value < 0) {
                        throw new IllegalArgumentException(
                                "Monto inválido | ACCIÓN: Ingrese un valor positivo"
                        );
                    }
                    return value;
                })

                // Simulación de carga del sistema
                .delayElement(Duration.ofMillis(
                        ThreadLocalRandom.current().nextInt(100, 1000)
                ))

                .map(value -> {

                    // Error intencional por sobrecarga
                    if (value > 30) {
                        throw new RuntimeException(
                                "Sistema en sobrecarga | CAUSA: Pedido pesado | " +
                                        "ACCIÓN: Reduzca el monto o intente más tarde"
                        );
                    }

                    return Map.<String, Object>of(
                            "status", "OK",
                            "amount", value,
                            "message", "Pedido procesado normalmente"
                    );
                })

                // 🟡 Degradación: respuesta parcial pero válida
                .onErrorResume(RuntimeException.class, error ->
                        Mono.just(Map.of(
                                "status", "DEGRADED",
                                "amount", amount,
                                "message",
                                "Pedido procesado en modo degradado | " +
                                        "Algunas validaciones fueron omitidas"
                        ))
                );
    }

    /**
     * Flujo reactivo protegido contra SOBRECARGA (Backpressure).
     *
     * ¿QUÉ PROBLEMA RESUELVE?
     * - Evita que el sistema se quede sin memoria
     * - Descarta pedidos cuando hay demasiados
     *
     * ¿QUÉ DEBERÍA HACER EL USUARIO?
     * - No enviar pedidos masivos
     * - Implementar colas o límites
     */
    public Flux<Map<String, Object>> protectedOrderStream() {

        return Flux.range(1, 20)

                // Simula llegada rápida de pedidos
                .delayElements(Duration.ofMillis(200))

                //  Protección ante exceso de pedidos
                .onBackpressureDrop(id ->
                        System.out.println("Pedido descartado por sobrecarga: " + id)
                )

                .map(id -> {
                    double value = ThreadLocalRandom.current().nextDouble(5, 40);

                    return Map.<String, Object>of(
                            "orderId", id,
                            "amount", Math.round(value * 100.0) / 100.0,
                            "status", "STREAMED"
                    );
                });
    }
}