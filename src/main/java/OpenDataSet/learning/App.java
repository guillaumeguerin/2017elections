package OpenDataSet.learning;

import java.util.List;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.PageConfiguration;
import com.ui4j.api.dom.Element;

import api.Election;
import api.Google;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	BrowserEngine webkit = BrowserFactory.getWebKit();
        PageConfiguration config = new PageConfiguration();
        config.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        Election e = new Election();
        int c = 0;
        List<String> urls = e.getDepartements();
        for(String u : urls){
        	c = e.getCandidatesScore(u, c);
        }
        e.getCandidatesScore("http://www.francetvinfo.fr/elections/resultats/ain_01/", 0);
    }
}
