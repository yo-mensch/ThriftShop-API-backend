package com.example.demo.hibController;

import com.example.demo.model.ShoppingCart;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ShoppingCartHibController {
    private EntityManagerFactory emf = null;

    public ShoppingCartHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createShoppingCart(ShoppingCart shoppingCart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(shoppingCart));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ShoppingCart> getShoppingCartsList(){
        return getShoppingCartsList(true, -1, -1);
    }

    public List<ShoppingCart> getShoppingCartsList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(ShoppingCart.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return null;
    }

    public ShoppingCart getCartByUserId(int userId){
        for(ShoppingCart shoppingCart: getShoppingCartsList()){
            if(shoppingCart.getOwner().getId()==userId) return shoppingCart;
        }
        return null;
    }

    public ShoppingCart getCartById(int id){
        for(ShoppingCart shoppingCart: getShoppingCartsList()){
            if(shoppingCart.getId() == id){
                return shoppingCart;
            }
        }
        return null;
    }

    public void updateShoppingCart(ShoppingCart shoppingCart){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            shoppingCart = entityManager.merge(shoppingCart);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteShoppingCart(int id){
        EntityManager entityManager = null;

        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            ShoppingCart shoppingCart = null;
            try{
                shoppingCart = entityManager.getReference(ShoppingCart.class, id);
                shoppingCart.getProducts().clear();
            } catch (Exception e){
                e.printStackTrace();
            }
            entityManager.remove(shoppingCart);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
