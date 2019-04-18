/**
 * 
 */
package es.uam.eps.tweetextractor.analytics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import es.uam.eps.tweetextractor.analytics.model.Keyword;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author jose
 *
 */
public class TweetExtractorNaturalTextProcessor {
	private List<Extraction> extractionList;
	private ExtractionServiceInterface eServ;
	private TweetServiceInterface tServ;
	private AnnotationConfigApplicationContext springContext;
	private Logger logger = LoggerFactory.getLogger(TweetExtractorNaturalTextProcessor.class);


	/**
	 * 
	 */
	public TweetExtractorNaturalTextProcessor(AnnotationConfigApplicationContext springContext) {
		super();
		this.springContext=springContext;
		eServ=springContext.getBean(ExtractionServiceInterface.class);
		tServ=springContext.getBean(TweetServiceInterface.class);
	}

	/**
	 * @return the extractionList
	 */
	public List<Extraction> getExtractionList() {
		return extractionList;
	}

	/**
	 * @param extractionList the extractionList to set
	 */
	public void setExtractionList(List<Extraction> extractionList) {
		this.extractionList = extractionList;
	}

	public void ner() {
		String spanishSerializedClassifier = "edu/stanford/nlp/models/ner/spanish.kbp.ancora.distsim.s512.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = null;
		try {
			classifier = CRFClassifier.getClassifier(spanishSerializedClassifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sBuild = new StringBuilder();
		List<Tweet> tweets = tServ.findAll();
		for (Tweet t : tweets) {
			sBuild.append(t.getText());
		}
		List<List<CoreLabel>> res = classifier.classify(sBuild.toString());
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("out.log"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (List<CoreLabel> coreLabels : res) {
			System.out.println(coreLabels);

			for (CoreLabel word : coreLabels) {
				try {
					writer.write(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ner2() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner");

		props.setProperty("tokenize.language", "es");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");
		props.setProperty("ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		List<Tweet> tweets = tServ.findAll();
		List<String> tests = new ArrayList<>();
		for (Tweet t:tweets) {
			tests.add(t.getText());
		}
		for (String s : tests) {

			Annotation document = new Annotation(s);
			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					String word = token.get(TextAnnotation.class);
					String ne = token.get(NamedEntityTagAnnotation.class);
					logger.info("Palabra: " + word + " tag:" + ne);
				}
			}
		}

	}
	public void frequency() {
		TokenStream tokenStream = null;
		try {
			  AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;

			StringBuilder sBuild = new StringBuilder();
			List <Tweet> tweets = tServ.findAll();
			for (Tweet t : tweets) {
				if(t.getLang().equals("es")) {
				sBuild.append(t.getText());
				}
			}
			String input = sBuild.toString();
		    // hack to keep dashed words (e.g. "non-specific" rather than "non" and "specific")
		    input = input.replaceAll("-+", "-0");
		    // replace any punctuation char but apostrophes and dashes by a space
		    input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
		    // replace most common english contractions
		    input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");
			   tokenStream = new StandardTokenizer(factory);
			  ((Tokenizer) tokenStream).setReader(new StringReader(input));
		
		   
		    // remove dots from acronyms (and "'s" but already done manually above)
		    tokenStream = new ClassicFilter(tokenStream);
		    // convert any char to ASCII
		    tokenStream = new ASCIIFoldingFilter(tokenStream);
		    CharArraySet set = SpanishAnalyzer.getDefaultStopSet();
		    set.add("http");
		    set.add("co");
		    set.add("t");
		    List<Keyword> keywords = new LinkedList<Keyword>();
		    CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		    try {
				tokenStream.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				while (tokenStream.incrementToken()) {
				  String term = token.toString();
				  // stem each term
				  String stem = null;
				try {
					stem = stem(term);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  if (stem != null) {
				    // create the keyword or get the existing one if any
				    Keyword keyword = find(keywords, new Keyword(stem.replaceAll("-0", "-")));
				    // add its corresponding initial token
				    keyword.add(term.replaceAll("-0", "-"));
				  }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("out.report"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    // reverse sort by frequency
		    Collections.sort(keywords);
		    try {
				writer.write("Frecuencia\tLexema\tTerminos\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    for (Keyword word : keywords) {
		    	 if (set.contains(word.getStem())) continue;
		    	    
		    	    try {
						writer.write(word.getFrequency()+"\t"+word.getStem()+"\t"+word.getTerms()+"\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	     
		    }    	    try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    return;

		  } finally {
		    if (tokenStream != null) {
		      try {
				tokenStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		  }
	}
	public static String stem(String term) throws IOException {
		TokenStream tokenStream = null;
		  try {
			  AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;

		    // tokenize
			   tokenStream = new StandardTokenizer(factory);
			  ((Tokenizer) tokenStream).setReader(new StringReader(term));
			  tokenStream.reset();
		    // stem
		    tokenStream = new PorterStemFilter(tokenStream);

		    // add each token in a set, so that duplicates are removed
		    Set<String> stems = new HashSet<String>();
		    CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		    while (tokenStream.incrementToken()) {
		      stems.add(token.toString());
		    }

		    // if no stem or 2+ stems have been found, return null
		    if (stems.size() != 1) {
		      return null;
		    }
		    String stem = stems.iterator().next();
		    // if the stem has non-alphanumerical chars, return null
		    if (!stem.matches("[a-zA-Z0-9-]+")) {
		      return null;
		    }

		    return stem;

		  } finally {
		    if (tokenStream != null) {
		      tokenStream.close();
		    }
		  }

		}
	public static <T> T find(Collection<T> collection, T example) {
		  for (T element : collection) {
		    if (element.equals(example)) {
		      return element;
		    }
		  }
		  collection.add(example);
		  return example;
		}
}
