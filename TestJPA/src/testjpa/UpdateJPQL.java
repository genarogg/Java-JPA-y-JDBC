package testjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Genarogg
 */
public class UpdateJPQL {
    public static void main(String[] args) {
        EntityManagerFactory factory = 
                Persistence.createEntityManagerFactory("TestJPAPU");
        EntityManager manager = factory.createEntityManager();
        
        String query = "update Productos p set p.descripcion = 'cambio desde JPA' where p.productoid = 1";
        
        Query tq = manager.createQuery(query);
        
        manager.getTransaction().begin();
        int filasasAfectadas = tq.executeUpdate();
        
        manager.getTransaction().commit();
        
    }
}
