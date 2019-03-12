/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.TweetDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportVolumeRegister;

/**
 * @author Jose Antonio García del Saz
 *
 */

@Repository
public class TweetDAO extends AbstractGenericDAO<Tweet,Integer> implements TweetDAOInterface<Tweet>{

	public TweetDAO() {
		super();
	}
	public List<TimelineReportVolumeRegister> extractGlobalTimelineVolumeReport(User user){
		Query q = currentSession().createNativeQuery("SELECT date_part('year', t.creation_date) AS \"Year\","
				+ "date_part('month', t.creation_date) AS \"Month\","
				+ "date_part('day', t.creation_date) AS \"Day\","
				+ "COUNT(t.*) AS \"Volume\" "
				+ "FROM perm_tweet t "
				+ "JOIN perm_extraction e ON e.identifier=t.extraction_identifier "
				+ "JOIN perm_user u ON e.user_identifier=u.identifier "
				+ "WHERE u.identifier=:user "
				+ "GROUP BY  date_part('day', t.creation_date),"
				+ "date_part('month', t.creation_date),"
				+ "date_part('year', t.creation_date) "
				+ "ORDER BY  \"Year\",\"Month\",\"Day\";").setParameter("user", user.getIdDB());
		List<Object[]> resultList;
		List<TimelineReportVolumeRegister> ret = new ArrayList<>();
		try {
			resultList=q.getResultList();
		}catch(Exception e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.warn(e.getMessage());
	    	logger.warn(Arrays.toString(e.getStackTrace()));
	    	return ret;
		}
		for(Object [] row : resultList) {
			TimelineReportVolumeRegister toadd = new TimelineReportVolumeRegister();
			toadd.setYear(((Double)row[0]).intValue());
			toadd.setMonth(((Double)row[1]).intValue());
			toadd.setDay(((Double)row[2]).intValue());
			toadd.setValue(((BigInteger)row[3]).intValue());
			ret.add(toadd);
		}
		return ret;
		
	}
	
	public List<Tweet> findByExtraction(Extraction extraction) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<Tweet> criteriaQuery = criteriaBuilder.createQuery(Tweet.class);
	    Root<Tweet> root = criteriaQuery.from(Tweet.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Extraction> params = criteriaBuilder.parameter(Extraction.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("extraction"), params));
	    TypedQuery<Tweet> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, extraction );
	    List<Tweet> ret= new ArrayList<>();
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No tweet found for extractionID: "+extraction.getIdDB());	   
	    	}
	    return ret;
	}
	
}