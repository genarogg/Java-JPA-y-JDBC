/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjpa.proyectoempresa.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import testjpa.proyectoempresa.Pojo.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import testjpa.proyectoempresa.Pojo.Proyectos;
import testjpa.proyectoempresa.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Genarogg
 */
public class ProyectosJpaController implements Serializable {

    public ProyectosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyectos proyectos) {
        if (proyectos.getListaEmpleados() == null) {
            proyectos.setListaEmpleados(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedListaEmpleados = new ArrayList<Empleado>();
            for (Empleado listaEmpleadosEmpleadoToAttach : proyectos.getListaEmpleados()) {
                listaEmpleadosEmpleadoToAttach = em.getReference(listaEmpleadosEmpleadoToAttach.getClass(), listaEmpleadosEmpleadoToAttach.getId());
                attachedListaEmpleados.add(listaEmpleadosEmpleadoToAttach);
            }
            proyectos.setListaEmpleados(attachedListaEmpleados);
            em.persist(proyectos);
            for (Empleado listaEmpleadosEmpleado : proyectos.getListaEmpleados()) {
                listaEmpleadosEmpleado.getListaProyectos().add(proyectos);
                listaEmpleadosEmpleado = em.merge(listaEmpleadosEmpleado);
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyectos proyectos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos persistentProyectos = em.find(Proyectos.class, proyectos.getId());
            List<Empleado> listaEmpleadosOld = persistentProyectos.getListaEmpleados();
            List<Empleado> listaEmpleadosNew = proyectos.getListaEmpleados();
            List<Empleado> attachedListaEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado listaEmpleadosNewEmpleadoToAttach : listaEmpleadosNew) {
                listaEmpleadosNewEmpleadoToAttach = em.getReference(listaEmpleadosNewEmpleadoToAttach.getClass(), listaEmpleadosNewEmpleadoToAttach.getId());
                attachedListaEmpleadosNew.add(listaEmpleadosNewEmpleadoToAttach);
            }
            listaEmpleadosNew = attachedListaEmpleadosNew;
            proyectos.setListaEmpleados(listaEmpleadosNew);
            proyectos = em.merge(proyectos);
            for (Empleado listaEmpleadosOldEmpleado : listaEmpleadosOld) {
                if (!listaEmpleadosNew.contains(listaEmpleadosOldEmpleado)) {
                    listaEmpleadosOldEmpleado.getListaProyectos().remove(proyectos);
                    listaEmpleadosOldEmpleado = em.merge(listaEmpleadosOldEmpleado);
                }
            }
            for (Empleado listaEmpleadosNewEmpleado : listaEmpleadosNew) {
                if (!listaEmpleadosOld.contains(listaEmpleadosNewEmpleado)) {
                    listaEmpleadosNewEmpleado.getListaProyectos().add(proyectos);
                    listaEmpleadosNewEmpleado = em.merge(listaEmpleadosNewEmpleado);
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = proyectos.getId();
                if (findProyectos(id) == null) {
                    throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos proyectos;
            try {
                proyectos = em.getReference(Proyectos.class, id);
                proyectos.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> listaEmpleados = proyectos.getListaEmpleados();
            for (Empleado listaEmpleadosEmpleado : listaEmpleados) {
                listaEmpleadosEmpleado.getListaProyectos().remove(proyectos);
                listaEmpleadosEmpleado = em.merge(listaEmpleadosEmpleado);
            }
            em.remove(proyectos);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyectos> findProyectosEntities() {
        return findProyectosEntities(true, -1, -1);
    }

    public List<Proyectos> findProyectosEntities(int maxResults, int firstResult) {
        return findProyectosEntities(false, maxResults, firstResult);
    }

    private List<Proyectos> findProyectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyectos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }

    public Proyectos findProyectos(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyectos.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getProyectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyectos> rt = cq.from(Proyectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
}
