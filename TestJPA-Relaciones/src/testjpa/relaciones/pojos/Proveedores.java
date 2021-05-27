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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Genarogg
 */
@Entity
@Table(name = "proveedores")
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p")
    , @NamedQuery(name = "Proveedores.findByProveedorid", query = "SELECT p FROM Proveedores p WHERE p.proveedorid = :proveedorid")
    , @NamedQuery(name = "Proveedores.findByNombreprov", query = "SELECT p FROM Proveedores p WHERE p.nombreprov = :nombreprov")
    , @NamedQuery(name = "Proveedores.findByContacto", query = "SELECT p FROM Proveedores p WHERE p.contacto = :contacto")
    , @NamedQuery(name = "Proveedores.findByCeluprov", query = "SELECT p FROM Proveedores p WHERE p.celuprov = :celuprov")
    , @NamedQuery(name = "Proveedores.findByFijoprov", query = "SELECT p FROM Proveedores p WHERE p.fijoprov = :fijoprov")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PROVEEDORID")
    private Integer proveedorid;
    @Basic(optional = false)
    @Column(name = "NOMBREPROV")
    private String nombreprov;
    @Basic(optional = false)
    @Column(name = "CONTACTO")
    private String contacto;
    @Column(name = "CELUPROV")
    private String celuprov;
    @Column(name = "FIJOPROV")
    private String fijoprov;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedorid")
    private Collection<Productos> productosCollection;

    public Proveedores() {
    }

    public Proveedores(Integer proveedorid) {
        this.proveedorid = proveedorid;
    }

    public Proveedores(Integer proveedorid, String nombreprov, String contacto) {
        this.proveedorid = proveedorid;
        this.nombreprov = nombreprov;
        this.contacto = contacto;
    }

    public Integer getProveedorid() {
        return proveedorid;
    }

    public void setProveedorid(Integer proveedorid) {
        this.proveedorid = proveedorid;
    }

    public String getNombreprov() {
        return nombreprov;
    }

    public void setNombreprov(String nombreprov) {
        this.nombreprov = nombreprov;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCeluprov() {
        return celuprov;
    }

    public void setCeluprov(String celuprov) {
        this.celuprov = celuprov;
    }

    public String getFijoprov() {
        return fijoprov;
    }

    public void setFijoprov(String fijoprov) {
        this.fijoprov = fijoprov;
    }

    public Collection<Productos> getProductosCollection() {
        return productosCollection;
    }

    public void setProductosCollection(Collection<Productos> productosCollection) {
        this.productosCollection = productosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorid != null ? proveedorid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.proveedorid == null && other.proveedorid != null) || (this.proveedorid != null && !this.proveedorid.equals(other.proveedorid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.relaciones.pojos.Proveedores[ proveedorid=" + proveedorid + " ]";
    }
    
}
