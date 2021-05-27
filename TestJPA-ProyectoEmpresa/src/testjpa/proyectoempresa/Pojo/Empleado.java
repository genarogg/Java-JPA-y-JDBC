package testjpa.proyectoempresa.Pojo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Genarogg
 */
@Entity
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private int telefono;
    @Column(name = "departamento_id")
    private String departamento_id;
    
    @ManyToOne
    @JoinColumn(name = "departamento")
    private Departamento departamentoVar;
    
    @OneToOne(mappedBy = "empleado")
    private Usuario usuario;
    
    @ManyToMany(mappedBy = "listaEmpleados")
    private List<Proyectos> listaProyectos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testjpa.proyectoempresa.Pojo.Empleado[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento_id(String departamento_id) {
        this.departamento_id = departamento_id;
    }

    public Departamento getDepartamentoVar() {
        return departamentoVar;
    }

    public void setDepartamentoVar(Departamento departamentoVar) {
        this.departamentoVar = departamentoVar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Proyectos> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyectos> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }
    
}
