/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjpa.relaciones.pojos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Genarogg
 */
@Entity
@Table(name = "empleados")
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByEmpleadoid", query = "SELECT e FROM Empleados e WHERE e.empleadoid = :empleadoid")
    , @NamedQuery(name = "Empleados.findByNombre", query = "SELECT e FROM Empleados e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Empleados.findByApellido", query = "SELECT e FROM Empleados e WHERE e.apellido = :apellido")
    , @NamedQuery(name = "Empleados.findByFechaNac", query = "SELECT e FROM Empleados e WHERE e.fechaNac = :fechaNac")
    , @NamedQuery(name = "Empleados.findByExtension", query = "SELECT e FROM Empleados e WHERE e.extension = :extension")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EMPLEADOID")
    private Integer empleadoid;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "FECHA_NAC")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "EXTENSION")
    private Integer extension;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadoid")
    private Collection<Ordenes> ordenesCollection;
    @OneToMany(mappedBy = "reportaA")
    private Collection<Empleados> empleadosCollection;
    @JoinColumn(name = "REPORTA_A", referencedColumnName = "EMPLEADOID")
    @ManyToOne
    private Empleados reportaA;

    public Empleados() {
    }

    public Empleados(Integer empleadoid) {
        this.empleadoid = empleadoid;
    }

    public Integer getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Integer empleadoid) {
        this.empleadoid = empleadoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public Collection<Ordenes> getOrdenesCollection() {
        return ordenesCollection;
    }

    public void setOrdenesCollection(Collection<Ordenes> ordenesCollection) {
        this.ordenesCollection = ordenesCollection;
    }

    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    public Empleados getReportaA() {
        return reportaA;
    }

    public void setReportaA(Empleados reportaA) {
        this.reportaA = reportaA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleadoid != null ? empleadoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.empleadoid == null && other.empleadoid != null) || (this.empleadoid != null && !this.empleadoid.equals(other.empleadoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.relaciones.pojos.Empleados[ empleadoid=" + empleadoid + " ]";
    }
    
}
