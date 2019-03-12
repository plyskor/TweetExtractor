/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.CredentialsDAOInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Repository
public class CredentialsDAO  extends AbstractGenericDAO<Credentials,Integer> implements CredentialsDAOInterface<Credentials> {
	public CredentialsDAO() {
		super();
	}
	public List<Credentials> findByUser(User user) {
		Query<Credentials> query = currentSession().createNamedQuery("findCredentialsByUser",Credentials.class);
	    query.setParameter("user", user);
	     List<Credentials> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No credentials found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}	
}