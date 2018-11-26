package es.uam.eps.tweetextractor.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v, formatter);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.format(formatter);
	}

}
