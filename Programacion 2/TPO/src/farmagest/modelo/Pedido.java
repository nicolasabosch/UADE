package farmagest.modelo;

import java.time.LocalDateTime;

/**
 * Pedido
 * Representa una orden de reposición generada automáticamente
 * cuando el stock de un medicamento en una sucursal cae por debajo
 * del umbral mínimo configurado, o manualmente por el operador.
 */
public class Pedido {

    private final String idPedido;
    private final String sucursalId;
    private final String medicamentoId;
    private final int cantidadSolicitada;
    private final String proveedor;
    private final LocalDateTime fechaGeneracion;

    /**
     * Precondición: idPedido no nulo, sucursalId no nulo,
     * medicamentoId no nulo, cantidadSolicitada > 0.
     */
    public Pedido(String idPedido, String sucursalId, String medicamentoId,
                  int cantidadSolicitada, String proveedor) {
        if (idPedido == null || idPedido.isBlank()) throw new IllegalArgumentException("El ID del pedido no puede ser nulo.");
        if (sucursalId == null) throw new IllegalArgumentException("La sucursal no puede ser nula.");
        if (medicamentoId == null) throw new IllegalArgumentException("El medicamento no puede ser nulo.");
        if (cantidadSolicitada <= 0) throw new IllegalArgumentException("La cantidad solicitada debe ser mayor a 0.");
        this.idPedido = idPedido;
        this.sucursalId = sucursalId;
        this.medicamentoId = medicamentoId;
        this.cantidadSolicitada = cantidadSolicitada;
        this.proveedor = (proveedor != null) ? proveedor : "PROVEEDOR_GENERICO";
        this.fechaGeneracion = LocalDateTime.now();
    }

    public String getIdPedido() { return idPedido; }
    public String getSucursalId() { return sucursalId; }
    public String getMedicamentoId() { return medicamentoId; }
    public int getCantidadSolicitada() { return cantidadSolicitada; }
    public String getProveedor() { return proveedor; }
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }

    @Override
    public String toString() {
        return String.format("Pedido[id=%s, sucursal=%s, medicamento=%s, cantidad=%d, proveedor=%s]",
                idPedido, sucursalId, medicamentoId, cantidadSolicitada, proveedor);
    }
}
