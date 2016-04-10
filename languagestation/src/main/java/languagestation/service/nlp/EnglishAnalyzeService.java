package languagestation.service.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import languagestation.service.nlp.model.PosPair;

@Service
public class EnglishAnalyzeService {
	public static List<PosPair> doit (String sentence) {
		List<PosPair> pairs = new ArrayList<PosPair>();
		
		MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		String taggedString = tagger.tagString(sentence);

		StringTokenizer tok = new StringTokenizer(taggedString, " ");
		
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			String pos = token.replaceAll("^.*_", "");
			String word = token.replaceAll("_.*$", "");
			//System.out.println(word + "/" + pos);
			PosPair pair = new PosPair();
			pair.setWord(word);
			pair.setPos(pos);
			pairs.add(pair);
		}
		
		return pairs;
	}
	
	public static Map<String, Set<String>> getWordByPos(String sentence) {
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		
		MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		String taggedString = tagger.tagString(sentence);

		StringTokenizer tok = new StringTokenizer(taggedString, " ");
		
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			String pos = token.replaceAll("^.*_", "");
			String word = token.replaceAll("_.*$", "");
			
			if (result.get(pos) == null) {
				result.put(pos, new HashSet<String>());
			}
			
			result.get(pos).add(word);
//			
//			if (pos.matches("VBP") || 
//				pos.matches("VBZ")) {
//				continue;
//			}
//			
//			if (pos.matches("JJ.*") || 
//				pos.matches("NN.*") || 
//				pos.matches("RB.*")) {
//				System.out.println(pos + " " + word);
//				result.add(word);
//			}
		}
		
		for (Entry<String, Set<String>> oh : result.entrySet()) {
			System.out.println("***"+oh.getKey());

			Set<String> sets = oh.getValue();
			
			for (String set : sets) {
				System.out.println(set);
			}
		}
		
		
		return result;
	}
	

	public static Set<String> getDistinctWords(String sentence) {
		Set<String> result = new HashSet<String>();
		
		MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		
		String taggedString = tagger.tagString(sentence);

		StringTokenizer tok = new StringTokenizer(taggedString, " ");
		
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			String pos = token.replaceAll("^.*_", "");
			String word = token.replaceAll("_.*$", "");
			
			if (pos.matches("VBP") || 
				pos.matches("PRP") ||
				pos.matches("DT")  || 
				pos.matches("WP")  || 
				pos.matches("CC")  || 
				pos.matches("CD")  || 
				pos.matches("PDT")  || 
				pos.matches("RBS")  || 
				pos.matches("WDT")  || 
				pos.matches(",")  || 
				pos.matches(".")  || 
				pos.matches("POS")  || 
				pos.matches("MD")  ||
				pos.matches("VBZ")) {
				continue;
			}
			
			if (pos.matches("JJ.*") || 
				pos.matches("NN.*") || 
				pos.matches("RB.*")) {
				//System.out.println(pos + " " + word);
				result.add(word);
			}
		}
		
		return result;
	}
}
