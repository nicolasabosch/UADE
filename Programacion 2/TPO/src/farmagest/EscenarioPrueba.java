package farmagest;

import farmagest.controlador.FarmaGestController;
import farmagest.modelo.Lote;
import farmagest.modelo.Medicamento;
import farmagest.modelo.Pedido;

import java.time.LocalDate;

/**
 * EscenarioPrueba — Simulación integrada del sistema FarmaGest
 *
 * Valida los seis flujos principales del sistema en secuencia lógica:
 *   1. Ingreso de medicamentos, sucursales y lotes
 *   2. Despacho FEFO (siempre el lote más próximo a vencer)
 *   3. Generación automática de pedido por stock bajo umbral
 *   4. Búsqueda de sustituto terapéutico via BFS (nodo origen excluido)
 *   5. Procesamiento FIFO de pedido pendiente
 *   6. Purga de lotes vencidos
 *
 * Muestra cómo FarmaGestController coordina CatálogoMedicamentos,
 * Inventario, ColaDePedidos y RedDeSustitutos en cada operación.
 */
public class EscenarioPrueba {

    public static void main(String[] args) {

        FarmaGestController sistema = new FarmaGestController();
        System.out.println("=== FARMAGEST — ESCENARIO DE PRUEBA ===\n");

        // ── SETUP: Registrar sucursales ────────────────────────────────────
        sistema.registrarSucursal("SUC-PALERMO");
        sistema.registrarSucursal("SUC-BELGRANO");
        System.out.println("[SETUP] Sucursales registradas: SUC-PALERMO, SUC-BELGRANO");

        // ── SETUP: Registrar medicamentos en el catálogo ───────────────────
        // registrarMedicamento() los agrega al catálogo Y a la red de sustitutos
        sistema.registrarMedicamento("IBU-400", "Ibuprofeno 400mg",  "Ibuprofeno",   "Comprimidos x 20");
        sistema.registrarMedicamento("IBU-600", "Ibuprofeno 600mg",  "Ibuprofeno",   "Comprimidos x 20");
        sistema.registrarMedicamento("DICLOF",  "Diclofenac 50mg",   "Diclofenac",   "Comprimidos x 20");
        sistema.registrarMedicamento("AMOX-500","Amoxicilina 500mg", "Amoxicilina",  "Cápsulas x 12");
        sistema.registrarMedicamento("AMOX-750","Amoxicilina 750mg", "Amoxicilina",  "Comprimidos x 12");
        System.out.println("[SETUP] Medicamentos registrados: IBU-400, IBU-600, DICLOF, AMOX-500, AMOX-750");

        // ── SETUP: Configurar red de sustitutos (grafo no dirigido) ────────
        // IBU-400 <-> IBU-600 <-> DICLOF  (cadena antiinflamatoria)
        sistema.agregarSustitucion("IBU-400", "IBU-600");
        sistema.agregarSustitucion("IBU-600", "DICLOF");
        // AMOX-500 <-> AMOX-750
        sistema.agregarSustitucion("AMOX-500", "AMOX-750");
        System.out.println("[SETUP] Red de sustitutos configurada.");

        // ── SETUP: Configurar umbrales de reposición automática ────────────
        sistema.configurarUmbral("SUC-PALERMO", "IBU-400",  10);
        sistema.configurarUmbral("SUC-PALERMO", "AMOX-500",  5);

        // ── PRUEBA 1: Ingreso de lotes (Catálogo -> Inventario -> ColaDeLotes) ──
        System.out.println("\n--- PRUEBA 1: Ingreso de lotes ---");

        // IBU-400: 3 lotes con distintas fechas de vencimiento
        // El heap los ordena por fecha — L001 queda en la raíz (vence primero)
        sistema.agregarLote("SUC-PALERMO", "IBU-400", "L001", LocalDate.of(2025,  6, 30),  50, "ProveedorA");
        sistema.agregarLote("SUC-PALERMO", "IBU-400", "L002", LocalDate.of(2026,  3, 15),  30, "ProveedorB");
        sistema.agregarLote("SUC-PALERMO", "IBU-400", "L003", LocalDate.of(2026, 12,  1),  20, "ProveedorA");

        // AMOX-500: 1 lote con pocas unidades (cerca del umbral = 5)
        sistema.agregarLote("SUC-PALERMO", "AMOX-500", "L010", LocalDate.of(2025, 9, 1),    8, "ProveedorC");

        // DICLOF y AMOX-750: lotes para ser encontrados como sustitutos por BFS
        sistema.agregarLote("SUC-PALERMO", "DICLOF",   "L020", LocalDate.of(2026,  5, 15), 40, "ProveedorB");
        sistema.agregarLote("SUC-PALERMO", "AMOX-750", "L030", LocalDate.of(2026,  5,  1), 25, "ProveedorD");

        System.out.println("Stock IBU-400  en SUC-PALERMO: " + sistema.consultarStock("SUC-PALERMO", "IBU-400")  + " u.");
        System.out.println("Stock AMOX-500 en SUC-PALERMO: " + sistema.consultarStock("SUC-PALERMO", "AMOX-500") + " u.");

        // ── PRUEBA 2: Despacho FEFO (Controller -> Inventario -> ColaDeLotes) ──
        System.out.println("\n--- PRUEBA 2: Despacho FEFO ---");

        Lote proximo = sistema.proximoAVencer("SUC-PALERMO", "IBU-400");
        System.out.println("Próximo lote a vencer (ANTES): " + proximo);
        // Esperado: L001 vence 2025-06-30

        // Despacho parcial: L001 tiene 50u, despachamos 40 -> quedan 10 en L001
        boolean ok = sistema.despacharMedicamento("SUC-PALERMO", "IBU-400", 40);
        System.out.println("Despacho 40u IBU-400: " + (ok ? "EXITOSO" : "FALLIDO"));
        System.out.println("Stock IBU-400 tras despacho: " + sistema.consultarStock("SUC-PALERMO", "IBU-400") + " u.");
        // Esperado: 100 - 40 = 60 u.

        proximo = sistema.proximoAVencer("SUC-PALERMO", "IBU-400");
        System.out.println("Próximo lote (DESPUÉS del despacho): " + proximo);
        // Esperado: aún L001 con 10u restantes

        // Agotar L001: al extraer las últimas 10u, heap pasa a L002
        sistema.despacharMedicamento("SUC-PALERMO", "IBU-400", 10);
        proximo = sistema.proximoAVencer("SUC-PALERMO", "IBU-400");
        System.out.println("Próximo lote tras agotar L001: " + proximo);
        // Esperado: L002 vence 2026-03-15

        // ── PRUEBA 3: Pedido automático por umbral ─────────────────────────
        System.out.println("\n--- PRUEBA 3: Pedido automático por umbral ---");
        System.out.println("Pedidos pendientes antes: " + sistema.pedidosPendientes());

        // AMOX-500: stock=8, umbral=5. Despachamos 4u -> stock=4 <= umbral -> PED-1 generado
        sistema.despacharMedicamento("SUC-PALERMO", "AMOX-500", 4);
        System.out.println("Stock AMOX-500 tras despacho: " + sistema.consultarStock("SUC-PALERMO", "AMOX-500") + " u.");
        System.out.println("Pedidos pendientes después: " + sistema.pedidosPendientes());
        // Esperado: 1 pedido generado automáticamente

        // ── PRUEBA 4: BFS de sustitutos (Controller -> RedSustitutos -> Inventario) ──
        System.out.println("\n--- PRUEBA 4: Búsqueda de sustituto disponible ---");

        // Agotar IBU-400 para forzar búsqueda de sustituto
        int stockIbu = sistema.consultarStock("SUC-PALERMO", "IBU-400");
        sistema.despacharMedicamento("SUC-PALERMO", "IBU-400", stockIbu);
        System.out.println("Stock IBU-400 agotado: " + sistema.consultarStock("SUC-PALERMO", "IBU-400"));

        // Red: IBU-400 <-> IBU-600 <-> DICLOF
        // BFS desde IBU-400: visita IBU-600 (sin stock), luego DICLOF (40u) -> retorna DICLOF
        Medicamento sust1 = sistema.buscarSustituto("SUC-PALERMO", "IBU-400");
        System.out.println("Sustituto IBU-400: " + sust1);
        // Esperado: DICLOF

        // Para forzar la búsqueda de sustituto, primero agotamos AMOX-500.
        // (Si el medicamento aún tiene stock, el controlador no busca sustituto:
        //  no tiene sentido buscar un reemplazo de algo que está disponible.)
        int stockAmox = sistema.consultarStock("SUC-PALERMO", "AMOX-500");
        sistema.despacharMedicamento("SUC-PALERMO", "AMOX-500", stockAmox);
        System.out.println("Stock AMOX-500 agotado: " + sistema.consultarStock("SUC-PALERMO", "AMOX-500"));

        // Red: AMOX-500 <-> AMOX-750
        // BFS desde AMOX-500: el nodo origen se excluye; el primer vecino con stock
        // es AMOX-750 (25u) -> retorna AMOX-750 (corrige el caso de la Entrega 2,
        // donde AMOX-500 se devolvía como su propio sustituto).
        Medicamento sust2 = sistema.buscarSustituto("SUC-PALERMO", "AMOX-500");
        System.out.println("Sustituto AMOX-500: " + sust2);
        // Esperado: AMOX-750

        // ── PRUEBA 5: Procesamiento de pedido FIFO ─────────────────────────
        System.out.println("\n--- PRUEBA 5: Procesamiento de pedido ---");
        System.out.println("Pedidos en cola: " + sistema.pedidosPendientes());
        for (Pedido p : sistema.listarPedidosPendientes()) {
            System.out.println("  " + p);
        }

        // Extrae el primer pedido de la cola (FIFO), crea el lote L-REPO-001
        // y lo ingresa a la ColaDeLotes del medicamento correspondiente.
        sistema.procesarPedido("L-REPO-001", LocalDate.of(2026, 12, 31));
        System.out.println("Stock AMOX-500 tras procesar pedido: " +
                sistema.consultarStock("SUC-PALERMO", "AMOX-500") + " u.");
        // El primer pedido en cola (FIFO) repone AMOX-500: 0 + 100 = 100 u.

        // ── PRUEBA 6: Purga de lotes vencidos ─────────────────────────────
        System.out.println("\n--- PRUEBA 6: Purga de lotes vencidos ---");
        // DICLOF (L020) y AMOX-750 (L030) tienen lotes con fecha ya vencida.
        // eliminarVencidos() los detecta, los extrae de la cola y descuenta su stock.
        System.out.println("Stock DICLOF   antes de purga: " + sistema.consultarStock("SUC-PALERMO", "DICLOF")   + " u.");
        System.out.println("Stock AMOX-750 antes de purga: " + sistema.consultarStock("SUC-PALERMO", "AMOX-750") + " u.");
        sistema.purgarVencidos();
        System.out.println("Stock DICLOF   después de purga: " + sistema.consultarStock("SUC-PALERMO", "DICLOF")   + " u.");
        System.out.println("Stock AMOX-750 después de purga: " + sistema.consultarStock("SUC-PALERMO", "AMOX-750") + " u.");

        System.out.println("\n=== FIN DEL ESCENARIO DE PRUEBA ===");
    }
}
