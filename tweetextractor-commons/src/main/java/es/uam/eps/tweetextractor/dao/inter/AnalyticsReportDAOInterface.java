package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;

public interface AnalyticsReportDAOInterface<T> {
	public List<T> findByUser(User user);
}
