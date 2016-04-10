package languagestation;

import java.util.List;
import java.util.Set;

import org.chasen.mecab.Tagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import languagestation.service.nlp.EnglishAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService.FURIGANA_TYPE;
import languagestation.service.nlp.model.PosPair;
import languagestation.service.scrape.WebKitScrapeService;


@SpringBootApplication
@EnableScheduling
public class Application  implements CommandLineRunner{
	@Autowired WebKitScrapeService scraper;
	
	@Autowired JapaneseAnalyzeService japService;

	@Autowired EnglishAnalyzeService engService;
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

	public void run(String... args) throws Exception {
		//japService.getPosAnalyzeResult("太郎は二郎にこの本を渡した."); 
		
//		System.out.println(japService.getFuriganaRuby("「どういうことかっていうとさ、消えるらしいよ」。有給休暇の取り扱いに怒るブログのエントリーが、はてなブックマークで話題となった。", FURIGANA_TYPE.RUBY));
//		System.out.println(japService.getFuriganaRuby("「どういうことかっていうとさ、消えるらしいよ」。有給休暇の取り扱いに怒るブログのエントリーが、はてなブックマークで話題となった。", FURIGANA_TYPE.BRACE));
//		System.out.println(japService.getFuriganaRuby("「どういうことかっていうとさ、消えるらしいよ」。有給休暇の取り扱いに怒るブログのエントリーが、はてなブックマークで話題となった。", FURIGANA_TYPE.PARENTHESIS));
//		
//		List<PosPair> pairs = PosTagger.doit("Hi my name is dongkyu lee.");
//		for (PosPair pair : pairs) {
//			System.out.println(pair.getWord() + " : " + pair.getPos());
//		}
//		
//		Set<String> words = PosTagger.getDistinctWords("The new type of engine would 'guarantee' the ability to launch a nuclear strike on the US mainland, the KCNA news agency said."
//				+ "The test was conducted at the country's long-range missile launch site near its west coast."
//				+ "It is the latest in a series of tests and launches carried out by the isolated nation.");
//		
//		for (String word : words) {
//			System.out.println(word);
//			scraper.scrapeGoogleTranslate(word);
//		}
	}
}
