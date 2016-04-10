package languagestation.service.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import languagestation.service.nlp.model.PosPair;
import languagestation.util.GoProcess;
import languagestation.util.MyUtil;

@Service
public class JapaneseAnalyzeService {
	
	public enum FURIGANA_TYPE {
		RUBY,          //ご<Ruby>飯<rt>はん</rt></Ruby>
		BRACE,         //ご{飯;はん}
		PARENTHESIS    //ご飯(はん)
	}
	
	public String getFuriganaRuby(String sentence, FURIGANA_TYPE type) throws IOException {
		List<PosPair> posPairs = getPosAnalyzeResult(sentence);
		
		StringBuffer buf = new StringBuffer();
		
		for (PosPair posPair : posPairs) {
			//System.out.println(posPair.toString());
			
			//not japanese. 
			if (posPair.getAdditionalInfo().size() == 6) {
				buf.append(posPair.getWord());
			}
			//japanese
			else if (posPair.getAdditionalInfo().size() == 8) {
				String word = posPair.getWord();
				String pos = posPair.getPos();
				String posType = posPair.getAdditionalInfo().get(0);
				String yomi_kata = posPair.getAdditionalInfo().get(6);
				String pronounce_kata = posPair.getAdditionalInfo().get(7);
				
				if (MyUtil.isHiragana(word)) {
					buf.append(word);
				}
				else if (pos.equals("記号")) { 
					buf.append(word);
				}
				else if (word.equals(yomi_kata)) {//kata kana
					buf.append(word);
				}
				else if (pos.equals("名詞") && posType.equals("数")) {
					buf.append(word);
				}
				else {//only kanji need to be rubynize
					String yomi_hira = MyUtil.convertKana(yomi_kata);
					if (type.equals(FURIGANA_TYPE.RUBY)) {
						buf.append(MyUtil.makeRuy(word, yomi_hira));	
					}
					else if (type.equals(FURIGANA_TYPE.BRACE)) {
						buf.append(MyUtil.makeBrace(word, yomi_hira));
					}
					else if (type.equals(FURIGANA_TYPE.PARENTHESIS)) {
						buf.append(MyUtil.makeParenthesis(word, yomi_hira));
					}	
				}
			}
		}
		
		return buf.toString();
	}
	
	public List<PosPair> getPosAnalyzeResult(String sentence) throws IOException {
		MyUtil.writeFile("temp_mecab.txt", sentence);

		String result = GoProcess.goProcess(10000, "/usr/local/bin/mecab", System.getProperty("user.dir") + "/temp_mecab.txt");
		
		MyUtil.deleteFile("temp_file_mecab.txt");
		
		List<PosPair> posPairs = new ArrayList<PosPair>();
		
		StringTokenizer tok = new StringTokenizer(result, "\n");
		
		while (tok.hasMoreTokens()) {
			String line = tok.nextToken();
			if (line.equals("EOS")) break;
			
			PosPair posPair = new PosPair();
			
			StringTokenizer tok2 = new StringTokenizer(line, "\t");
			
			String word = tok2.nextToken();
			
			String mecab = tok2.nextToken();
			
			posPair.setWord(word);
			
			StringTokenizer tok3 = new StringTokenizer(mecab, ",");
			posPair.setPos(tok3.nextToken());
			while (tok3.hasMoreTokens()) {
				posPair.addAdditionalInfo(tok3.nextToken());
			}
			
			posPairs.add(posPair);
			//System.out.println(posPair.toString());
		}
		return posPairs;
	}

	public List<String> extractWords(String sentence) throws IOException {
		List<PosPair> posPairs = getPosAnalyzeResult(sentence);
		List<String> words = new ArrayList<String>();
		
		for (PosPair posPair : posPairs) {
			//not japanese. 
			if (posPair.getAdditionalInfo().size() == 6) {
				continue;
			}
			//japanese
			else if (posPair.getAdditionalInfo().size() == 8) {
				String word = posPair.getWord();
				String pos = posPair.getPos();
				String original = posPair.getAdditionalInfo().get(5);
				String yomi_kata = posPair.getAdditionalInfo().get(6);
				String pronounce_kata = posPair.getAdditionalInfo().get(7);
				
				if (pos.equals("記号") || pos.equals("助詞") || pos.equals("助動詞")) { //「」
					continue;
				}
				else if (pos.equals("名詞")) {
					 String posType = posPair.getAdditionalInfo().get(0);
					 if (posType.equals("数")) continue;
					 if (words.contains(original) == false) {
							words.add(original);
					 }
				}
				else {
					if (words.contains(original) == false) {
						words.add(original);
					}
				}
			}
		}
		return words;
	}
}
