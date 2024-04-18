package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "producto", schema = "jardineria", catalog = "")
public class ProductoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo_producto", nullable = false, length = 15)
    private String codigoProducto;
    @Basic
    @Column(name = "nombre", nullable = false, length = 70)
    private String nombre;
    @Basic
    @Column(name = "gama", nullable = false, length = 50)
    private String gama;
    @Basic
    @Column(name = "precio_venta", nullable = false, precision = 2)
    private double precioVenta;
    @ManyToOne
    @JoinColumn(name = "gama", referencedColumnName = "gama", nullable = false, insertable = false, updatable = false)
    private GamaProductoEntity gamaProductoByGama;

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGama() {
        return gama;
    }

    public void setGama(String gama) {
        this.gama = gama;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoEntity that = (ProductoEntity) o;

        if (Double.compare(precioVenta, that.precioVenta) != 0) return false;
        if (codigoProducto != null ? !codigoProducto.equals(that.codigoProducto) : that.codigoProducto != null)
            return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (gama != null ? !gama.equals(that.gama) : that.gama != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = codigoProducto != null ? codigoProducto.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (gama != null ? gama.hashCode() : 0);
        temp = Double.doubleToLongBits(precioVenta);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public GamaProductoEntity getGamaProductoByGama() {
        return gamaProductoByGama;
    }

    public void setGamaProductoByGama(GamaProductoEntity gamaProductoByGama) {
        this.gamaProductoByGama = gamaProductoByGama;
    }
}
