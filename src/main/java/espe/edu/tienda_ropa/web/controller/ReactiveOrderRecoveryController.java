package espe.edu.tienda_ropa.web.controller;

import espe.edu.tienda_ropa.reactive.ReactiveOrderRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador reactivo para:
 * - Probar manejo avanzado de errores
 * - Simular fallos controlados
 * - Ver recuperación del flujo en tiempo real
 *
 * USO:
 * Este controlador está diseñado para ser probado desde Postman
 * y observar el comportamiento del sistema ante errores.
 */
@RestController
@RequestMapping("/reactive/recovery")
public class ReactiveOrderRecoveryController {

    @Autowired
    private ReactiveOrderRecoveryService recoveryService;
    /**
     * Permite probar validaciones técnicas:
     * - Precio inválido
     * - Stock negativo
     * - Stock insuficiente
     * - Pedido sospechoso por monto
     *
     * EJEMPLO POSTMAN:
     * POST http://localhost:8080/reactive/recovery/order?price=5&quantity=2&stock=10
     */
    @PostMapping("/order")
    public Mono<String> processOrderWithRecovery(
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam int stock) {

        return recoveryService.handleOrderWithRecovery(price, quantity, stock)
                .doOnSubscribe(s -> System.out.println("Solicitud recibida desde el cliente"))
                .doOnSuccess(response ->
                        System.out.println("Respuesta enviada al cliente: " + response)
                );
    }


    /**
     * Simula múltiples pedidos donde algunos fallan intencionalmente.
     * El flujo NO se rompe, sino que se recupera con pedidos alternativos.
     *
     * EJEMPLO POSTMAN:
     * GET http://localhost:8080/reactive/recovery/orders/flow
     */
    @GetMapping("/orders/flow")
    public Flux<Map<String, Object>> simulateOrderFlow() {

        return recoveryService.simulateOrderFlowWithRecovery()
                .doOnSubscribe(s ->
                        System.out.println("Iniciando simulación de flujo reactivo")
                )
                .doOnNext(order ->
                        System.out.println("Evento emitido: " + order)
                )
                .doOnComplete(() ->
                        System.out.println("Flujo reactivo finalizado correctamente")
                );
    }
}