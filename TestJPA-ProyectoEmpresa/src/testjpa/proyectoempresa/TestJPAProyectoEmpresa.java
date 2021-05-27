package testjpa.proyectoempresa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import testjpa.proyectoempresa.Pojo.Departamento;
import testjpa.proyectoempresa.Pojo.Empleado;
import testjpa.proyectoempresa.Pojo.Usuario;
import testjpa.proyectoempresa.dao.DepartamentoJpaController;
import testjpa.proyectoempresa.dao.EmpleadoJpaController;
import testjpa.proyectoempresa.dao.UsuarioJpaController;

/**
 *
 * @author Genarogg
 */
public class TestJPAProyectoEmpresa {

    public static void main(String[] args) {
        EntityManagerFactory emf;
        emf = Persistence.
                createEntityManagerFactory("TestJPA-ProyectoEmpresaPU");
    
        try{
            Usuario user = new Usuario();
            user.setUsuario("Biochemistry43");
            user.setContraseña("123456");
            
            UsuarioJpaController contUsers = new UsuarioJpaController(emf);
            contUsers.create(user);
            
            Empleado empleado = new Empleado();
            empleado.setNombre("Ramiro");
            empleado.setTelefono(987654321);
            empleado.setUsuario(user);
            
            EmpleadoJpaController contEmpleado = new EmpleadoJpaController(emf);
            contEmpleado.create(empleado);
            
            List<Empleado> listaEmpleados = new ArrayList<>();
            listaEmpleados.add(empleado);
            
            Departamento departamento = new Departamento();
            departamento.setNombre("Java a profundidad");
            departamento.setTelefono("123456789");
            
            
            
            
            DepartamentoJpaController contDepartamentos = new DepartamentoJpaController(emf);
            contDepartamentos.create(departamento);
            
            emf.close();
            
            
        }
        catch(Exception e){
            System.out.println("Exeption");
            e.printStackTrace();
        }
    }     
    
}
