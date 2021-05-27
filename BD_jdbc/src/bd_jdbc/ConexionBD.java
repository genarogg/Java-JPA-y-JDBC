package bd_jdbc;
import java.sql.DriverManager; //para espesificar el tipo de base de datos 
import java.sql.Connection; //Permite la conexion entre java y la base de datos
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author Genarogg
 */
public class ConexionBD {
    Connection conn = null;
    //Statement st = null;
    Statement st = null;
    ResultSet rs = null; 
    
    public ConexionBD(){
        try {
            Class.forName("org.postgresql.Driver");
            
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pedidos", "postgres", "321");
            st = conn.createStatement();
            
            
            //st.executeUpdate("insert into clientes(clienteid, cedula_ruc, nombrecia, nombrecontacto, direccioncli) "
            //                + "values (12, '123456789','MI NEGOCIO','VERONICA VELA', 'Domicilio conocido')");            
            
            //st.executeUpdate("update clientes set nombrecia = 'KODIKAS' where clienteid = 10"); //Actualizar registros de una base de datos
            //st.executeUpdate("delete from clientes where clienteid = 11");// eliminar el registro de una base de datos
            
            
            rs = st.executeQuery("Select * from clientes");//LECTOR DE LA BASE DE DATOS
            while(rs.next()){
                int id = rs.getInt(1);
                String cedula = rs.getString(2);
                String nombrecia = rs.getString(3);
                String nombreContacto = rs.getString(4);
                String direccioncli = rs.getString(5);
                
                System.out.println("id: " + id);
                System.out.println("Cedula: " + cedula);
                System.out.println("Nombrecia: " + nombrecia);
                System.out.println("nombre contacto: " + nombreContacto);
                System.out.println("direccion Cliente: " + direccioncli);
            }
            
        
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("ERROR EN LA CONEXION CON LA BASE DE DATOS (DIVER)");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("ERROR EN LA CONEXION CON LA BASE DE DATOS (URL-USUARIO-CONTRASEÑA) ");
        }
        finally{
            
        }
    }
    
    public static void main(String[] args) {
        new ConexionBD();
    }

    
    
}
