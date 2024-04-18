package entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "gama_producto", schema = "jardineria", catalog = "")
public class GamaProductoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "gama", nullable = false, length = 50)
    private String gama;
    @Basic
    @Column(name = "descripcion_texto", nullable = true, length = -1)
    private String descripcionTexto;
    @OneToMany(mappedBy = "gamaProductoByGama")
    private Collection<ProductoEntity> productosByGama;

    public String getGama() {
        return gama;
    }

    public void setGama(String gama) {
        this.gama = gama;
    }

    public String getDescripcionTexto() {
        return descripcionTexto;
    }

    public void setDescripcionTexto(String descripcionTexto) {
        this.descripcionTexto = descripcionTexto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamaProductoEntity that = (GamaProductoEntity) o;

        if (gama != null ? !gama.equals(that.gama) : that.gama != null) return false;
        if (descripcionTexto != null ? !descripcionTexto.equals(that.descripcionTexto) : that.descripcionTexto != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gama != null ? gama.hashCode() : 0;
        result = 31 * result + (descripcionTexto != null ? descripcionTexto.hashCode() : 0);
        return result;
    }

    public Collection<ProductoEntity> getProductosByGama() {
        return productosByGama;
    }

    public void setProductosByGama(Collection<ProductoEntity> productosByGama) {
        this.productosByGama = productosByGama;
    }
}
