package es.uam.eps.tweetextractor.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.ExtractionDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

@Repository
public class ExtractionDAO extends AbstractGenericDAO<Extraction,Integer> implements ExtractionDAOInterface<Extraction, Integer> {
	public ExtractionDAO() {
		super();
	}
	public Extraction findById(Integer id) {
		return find(id);
	}
	public List<Extraction> findByUser(User user) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<Extraction> criteriaQuery = criteriaBuilder.createQuery(Extraction.class);
	    Root<Extraction> root = criteriaQuery.from(Extraction.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<Extraction> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<Extraction> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No extraction found for userID: "+user.getIdDB());
	    	return null;
	    	}
	    return ret;
	}
	public List<Extraction> findAll() {
		List<Extraction> extractions = (List<Extraction>) currentSession().createQuery("from Extraction").list();
		return extractions;
	}
	public void deleteAll() {
		List<Extraction> entityList = findAll();
		for (Extraction entity : entityList) {
			delete(entity);
		}
	}


	
}