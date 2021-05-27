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
import javax.persistence.FetchType;
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
@Table(name = "categorias")
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c")
    , @NamedQuery(name = "Categorias.findByCategoriaid", query = "SELECT c FROM Categorias c WHERE c.categoriaid = :categoriaid")
    , @NamedQuery(name = "Categorias.findByNombrecat", query = "SELECT c FROM Categorias c WHERE c.nombrecat = :nombrecat")})
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CATEGORIAID")
    private Integer categoriaid;
    @Basic(optional = false)
    @Column(name = "NOMBRECAT")
    private String nombrecat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaid", fetch = FetchType.EAGER)
    private Collection<Productos> productosCollection;

    public Categorias() {
    }

    public Categorias(Integer categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Categorias(Integer categoriaid, String nombrecat) {
        this.categoriaid = categoriaid;
        this.nombrecat = nombrecat;
    }

    public Integer getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Integer categoriaid) {
        this.categoriaid = categoriaid;
    }

    public String getNombrecat() {
        return nombrecat;
    }

    public void setNombrecat(String nombrecat) {
        this.nombrecat = nombrecat;
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
        hash += (categoriaid != null ? categoriaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.categoriaid == null && other.categoriaid != null) || (this.categoriaid != null && !this.categoriaid.equals(other.categoriaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.relaciones.pojos.Categorias[ categoriaid=" + categoriaid + " ]";
    }
    
}
