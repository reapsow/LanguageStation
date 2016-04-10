package languagestation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import languagestation.controller.dto.ReqSentence;
import languagestation.controller.dto.ResFurigana;
import languagestation.controller.dto.ResWord;
import languagestation.controller.dto.ResWords;
import languagestation.repository.LanguageProcessRepository;
import languagestation.service.nlp.EnglishAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService.FURIGANA_TYPE;
import languagestation.service.scrape.WebKitScrapeService;

@RestController
public class LanguageStationController {
	@Autowired
	LanguageProcessRepository repo;

	@Autowired
	JapaneseAnalyzeService japService;

	@Autowired
	EnglishAnalyzeService engService;

	@Autowired
	WebKitScrapeService webkitScrapeService;

	@RequestMapping(value="/reqFurigana", method=RequestMethod.POST)
	@ResponseBody
	public ResFurigana reqFurigana(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();
		String ruby = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.RUBY);
		String brace = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.BRACE);
		String parenthesis = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.PARENTHESIS);

		ResFurigana res = new ResFurigana();
		res.setSentence(sentence);
		res.setRuby(ruby);
		res.setBrace(brace);
		res.setParenthesis(parenthesis);

		System.out.println(res.toString());

		return res;
	}

	@RequestMapping(value="/reqExtractJap", method=RequestMethod.POST)
	@ResponseBody
	public ResWords reqExtractJap(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();

		List<String> japWords = japService.extractWords(sentence);

		ResWords res = new ResWords();
		for (String japWord : japWords) {
			String engWord = webkitScrapeService.scrapeGoogleTranslate(japWord, "ja", "en");
			String korWord = webkitScrapeService.scrapeGoogleTranslate(japWord, "ja", "ko");
			
			ResWord resWord = new ResWord();
			resWord.setEng(engWord);
			resWord.setJap(japWord);
			resWord.setKor(korWord);
			
			res.addWord(resWord);
		}

		System.out.println(res.toString());

		return res;
	}
	
	@RequestMapping(value="/reqTrasnlateKor", method=RequestMethod.POST)
	@ResponseBody
	public ResWords reqTrasnlateKor(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();

		ResWords res = new ResWords();
		String engWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "ko", "en");
		String japWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "ko", "ja");

		ResWord resWord = new ResWord();
		resWord.setEng(engWord);
		resWord.setJap(japWord);
		resWord.setKor(sentence);
		
		res.addWord(resWord);

		System.out.println(res.toString());

		return res;
	}
	
	@RequestMapping(value="/reqTrasnlateJap", method=RequestMethod.POST)
	@ResponseBody
	public ResWords reqTrasnlateJap(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();

		ResWords res = new ResWords();
		String engWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "ja", "en");
		String korWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "ja", "ko");

		ResWord resWord = new ResWord();
		resWord.setEng(engWord);
		resWord.setJap(sentence);
		resWord.setKor(korWord);
		
		res.addWord(resWord);

		System.out.println(res.toString());

		return res;
	}

	@RequestMapping(value="/reqExtractEnglish", method=RequestMethod.POST)
	@ResponseBody
	public ResWords reqExtractEnglish(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();

		Set<String> engWords = engService.getDistinctWords(sentence);

		ResWords res = new ResWords();
		for (String engWord : engWords) {
			String japWord = webkitScrapeService.scrapeGoogleTranslate(engWord, "en", "ja");
			String korWord = webkitScrapeService.scrapeGoogleTranslate(engWord, "en", "ko");

			ResWord resWord = new ResWord();
			resWord.setEng(engWord);
			resWord.setJap(japWord);
			resWord.setKor(korWord);
			
			res.addWord(resWord);
		}

		System.out.println(res.toString());

		return res;

	}

	@RequestMapping(value="/reqTrasnlateEnglish", method=RequestMethod.POST)
	@ResponseBody
	public ResWords reqTrasnlateEnglish(@RequestBody ReqSentence reqSentence) throws IOException {

		String sentence = reqSentence.getSentence();


		ResWords res = new ResWords();
		String japWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "en", "ja");
		String korWord = webkitScrapeService.scrapeGoogleTranslate(sentence, "en", "ko");

		ResWord resWord = new ResWord();
		resWord.setEng(sentence);
		resWord.setJap(japWord);
		resWord.setKor(korWord);
		
		res.addWord(resWord);

		System.out.println(res.toString());

		return res;
	}

}
