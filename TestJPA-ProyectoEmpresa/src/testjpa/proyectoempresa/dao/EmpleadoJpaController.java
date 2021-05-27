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
import testjpa.proyectoempresa.Pojo.Departamento;
import testjpa.proyectoempresa.Pojo.Usuario;
import testjpa.proyectoempresa.Pojo.Proyectos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import testjpa.proyectoempresa.Pojo.Empleado;
import testjpa.proyectoempresa.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Genarogg
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getListaProyectos() == null) {
            empleado.setListaProyectos(new ArrayList<Proyectos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamentoVar = empleado.getDepartamentoVar();
            if (departamentoVar != null) {
                departamentoVar = em.getReference(departamentoVar.getClass(), departamentoVar.getId());
                empleado.setDepartamentoVar(departamentoVar);
            }
            Usuario usuario = empleado.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                empleado.setUsuario(usuario);
            }
            List<Proyectos> attachedListaProyectos = new ArrayList<Proyectos>();
            for (Proyectos listaProyectosProyectosToAttach : empleado.getListaProyectos()) {
                listaProyectosProyectosToAttach = em.getReference(listaProyectosProyectosToAttach.getClass(), listaProyectosProyectosToAttach.getId());
                attachedListaProyectos.add(listaProyectosProyectosToAttach);
            }
            empleado.setListaProyectos(attachedListaProyectos);
            em.persist(empleado);
            if (departamentoVar != null) {
                departamentoVar.getEmpleados().add(empleado);
                departamentoVar = em.merge(departamentoVar);
            }
            if (usuario != null) {
                Empleado oldEmpleadoOfUsuario = usuario.getEmpleado();
                if (oldEmpleadoOfUsuario != null) {
                    oldEmpleadoOfUsuario.setUsuario(null);
                    oldEmpleadoOfUsuario = em.merge(oldEmpleadoOfUsuario);
                }
                usuario.setEmpleado(empleado);
                usuario = em.merge(usuario);
            }
            for (Proyectos listaProyectosProyectos : empleado.getListaProyectos()) {
                listaProyectosProyectos.getListaEmpleados().add(empleado);
                listaProyectosProyectos = em.merge(listaProyectosProyectos);
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Departamento departamentoVarOld = persistentEmpleado.getDepartamentoVar();
            Departamento departamentoVarNew = empleado.getDepartamentoVar();
            Usuario usuarioOld = persistentEmpleado.getUsuario();
            Usuario usuarioNew = empleado.getUsuario();
            List<Proyectos> listaProyectosOld = persistentEmpleado.getListaProyectos();
            List<Proyectos> listaProyectosNew = empleado.getListaProyectos();
            if (departamentoVarNew != null) {
                departamentoVarNew = em.getReference(departamentoVarNew.getClass(), departamentoVarNew.getId());
                empleado.setDepartamentoVar(departamentoVarNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                empleado.setUsuario(usuarioNew);
            }
            List<Proyectos> attachedListaProyectosNew = new ArrayList<Proyectos>();
            for (Proyectos listaProyectosNewProyectosToAttach : listaProyectosNew) {
                listaProyectosNewProyectosToAttach = em.getReference(listaProyectosNewProyectosToAttach.getClass(), listaProyectosNewProyectosToAttach.getId());
                attachedListaProyectosNew.add(listaProyectosNewProyectosToAttach);
            }
            listaProyectosNew = attachedListaProyectosNew;
            empleado.setListaProyectos(listaProyectosNew);
            empleado = em.merge(empleado);
            if (departamentoVarOld != null && !departamentoVarOld.equals(departamentoVarNew)) {
                departamentoVarOld.getEmpleados().remove(empleado);
                departamentoVarOld = em.merge(departamentoVarOld);
            }
            if (departamentoVarNew != null && !departamentoVarNew.equals(departamentoVarOld)) {
                departamentoVarNew.getEmpleados().add(empleado);
                departamentoVarNew = em.merge(departamentoVarNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setEmpleado(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Empleado oldEmpleadoOfUsuario = usuarioNew.getEmpleado();
                if (oldEmpleadoOfUsuario != null) {
                    oldEmpleadoOfUsuario.setUsuario(null);
                    oldEmpleadoOfUsuario = em.merge(oldEmpleadoOfUsuario);
                }
                usuarioNew.setEmpleado(empleado);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Proyectos listaProyectosOldProyectos : listaProyectosOld) {
                if (!listaProyectosNew.contains(listaProyectosOldProyectos)) {
                    listaProyectosOldProyectos.getListaEmpleados().remove(empleado);
                    listaProyectosOldProyectos = em.merge(listaProyectosOldProyectos);
                }
            }
            for (Proyectos listaProyectosNewProyectos : listaProyectosNew) {
                if (!listaProyectosOld.contains(listaProyectosNewProyectos)) {
                    listaProyectosNewProyectos.getListaEmpleados().add(empleado);
                    listaProyectosNewProyectos = em.merge(listaProyectosNewProyectos);
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Departamento departamentoVar = empleado.getDepartamentoVar();
            if (departamentoVar != null) {
                departamentoVar.getEmpleados().remove(empleado);
                departamentoVar = em.merge(departamentoVar);
            }
            Usuario usuario = empleado.getUsuario();
            if (usuario != null) {
                usuario.setEmpleado(null);
                usuario = em.merge(usuario);
            }
            List<Proyectos> listaProyectos = empleado.getListaProyectos();
            for (Proyectos listaProyectosProyectos : listaProyectos) {
                listaProyectosProyectos.getListaEmpleados().remove(empleado);
                listaProyectosProyectos = em.merge(listaProyectosProyectos);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
}
