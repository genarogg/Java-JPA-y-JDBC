/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjpa.relaciones;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import testjpa.relaciones.pojos.Categorias;

/**
 *
 * @author Genarogg
 */
public class TestJPARelaciones {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("TestJPA-RelacionesPU");
        
        EntityManager manager = factory.createEntityManager();
        
        TypedQuery<Categorias> categoriaQuery = 
                manager.createNamedQuery("Categorias.findByCategoriaid", Categorias.class);
        
        categoriaQuery.setParameter("categoriaid", 100);
        
        Categorias categoria = categoriaQuery.getSingleResult();
        
        System.out.println("La categoira " + categoria.getNombrecat() + " tiene los productos: ");
        
        
        //Tiene Fallas
        categoria.getProductosCollection().stream()
                                .forEach((producto) -> System.out.println(producto.getDescripcion()));
    }
    
}
