package net.sf.minuteProject.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.minuteProject.model.dao.GenericDaoImpl;

public abstract class GenericDaoJpaImpl <T> extends GenericDaoImpl<T> {

    @PersistenceContext
    private EntityManager entityManager;
    /**
    * Saves a T entity 
    * @param T t
    */
   public void save (T t) {
	   entityManager.persist(t);
   }
   
   /**
    * Deletes a T entity 
    * @param T t
    */
   public void delete (T t) {
	   entityManager.remove(t);
   }

    /**
     * Inserts a T entity 
     * @param T t
     */
    public void insert (T t) {
    	entityManager.persist(t);
    }
 
    /**
     * Inserts a list of T entity 
     * @param List<T> ts
     */
    public void insert (List<T> list) {
    	for (T element : list)
    		insert(element);
    }
        
    /**
     * Updates a T entity 
     * @param T t
     */
    public T update(T t) {
    	entityManager.merge(t);
    	return t;
    }

    protected List<T> searchPrototype (String query, Integer maxResults) { 
        Query hquery = getEntityManager().createQuery(query);
        if (maxResults!=null)
           hquery.setMaxResults(maxResults);
        return hquery.getResultList();
     }
    
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
    
}
