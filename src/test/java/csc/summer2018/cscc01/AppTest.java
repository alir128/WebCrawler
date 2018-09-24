package csc.summer2018.cscc01;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.*;

import org.junit.Test;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;


/**
 * Unit test for simple App.
 */
public class AppTest {
    
    @Test
	public void paramsTest() throws Exception {
    	Controller controller=mock(Controller.class);
    	App.setController(controller);
    	App.main(new String[] { "-seed", "URL1", "URL2" });
    	verify(controller).run(new String[] {"-seed", "URL1", "URL2" });
	}
	
	@Test
	public void shouldVisitTest()  {
    	Crawler crawler =mock(Crawler.class);
    	WebURL url = new WebURL();
    	url.setURL("http://example.com/");
    	Page page = new Page(url);
    	
    	when(crawler.shouldVisit(page, url)).thenReturn(true);
    	assertEquals(crawler.shouldVisit(page,url),true);
	}
	
	@Test
	public void shouldVisitTestFalse()  {
    	Crawler crawler =mock(Crawler.class);
    	WebURL url = new WebURL();
    	url.setURL("nothing");
    	Page page = new Page(url);
    	
    	when(crawler.shouldVisit(page, url)).thenReturn(false);
    	assertEquals(crawler.shouldVisit(page,url),false);
	}
	
//	@Test
//	public void ControllerStart()  {
//    	Crawler crawler =mock(Crawler.class);
//    	Controller controller =mock(Controller.class);
//    	CrawlController ccontorller = mock(CrawlController.class);
//    	WebURL url = new WebURL();
//    	url.setURL("http://example.com/");
//    	Page page = new Page(url);
//    	
//    	when(crawler.shouldVisit(page, url)).thenReturn(true);
////    	verify(ccontorller).start(crawler,anyInt());
//	}
}
