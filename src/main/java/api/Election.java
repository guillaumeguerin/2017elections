package api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.Page;
import com.ui4j.api.browser.PageConfiguration;
import com.ui4j.api.dom.Document;
import com.ui4j.api.dom.Element;

import browser.HTTPBrowser;

public class Election {
    
	public List<String> getDepartements() {
		List<String> result = new ArrayList<String>();
		HTTPBrowser b = new HTTPBrowser();
		Page page = b.navigate("http://www.francetvinfo.fr/elections/resultats/");
		Document doc = page.getDocument();
		Element e = doc.query("section[id='departmentResults']").get();
		List<Element> as = e.queryAll("a");
		for(Element a : as){
        	if(a.hasAttribute("href")) {
        		result.add(a.getAttribute("href").get());
        	}
        }
		return result;
	}
	
	public int getCandidatesScore(String url, int i) {
		HTTPBrowser b = new HTTPBrowser();
		Page page = b.navigate(url);
		Document doc = page.getDocument();
		
		String departement = doc.queryAll("span[itemprop='title']").get(3).getInnerHTML();
		
		List<Element> divs = doc.queryAll("div");
		for(Element d : divs){
			//System.out.println(i);
        	if(d.hasAttribute("class") && d.getAttribute("class").isPresent() && d.getAttribute("class").get().toString().startsWith("candidate")) {
        		String candidat = d.query("span[class='name']").get().getInnerHTML().toString();
        		String voix = d.query("span[class='votes']").get().getInnerHTML().toString();
        		voix = voix.replace("votes","");
        		voix = voix.replace(" ","");
        		String tour = "1";
        		
        		
        		try
        		{
        		    String filename= "MyFile.txt";
        		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
        		    fw.write("{\"index\":{\"_index\":\"elections\",\"_type\":\"act\",\"_id\":"+ i +"}}\n");
        		    fw.write("{\"departement\":\"" + departement + "\",\"candidat\":\""+ candidat + "\",\"voix\":" + voix + ",\"tour\":" + tour + "}\n");
        		    fw.close();
        		}
        		catch(IOException ioe)
        		{
        		    System.err.println("IOException: " + ioe.getMessage());
        		}
        		
        		i++;
        	}
        }
		return i;
	}
	
    public List<String> search(String item) {
    	List<String> result = new ArrayList<String>();
    	item = item.replace(" ", "+");
    	HTTPBrowser b = new HTTPBrowser();
    	//HTTPBrowser b = HTTPBrowser.getInstance();
    	Page page = b.navigate("https://www.google.fr/search?q=" + item);
        Document doc = page.getDocument();
        doc = page.getDocument();
        List<Element> as = doc.queryAll("a");
        for(Element a : as){
        	if(a.hasAttribute("href") && (a.getAttribute("href").get().startsWith("/url?") || a.getAttribute("href").get().startsWith("http://www.") || a.getAttribute("href").get().startsWith("https://www.") ) && !(a.getAttribute("href").get().startsWith("http://www.google") || a.getAttribute("href").get().startsWith("https://www.google") || a.getAttribute("href").get().startsWith("https://www.youtube") || a.getAttribute("href").get().startsWith("https://www.blogger")) ) {
        		result.add(a.getAttribute("href").get());
        	}
        }
        return result;
    }
    

    
    public void images(String item) throws IOException {
    	HTTPBrowser b = HTTPBrowser.getInstance();
    	Page page = b.navigate("https://www.google.nl/search?tbm=isch&q=" + item);
		Document doc = page.getDocument();
		boolean stop = false;
		File file = new File("GoogleLinks.txt");
		FileWriter fileWriter = new FileWriter(file);
		page.show(true);
		doc = page.getDocument();
		outputImageLinks(doc, fileWriter);
		doc.queryAll("div[id='navbar']").get(0).query("a").get().click();
		do {
			b.waitForAsyncRequestsToEnd();
			doc = page.getDocument();
			outputImageLinks(doc, fileWriter);
			Optional<Element> navBar = doc.query("div[id='navbar']");
			if(navBar.isPresent()) {
				List<Element> links = navBar.get().queryAll("a");
				if(links.size() >= 2) {
					Optional<String> url = links.get(1).getAttribute("href");
					if(url.isPresent()) {
						System.out.println(url.get());
					}
					links.get(1).click();
				}
				else {
					stop = true;
				}
			}
			else {
				stop = true;
			}
		}
		while(!stop);
		fileWriter.flush();
		fileWriter.close();
    }
    
    public void outputImageLinks(Document doc, FileWriter fileWriter) throws IOException {
    	List<Element> aLinks = doc.queryAll("a[class='image']");
    	for(Element aLink : aLinks){
			fileWriter.write(aLink.getAttribute("href").get().toString() + "\n");
        }
    }
}
