/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Genarogg
 */
public class ConexionBD2 {
    Connection conn = null;
    //Statement st = null;
    PreparedStatement st = null;
    ResultSet rs = null; 
    
    public ConexionBD2(){
        try {
            Class.forName("org.postgresql.Driver");
            
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pedidos", "postgres", "321");
            /*
            st = conn.prepareStatement("select * from clientes where clienteid = ?");
            st.setInt(1, 1); //el segundo numero se basa en el parametro al que se va a adceder
            */
            
            st = conn.prepareStatement("update clientes set nombrecia=? where clienteid=?");
            st.setString(1, "Mi negocio2");
            st.setInt(2, 3);
            
            st.executeUpdate();
            /*
            rs = st.executeQuery();
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
            */
        
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
        new ConexionBD2();
    }

    
}
