package net.sf.minuteProject.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import net.sf.minuteProject.model.dao.GenericDaoImpl;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;

@Repository
public abstract class GenericDaoJpaImpl <T> extends GenericDaoImpl<T> {

    @PersistenceContext
    protected EntityManager entityManager;
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
    
    public Long count(T t, EntityMatchType matchType, OperandType operandType, Boolean caseSensitivenessType) {
        Query query = getEntityManager().createQuery(countQuery(t, matchType, operandType, caseSensitivenessType));
        List<Long> list = query.getResultList();
    	if (!list.isEmpty()) {
            return list.get(0);
    	}
    	return 0L;
    }
    
	protected String countQuery (T t, EntityMatchType matchType, OperandType operandType, Boolean caseSensitivenessType) {
        String what = getQuerySelectFromEntity();
		return findQuery (t, null, what, matchType, operandType, caseSensitivenessType, null);
    }

	protected abstract String getQuerySelectFromEntity() ;
}
