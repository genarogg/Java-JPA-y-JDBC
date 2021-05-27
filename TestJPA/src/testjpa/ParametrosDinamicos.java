package testjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import testjpa.Pojos.Productos;

/**
 *
 * @author Genarogg
 */
public class ParametrosDinamicos {
    public static void main(String[] args) {
        EntityManagerFactory factory = 
                Persistence.createEntityManagerFactory("TestJPAPU");
        
        EntityManager manager = factory.createEntityManager();
        
        //String query = " select a from Productos a where a.productoid = ?1";
        
        String query = " select a from Productos a where a.productoid = :idProd";
        
        TypedQuery<Productos> prods = 
                        manager.createQuery(query, Productos.class);
        
        prods.setParameter("idProd", 5);
                
        prods.getResultList().stream()
                             .forEach(pro -> System.out.println(pro.getDescripcion()));
    }
}
