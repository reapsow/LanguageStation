package languagestation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyUtil {

	public static void writeFile(String fileName, String sentence) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(sentence); 
		out.newLine();
		out.close();
	}

	public static boolean isHiragana(String sentence) {
		if (sentence.matches("^[あ-ん]+$")) return true;
		return false;
	}
	public static String convertKana(String input) {
		if (input == null || input.length() == 0) return "";

		StringBuilder out = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (ch == 'ー') {
				out.append(ch);
			}
			else if (JapaneseCharacter.isHiragana(ch)) { // convert to hiragana to katakana
				out.append(JapaneseCharacter.toKatakana(ch));
			}
			else if (JapaneseCharacter.isKatakana(ch)) { 
				out.append(JapaneseCharacter.toHiragana(ch));
			}
			else {
				out.append(ch);
			}
		}

		return out.toString();
	}

	public static void deleteFile(String fileName) {
		File f = new File(fileName);
		f.delete();
	}


	public static boolean isHiraganaChar(char code) {
		if (code >= 12352 && code <= 12447) {
			return true;
		}
		else {
			return false;
		}
	}

	/* 
	 * example input and output
	 * original : A_K1_B_K2_C
	 * hiragana : A_H1_B_H2_C
	 * output   : A_<Ruby>K1<rt>H1</rt></Ruby>_B_<Ruby>K2<rt>H2</rt></Ruby>_C
	 * 
	 */
	public static String makeRuy(String original, String hiragana) {
		int o_idx = 0;
		int h_idx = 0;

		StringBuffer result = new StringBuffer();

		while (true) {
			if (isHiraganaChar(original.charAt(o_idx)) ) {
				result.append(original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			else {
				result.append("<Ruby>");
				while (true) {
					result.append(original.charAt(o_idx));
					o_idx++;
					if (o_idx >= original.length()) {
						result.append("<rt>");
						for (;h_idx < hiragana.length(); h_idx++) {
							result.append(hiragana.charAt(h_idx));
						}
						result.append("</rt></Ruby>");
						return result.toString();
					}

					if (isHiraganaChar(original.charAt(o_idx))) {
						break;
					}
				}
				result.append("<rt>");

				String buffer = "";

				for (;h_idx < hiragana.length(); h_idx++) {
					if (hiragana.charAt(h_idx) == original.charAt(o_idx)) break;
					buffer = buffer + hiragana.charAt(h_idx);
				}
				result.append(buffer  + "</rt></Ruby>"+original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			if (o_idx >= original.length()) return result.toString();
		}

	}

	public static String makeBrace(String original, String hiragana) {
		int o_idx = 0;
		int h_idx = 0;

		StringBuffer result = new StringBuffer();

		while (true) {
			if (isHiraganaChar(original.charAt(o_idx)) ) {
				result.append(original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			else {
				result.append("{");
				while (true) {
					result.append(original.charAt(o_idx));
					o_idx++;
					if (o_idx >= original.length()) {
						result.append(";");
						for (;h_idx < hiragana.length(); h_idx++) {
							result.append(hiragana.charAt(h_idx));
						}
						result.append("}");
						return result.toString();
					}

					if (isHiraganaChar(original.charAt(o_idx))) {
						break;
					}
				}
				result.append(";");

				String buffer = "";

				for (;h_idx < hiragana.length(); h_idx++) {
					if (hiragana.charAt(h_idx) == original.charAt(o_idx)) break;
					buffer = buffer + hiragana.charAt(h_idx);
				}
				result.append(buffer  + "}"+original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			if (o_idx >= original.length()) return result.toString();
		}
	}

	public static String makeParenthesis(String original, String hiragana) {
		int o_idx = 0;
		int h_idx = 0;

		StringBuffer result = new StringBuffer();

		while (true) {
			if (isHiraganaChar(original.charAt(o_idx)) ) {
				result.append(original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			else {
				result.append("");
				while (true) {
					result.append(original.charAt(o_idx));
					o_idx++;
					if (o_idx >= original.length()) {
						result.append("(");
						for (;h_idx < hiragana.length(); h_idx++) {
							result.append(hiragana.charAt(h_idx));
						}
						result.append(")");
						return result.toString();
					}

					if (isHiraganaChar(original.charAt(o_idx))) {
						break;
					}
				}
				result.append("(");

				String buffer = "";

				for (;h_idx < hiragana.length(); h_idx++) {
					if (hiragana.charAt(h_idx) == original.charAt(o_idx)) break;
					buffer = buffer + hiragana.charAt(h_idx);
				}
				result.append(buffer  + ")"+original.charAt(o_idx));
				o_idx++;
				h_idx++;
			}
			if (o_idx >= original.length()) return result.toString();
		}
	}
}
