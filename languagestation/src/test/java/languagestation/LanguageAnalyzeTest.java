package languagestation;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import languagestation.service.nlp.EnglishAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService;
import languagestation.service.nlp.JapaneseAnalyzeService.FURIGANA_TYPE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class LanguageAnalyzeTest {

	protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EnglishAnalyzeService engService;
	
	@Autowired
	JapaneseAnalyzeService japService;
	
	@Before
	public void setup() {}
	
	@After
	public void finish() {}
	
	@Test
	public void testFurigana() throws IOException {
		
		String sentence = "有給休暇の取り扱いに怒る";
		
		String ruby = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.RUBY);
		String brace = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.BRACE);
		String parenthesis = japService.getFuriganaRuby(sentence, FURIGANA_TYPE.PARENTHESIS);
		
		System.out.println(ruby);
		System.out.println(brace);
		System.out.println(parenthesis);
		
		Assert.assertEquals("<Ruby>有給<rt>ゆうきゅう</rt></Ruby><Ruby>休暇<rt>きゅうか</rt></Ruby>の<Ruby>取<rt>と</rt></Ruby>り<Ruby>扱<rt>あつか</rt></Ruby>いに<Ruby>怒<rt>おこ</rt></Ruby>る", ruby);
		Assert.assertEquals("{有給;ゆうきゅう}{休暇;きゅうか}の{取;と}り{扱;あつか}いに{怒;おこ}る", brace);
		Assert.assertEquals("有給(ゆうきゅう)休暇(きゅうか)の取(と)り扱(あつか)いに怒(おこ)る", parenthesis);
	}
	
	@Test
	public void testExtractWordJap() throws IOException {
		
		String sentence = "このエントリーには、はてブが400近く集まり、「私も今年10日消えた」など投稿者の考えに理解を示す声が相次いだ。一方で、「有給の買い取りは法律で禁止されている」という指摘もあった。";
		
		List<String> words = japService.extractWords(sentence);
		
		for (String word : words) {
			System.out.println(word);
		}
	}
}
