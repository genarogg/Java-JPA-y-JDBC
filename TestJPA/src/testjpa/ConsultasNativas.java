package testjpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import testjpa.Pojos.Productos;

/**
 *
 * @author Genarogg
 */
public class ConsultasNativas {
    public static void main(String[] args) {
        EntityManagerFactory factory = 
                Persistence.createEntityManagerFactory("TestJPAPU");
        
        EntityManager manager = factory.createEntityManager();
        
        String sql = "SELECT * FROM productos";
        
        Query q = manager.createNativeQuery(sql, Productos.class);
        
        List<Productos> productos = q.getResultList();
        
        for(Productos producto : productos){
            System.out.println(producto.getDescripcion());
        }
        
    }
}
