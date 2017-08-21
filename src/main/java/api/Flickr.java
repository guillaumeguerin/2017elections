package api;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.Page;
import com.ui4j.api.browser.PageConfiguration;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

import browser.HTTPBrowser;

public class Flickr {

    public void images(String item) {
    	item = item.replace(" ", "%20");
    	HTTPBrowser b = HTTPBrowser.getInstance();
    	Page page = b.navigate("https://www.flickr.com/search/?text=" + item);
		Document doc = page.getDocument();
    	List<Element> aLinks = doc.queryAll("a");
    	for(Element aLink : aLinks){
			if(aLink.hasAttribute("href") && aLink.getAttribute("href").get().startsWith("/photos/")) {
				//Download
			}
        }
    }
}
