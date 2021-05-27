package testjpa;

import testjpa.Pojos.Categorias;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Genarogg
 */
public class TestJPA {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestJPAPU");
        
        EntityManager manager = factory.createEntityManager();
        
        TypedQuery <Categorias> listaCategorias = manager.createQuery("SELECT C FROM Categorias c", Categorias.class);
        
        listaCategorias.getResultList().stream()
                                       .forEach(
                                            (categoria) -> 
                                            {
                                                    System.out.println(categoria.getNombrecat());
                                            }
                                            );
    }
    
}
