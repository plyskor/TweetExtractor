/**
 * 
 */
package es.uam.eps.tweetextractor.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author jgarciadelsaz
 *
 */
public class TweetExtractorThreadFactory implements ThreadFactory {
	private int counter;
	private String name;
	private List<String> stats;

	/**
	 * 
	 */
	public TweetExtractorThreadFactory() {
		super();
		counter = 1;
	    this.name = "TweetExtractorScheduler";
	    stats = new ArrayList<>();
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stats
	 */
	public List<String> getStats() {
		return stats;
	}

	/**
	 * @param stats the stats to set
	 */
	public void setStats(List<String> stats) {
		this.stats = stats;
	}

	@Override
	public Thread newThread(Runnable arg0) {
		Thread t = new Thread(arg0, name + "-ScheduledJob_" + counter);
	    counter++;
	    stats.add(String.format("Created scheduled job %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
	    return t;
	}

}
