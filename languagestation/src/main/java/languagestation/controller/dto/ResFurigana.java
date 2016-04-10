package languagestation.controller.dto;

public class ResFurigana {
	private String sentence;
	private String ruby;
	private String brace;
	private String parenthesis;
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getRuby() {
		return ruby;
	}
	public void setRuby(String ruby) {
		this.ruby = ruby;
	}
	public String getBrace() {
		return brace;
	}
	public void setBrace(String brace) {
		this.brace = brace;
	}
	public String getParenthesis() {
		return parenthesis;
	}
	public void setParenthesis(String parenthesis) {
		this.parenthesis = parenthesis;
	}
	@Override
	public String toString() {
		return "ResFurigana [sentence=" + sentence + ", ruby=" + ruby + ", brace=" + brace + ", parenthesis="
				+ parenthesis + "]";
	}
	
}
