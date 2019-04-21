/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.nlp;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Keyword;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingWordsReportRegister;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;

/**
 * @author jose
 *
 */
public class TweetExtractorNaturalTextProcessor {
	private AnnotationConfigApplicationContext springContext;
	private Logger logger = LoggerFactory.getLogger(TweetExtractorNaturalTextProcessor.class);
	private TweetServiceInterface tServ;
	/**
	 * 
	 */
	public TweetExtractorNaturalTextProcessor(AnnotationConfigApplicationContext springContext) {
		super();
		this.springContext = springContext;
		tServ=this.springContext.getBean(TweetServiceInterface.class);
	}


/*
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
		for (Tweet t : tweets) {
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
*/
	public List<TrendingWordsReportRegister> createWordFrequencyReport(TrendingWordsReport report) throws Exception {
		List<TrendingWordsReportRegister> toReturn= new ArrayList<>();
		AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;
		StringBuilder sBuild = new StringBuilder();
		/* Choose as input every tweet in the desired language */
		for (Extraction e : report.getExtractions()) {
			e.setTweetList(tServ.findByExtraction(e));
			for (Tweet t : e.getTweetList()) {
				if (t.getLang().equals(report.getLanguage().getShortName())) {
					sBuild.append(t.getText());
				}
			}
			tServ.detachList(e.getTweetList());
		}
		
		String input = sBuild.toString();
		try(TokenStream tokenStream =new StandardTokenizer(factory)){
		// hack to keep dashed words (e.g. "non-specific" rather than "non" and
		// "specific")
		input = input.replaceAll("-+", "-0");
		// replace any punctuation char but apostrophes and dashes by a space
		input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
		// replace most common english contractions
		input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");
		((Tokenizer) tokenStream).setReader(new StringReader(input));
		// convert any char to ASCII
		CharArraySet stopWordsSet = new CharArraySet(0,true);
		for (StopWord stopWord : report.getStopWordsList().getList()) {
			stopWordsSet.add(stopWord.getStopWord());
		}
		List<Keyword> keywords = new LinkedList<>();
		CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				String term = token.toString();
				// stem each term
				String stem = null;
				stem = stem(term);
				if (stem != null) {
					// create the keyword or get the existing one if any
					Keyword keyword = find(keywords, new Keyword(stem.replaceAll("-0", "-")));
					// add its corresponding initial token
					keyword.add(term.replaceAll("-0", "-"));
				}
			}
			// reverse sort by frequency
			Collections.sort(keywords);
			int nWords=0;
			for (Keyword word : keywords) {
				boolean discardWord=false;
				for(String term: word.getTerms()) {
					if (stopWordsSet.contains(term)) {
						discardWord=true;
						break;
					}
				}
				if(!discardWord) {
					nWords++;
					TrendingWordsReportRegister newRegister = new TrendingWordsReportRegister();
					newRegister.setFrequency(word.getFrequency());
					newRegister.setRoot(word.getStem());
					newRegister.getTerms().addAll(word.getTerms());
					toReturn.add(newRegister);
				}
				if(nWords==report.getN()) {
					break;
				}
			}
		} catch (Exception e) {
			logger.warn("An exception has been thrown analyzing word frequencies: "+e.getMessage());
			throw(e);
		}
		return toReturn;
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
