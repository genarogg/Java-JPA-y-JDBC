/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjpa.relaciones.pojos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Genarogg
 */
@Entity
@Table(name = "productos")
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p")
    , @NamedQuery(name = "Productos.findByProductoid", query = "SELECT p FROM Productos p WHERE p.productoid = :productoid")
    , @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Productos.findByPreciounit", query = "SELECT p FROM Productos p WHERE p.preciounit = :preciounit")
    , @NamedQuery(name = "Productos.findByExistencia", query = "SELECT p FROM Productos p WHERE p.existencia = :existencia")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCTOID")
    private Integer productoid;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "PRECIOUNIT")
    private long preciounit;
    @Basic(optional = false)
    @Column(name = "EXISTENCIA")
    private int existencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoid")
    private Collection<DetalleOrdenes> detalleOrdenesCollection;
    @JoinColumn(name = "CATEGORIAID", referencedColumnName = "CATEGORIAID")
    @ManyToOne(optional = false)
    private Categorias categoriaid;
    @JoinColumn(name = "PROVEEDORID", referencedColumnName = "PROVEEDORID")
    @ManyToOne(optional = false)
    private Proveedores proveedorid;

    public Productos() {
    }

    public Productos(Integer productoid) {
        this.productoid = productoid;
    }

    public Productos(Integer productoid, long preciounit, int existencia) {
        this.productoid = productoid;
        this.preciounit = preciounit;
        this.existencia = existencia;
    }

    public Integer getProductoid() {
        return productoid;
    }

    public void setProductoid(Integer productoid) {
        this.productoid = productoid;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getPreciounit() {
        return preciounit;
    }

    public void setPreciounit(long preciounit) {
        this.preciounit = preciounit;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public Collection<DetalleOrdenes> getDetalleOrdenesCollection() {
        return detalleOrdenesCollection;
    }

    public void setDetalleOrdenesCollection(Collection<DetalleOrdenes> detalleOrdenesCollection) {
        this.detalleOrdenesCollection = detalleOrdenesCollection;
    }

    public Categorias getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Categorias categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Proveedores getProveedorid() {
        return proveedorid;
    }

    public void setProveedorid(Proveedores proveedorid) {
        this.proveedorid = proveedorid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoid != null ? productoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.productoid == null && other.productoid != null) || (this.productoid != null && !this.productoid.equals(other.productoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.relaciones.pojos.Productos[ productoid=" + productoid + " ]";
    }
    
}
