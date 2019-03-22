package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.ExtractionDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

@Repository
public class ExtractionDAO extends AbstractGenericDAO<Extraction,Integer> implements ExtractionDAOInterface<Extraction> {
	public ExtractionDAO() {
		super();
	}

	public List<Extraction> findByUser(User user) {
		Query<Extraction> query = currentSession().createNamedQuery("findExtractionsByUser",Extraction.class);
	    query.setParameter("user", user);
	     List<Extraction> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No extraction found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}