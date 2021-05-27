package testjpa;

import testjpa.Pojos.Productos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Genarogg
 */
public class SelectJPQL {
    public static void main(String[] args) {
        EntityManagerFactory factory = 
                Persistence.createEntityManagerFactory("TestJPAPU");
        
        EntityManager manager = factory.createEntityManager();
        
        String jpql = "select p from Productos p";
        
       
        
        TypedQuery <Productos> query = manager.createQuery(jpql, Productos.class);
        
        query.getResultList().stream()
                             .forEach(producto -> System.out.println(producto.getDescripcion()));
        
        
        /*Query query = manager.createQuery(jpql);
        query.getResultList().stream()
                             .forEach(producto -> System.out.println(((Productos)producto).getDescripcion()));
        */ 
    }
}
