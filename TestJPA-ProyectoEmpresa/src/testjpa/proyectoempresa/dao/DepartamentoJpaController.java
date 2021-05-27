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
import testjpa.proyectoempresa.Pojo.Departamento;
import testjpa.proyectoempresa.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Genarogg
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getEmpleados() == null) {
            departamento.setEmpleados(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedEmpleados = new ArrayList<Empleado>();
            for (Empleado empleadosEmpleadoToAttach : departamento.getEmpleados()) {
                empleadosEmpleadoToAttach = em.getReference(empleadosEmpleadoToAttach.getClass(), empleadosEmpleadoToAttach.getId());
                attachedEmpleados.add(empleadosEmpleadoToAttach);
            }
            departamento.setEmpleados(attachedEmpleados);
            em.persist(departamento);
            for (Empleado empleadosEmpleado : departamento.getEmpleados()) {
                Departamento oldDepartamentoVarOfEmpleadosEmpleado = empleadosEmpleado.getDepartamentoVar();
                empleadosEmpleado.setDepartamentoVar(departamento);
                empleadosEmpleado = em.merge(empleadosEmpleado);
                if (oldDepartamentoVarOfEmpleadosEmpleado != null) {
                    oldDepartamentoVarOfEmpleadosEmpleado.getEmpleados().remove(empleadosEmpleado);
                    oldDepartamentoVarOfEmpleadosEmpleado = em.merge(oldDepartamentoVarOfEmpleadosEmpleado);
                }
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            List<Empleado> empleadosOld = persistentDepartamento.getEmpleados();
            List<Empleado> empleadosNew = departamento.getEmpleados();
            List<Empleado> attachedEmpleadosNew = new ArrayList<Empleado>();
            for (Empleado empleadosNewEmpleadoToAttach : empleadosNew) {
                empleadosNewEmpleadoToAttach = em.getReference(empleadosNewEmpleadoToAttach.getClass(), empleadosNewEmpleadoToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewEmpleadoToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            departamento.setEmpleados(empleadosNew);
            departamento = em.merge(departamento);
            for (Empleado empleadosOldEmpleado : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleado)) {
                    empleadosOldEmpleado.setDepartamentoVar(null);
                    empleadosOldEmpleado = em.merge(empleadosOldEmpleado);
                }
            }
            for (Empleado empleadosNewEmpleado : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleado)) {
                    Departamento oldDepartamentoVarOfEmpleadosNewEmpleado = empleadosNewEmpleado.getDepartamentoVar();
                    empleadosNewEmpleado.setDepartamentoVar(departamento);
                    empleadosNewEmpleado = em.merge(empleadosNewEmpleado);
                    if (oldDepartamentoVarOfEmpleadosNewEmpleado != null && !oldDepartamentoVarOfEmpleadosNewEmpleado.equals(departamento)) {
                        oldDepartamentoVarOfEmpleadosNewEmpleado.getEmpleados().remove(empleadosNewEmpleado);
                        oldDepartamentoVarOfEmpleadosNewEmpleado = em.merge(oldDepartamentoVarOfEmpleadosNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> empleados = departamento.getEmpleados();
            for (Empleado empleadosEmpleado : empleados) {
                empleadosEmpleado.setDepartamentoVar(null);
                empleadosEmpleado = em.merge(empleadosEmpleado);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
}
