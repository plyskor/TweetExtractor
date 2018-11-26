/**
 * 
 */
package es.uam.eps.tweetextractor.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Jose Antonio García del Saz
 *
 */

public class DateAdapter extends XmlAdapter<String, Date> {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
        return df.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return df.format(v);
    }
}