package com.example.demo.hibController;

import com.example.demo.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class PaymentHibController {
    private EntityManagerFactory emf = null;

    public PaymentHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() { return emf.createEntityManager(); }

    public void createPayment(Payment payment){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(payment));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Payment> getPaymentList(){ return getPaymentList(true, -1, -1); };

    public List<Payment> getPaymentList(boolean all, int maxRes, int firstRes){
        EntityManager entityManager = getEntityManager();
        try{
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Payment.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if(!all){
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
        return null;
    }

    public void updatePayment(Payment payment){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            payment = entityManager.merge(payment);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deletePayment(int id){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Payment payment = null;
            try {
                payment = entityManager.getReference(Payment.class, id);
            } catch (Exception e){
                e.printStackTrace();
            }
            entityManager.remove(payment);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Payment getPaymentById(int id){
        for(Payment payment: getPaymentList()){
            if(payment.getId()==id){
                return payment;
            }
        }
        return null;
    }

    public Payment getPaymentByOrderId(int id){
        for(Payment payment: getPaymentList()){
            if(payment.getOrder().getId() == id){
                return payment;
            }
        }
        return null;
    }
}
