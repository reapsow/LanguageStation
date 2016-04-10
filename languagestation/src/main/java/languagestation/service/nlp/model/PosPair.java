package languagestation.service.nlp.model;

import java.util.ArrayList;
import java.util.List;

public class PosPair {
	private String word;
	private String pos;
	private List<String> additionalInfo;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public List<String> getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(List<String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public void addAdditionalInfo(String info) {
		if (additionalInfo == null) additionalInfo = new ArrayList<String>();
		
		additionalInfo.add(info);
	}
	@Override
	public String toString() {
		return "PosPair [word=" + word + ", pos=" + pos + ", additionalInfo=" + additionalInfo + "]";
	}
	
	
}
