package browser;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.Page;
import com.ui4j.api.browser.PageConfiguration;

public class HTTPBrowser {
	
	private static volatile HTTPBrowser instance = null;
	
	private BrowserEngine browser;
    private PageConfiguration userAgent;
    
    public HTTPBrowser() {
    	BrowserEngine webkit = BrowserFactory.getWebKit();
        PageConfiguration config = new PageConfiguration();
        config.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
    }
    
    public HTTPBrowser(BrowserEngine b, PageConfiguration p) {
    	this.browser = b;
    	this.userAgent = p;
    }
    
    public void setUserAgent(PageConfiguration p) {
    	this.userAgent = p;
    }
    
    public void waitForAsyncRequestsToEnd() {
    	try {
			Thread.sleep(5000);
		} 
        catch (InterruptedException e) {
			e.printStackTrace();
        }
    }
    
    public Page navigate(String url) {
    	if (browser == null) {
    		browser = BrowserFactory.getWebKit();
    	}
    	if(userAgent == null) {
    		PageConfiguration config = new PageConfiguration();
            config.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            userAgent = config;
    	}
    	Page page = browser.navigate(url, userAgent);
    	waitForAsyncRequestsToEnd();
    	return page;
    }

	public static HTTPBrowser getInstance() {
		if(HTTPBrowser.instance == null) {
			synchronized(HTTPBrowser.class) {
              if (HTTPBrowser.instance == null) {
            	  HTTPBrowser.instance = new HTTPBrowser();
              }
            }
         }
         return HTTPBrowser.instance;
	}

}
