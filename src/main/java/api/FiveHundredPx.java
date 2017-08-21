package api;

import java.util.List;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.Page;
import com.ui4j.api.browser.PageConfiguration;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

import browser.HTTPBrowser;

public class FiveHundredPx {

	public void images(String item) {
    	item = item.replace(" ", "%20");
    	HTTPBrowser b = HTTPBrowser.getInstance();
    	Page page = b.navigate("https://500px.com/search?type=photos&utm_campaign=google_search_box&q=" + item);
		Document doc = page.getDocument();
    	List<Element> aLinks = doc.queryAll("a");
    	for(Element aLink : aLinks){
			if(aLink.hasAttribute("href") && aLink.getAttribute("href").get().startsWith("/photos/")) {
				//Download
			}
        }
    }
	
}
