package net.sf.minuteProject.model.dao.jpa;

import java.util.List;

import net.sf.minuteProject.model.dao.GenericDaoImpl;

public abstract class GenericDaoJpaImpl <T> extends GenericDaoImpl<T> {

    @PersistenceContext
    private EntityManager entityManager;
    /**
    * Saves a T entity 
    * @param T t
    */
   public void save (T t) {
	   
   }
   
   /**
    * Deletes a T entity 
    * @param T t
    */
   public void delete (T t) {
	   
   }

    /**
     * Inserts a T entity 
     * @param T t
     */
    public void insert (T t) {
    	
    }
 
    /**
     * Inserts a list of T entity 
     * @param List<T> ts
     */
    public void insert (List<T> list) {
    	
    }
        
    /**
     * Updates a T entity 
     * @param T t
     */
    public T update(T t) {
    	return t;
    }
    
}
