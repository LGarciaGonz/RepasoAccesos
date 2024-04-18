package entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "pedido", schema = "jardineria", catalog = "")
public class PedidoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo_pedido", nullable = false)
    private int codigoPedido;
    @Basic
    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;
    @Basic
    @Column(name = "codigo_cliente", nullable = false)
    private int codigoCliente;
    @ManyToOne
    @JoinColumn(name = "codigo_cliente", referencedColumnName = "codigo", nullable = false, insertable = false, updatable = false)
    private ClienteEntity clienteByCodigoCliente;

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoEntity that = (PedidoEntity) o;

        if (codigoPedido != that.codigoPedido) return false;
        if (codigoCliente != that.codigoCliente) return false;
        if (fechaPedido != null ? !fechaPedido.equals(that.fechaPedido) : that.fechaPedido != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoPedido;
        result = 31 * result + (fechaPedido != null ? fechaPedido.hashCode() : 0);
        result = 31 * result + codigoCliente;
        return result;
    }

    public ClienteEntity getClienteByCodigoCliente() {
        return clienteByCodigoCliente;
    }

    public void setClienteByCodigoCliente(ClienteEntity clienteByCodigoCliente) {
        this.clienteByCodigoCliente = clienteByCodigoCliente;
    }
}
