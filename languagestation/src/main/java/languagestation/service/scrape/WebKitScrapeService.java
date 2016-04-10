package languagestation.service.scrape;

import static com.ui4j.api.browser.BrowserFactory.getWebKit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.ui4j.api.browser.Page;

@Service
public class WebKitScrapeService {

	public static String scrapeGoogleTranslate(String sentence, String from, String to ) throws UnsupportedEncodingException {
		System.setProperty("ui4j.headless", "true");

		String encoded = URLEncoder.encode(sentence, "UTF-8");

		StringBuffer buf = new StringBuffer();
		
		try (Page page = getWebKit().navigate("https://translate.google.com/#" + from + "/" + to + "/"+encoded)) {
			page.getDocument()
			.queryAll("#result_box")
			.forEach(e -> {
				System.out.println(e.getText().get());
				buf.append(e.getText().get());
			});
		}
		
		return buf.toString();
	}
}
