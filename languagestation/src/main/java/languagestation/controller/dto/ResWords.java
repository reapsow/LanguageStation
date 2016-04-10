package languagestation.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ResWords {
	private List<ResWord> words;
	
	
	public void addWord(ResWord word) {
		if (this.words  == null) this.words = new ArrayList<ResWord>();
		this.words.add(word);
	}


	public List<ResWord> getWords() {
		return words;
	}


	public void setWords(List<ResWord> words) {
		this.words = words;
	}
	
}
