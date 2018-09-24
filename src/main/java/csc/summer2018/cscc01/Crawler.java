/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * referenced from example:
 * https://github.com/yasserg/crawler4j/blob/master/crawler4j-examples/crawler4j-examples-base/src/test/java/edu/uci/ics/crawler4j/examples/localdata/LocalDataCollectorCrawler.java
*/

package csc.summer2018.cscc01;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|json|"
            + "|mp3|mp4|zip|gz))$");
	
	CrawlStat myCrawlStat;
	Page referringPage;
    public Crawler() {
        myCrawlStat = new CrawlStat();
    }
    
    @Override
	  protected void handlePageStatusCode(WebURL url, int statusCode, String statusDescription) {
		  	String weburl=url.getURL();
			int status=statusCode;
			 myCrawlStat.fetch.append(weburl);
			 myCrawlStat.fetch.append(',');
			 myCrawlStat.fetch.append(status);
			 myCrawlStat.fetch.append('\n');
	  }
    
    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions. In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
    	this.referringPage=referringPage;
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }
    
    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
    	String url = page.getWebURL().getURL();
    	int status=	page.getStatusCode();
    	Integer statusCount= myCrawlStat.statusCodes.get(status);
        if (statusCount == null) {
        	myCrawlStat.statusCodes.put(status, 1);
        }
        else {
        	myCrawlStat.statusCodes.put(status, statusCount+1);
        }

        myCrawlStat.incProcessedPages();
        if(status>=200 && status<300) {
        	myCrawlStat.visit.append(url);
        	myCrawlStat.visit.append(",");
        	
        }
        
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData parseData = (HtmlParseData) page.getParseData();
            int length = page.getContentData().length;      
            myCrawlStat.visit.append(length);
            myCrawlStat.visit.append(",");
            Set<WebURL> links = parseData.getOutgoingUrls();
            myCrawlStat.incTotalLinks(links.size());
            myCrawlStat.visit.append(links.size());
            myCrawlStat.visit.append(",");
            String []ContentType = page.getContentType().split(";");
            myCrawlStat.visit.append(ContentType[0]);
            myCrawlStat.visit.append("\n");
            
            try {
                myCrawlStat.incTotalTextSize(parseData.getText().getBytes("UTF-8").length);
            } catch (UnsupportedEncodingException ignored) {
                // Do nothing
            }
            for(WebURL link: links) {
            	myCrawlStat.url.append(link.getURL());
            	myCrawlStat.url.append(",");
            	String url_link = link.getURL();
            	if(url_link.startsWith(referringPage.getWebURL().getURL())) {
            		myCrawlStat.url.append("OK");
            	}else {
            		myCrawlStat.url.append("N_OK");
            	}
            	myCrawlStat.url.append("\n");
            }
        }
    }

    /**
     * This function is called by controller to get the local data of this crawler when job is
     * finished
     */
    @Override
    public Object getMyLocalData() {
        return myCrawlStat;
    }

    /**
     * This function is called by controller before finishing the job.
     * You can put whatever stuff you need here.
     */
    @Override
    public void onBeforeExit() {
        dumpMyData();
    }

    public void dumpMyData() {
        int id = getMyId();

        logger.info("Crawler {} > Processed Pages: {}", id, myCrawlStat.getTotalProcessedPages());
        logger.info("Crawler {} > Total Links Found: {}", id, myCrawlStat.getTotalLinks());
        logger.info("Crawler {} > Total Text Size: {}", id, myCrawlStat.getTotalTextSize());
    }
	
}
