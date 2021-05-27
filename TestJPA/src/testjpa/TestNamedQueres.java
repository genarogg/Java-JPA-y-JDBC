package testjpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import testjpa.Pojos.Categorias;

/**
 *
 * @author Genarogg
 */
public class TestNamedQueres {
    public static void main(String[] args) {
        EntityManagerFactory factory = 
                    Persistence.createEntityManagerFactory("TestJPAPU");
        
        EntityManager manager = factory.createEntityManager();
        
        TypedQuery<Categorias> findCategorias = 
                        manager.createNamedQuery("Categorias.findAll", Categorias.class);
        
        TypedQuery<Categorias> categoriaPorId = manager.
                                createNamedQuery("Categorias.findByCategoriaid", Categorias.class);
        
        categoriaPorId.setParameter("categoriaid", 100);
        
        Categorias catEncontrada = categoriaPorId.getSingleResult();
        
        List<Categorias> categorias = findCategorias.getResultList();
        
        for(Categorias categoria : categorias){
            System.out.println(categoria.getNombrecat());
        }
        
        System.out.println("Categoria encontrada: " + catEncontrada.getNombrecat());
    }
}
