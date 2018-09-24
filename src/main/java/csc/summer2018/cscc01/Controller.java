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
 * referenced from:
 * https://github.com/yasserg/crawler4j/blob/master/crawler4j-examples/crawler4j-examples-base/src/test/java/edu/uci/ics/crawler4j/examples/localdata/LocalDataCollectorController.java
 * 
 */

package csc.summer2018.cscc01;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.*;

import java.io.FileWriter;
import java.io.PrintWriter;


public class Controller { 
	private String[] args;
	List<String> sites = new ArrayList<String>();
	private static final Logger logger =
	        LoggerFactory.getLogger(Controller.class);
	int depth = 0;
	int pages = 0;
	
    public void run(String[] args) throws Exception{
    	this.args = args;
    	
    	try {
		    hasNoArguments();
		    parseArgs();
		} catch(InvalidParamsException e) {
		    System.out.println("invalid params");
		    System.exit(-1);
		}
    	
    	//create the files required.
    	PrintWriter fetchFile = new PrintWriter(new  FileWriter("fetch.csv"));
    	PrintWriter visitFile = new PrintWriter(new  FileWriter("visit.csv"));
    	PrintWriter urlFile = new PrintWriter(new  FileWriter("url.csv"));

	     
	    String crawlStorageFolder = "./data/crawl/root";
		int numberOfCrawlers = 7;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(depth);
		config.setMaxPagesToFetch(pages);
		
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 * Used an iterator to loop over pages provided in args.
		 */

		Iterator<String> itr = sites.iterator();
		while(itr.hasNext()) {
			controller.addSeed(itr.next());
			
		}
		
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(Crawler.class, numberOfCrawlers);
		System.out.println("Done Crawling");
		
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
        long totalLinks = 0;
        int totalProcessedPages = 0;
        for (Object localData : crawlersLocalData) {
            CrawlStat stat = (CrawlStat) localData;
            totalLinks += stat.getTotalLinks();
            totalProcessedPages += stat.getTotalProcessedPages();
            fetchFile.write(stat.fetch.toString());
            visitFile.write(stat.visit.toString());
            urlFile.write(stat.url.toString());
        }

        logger.info("Aggregated Statistics:");
        logger.info("\tProcessed Pages: {}", totalProcessedPages);
        logger.info("\tTotal Links found: {}", totalLinks);
        
        fetchFile.close();
        visitFile.close();
        urlFile.close();
	 }

     private boolean hasNoArguments() throws InvalidParamsException{
         Boolean value = null;
         
         if(args.length == 0) {
        	 throw new InvalidParamsException();
    	 } else {
    		 value =false;
    		
    	 }
         return value;
     }
     
     protected void parseArgs() throws InvalidParamsException {
    	 for(int i=0;i < args.length;i++) {
    		 if( args[i].equals("-seed")) {
    			 i++;
    			 sites.add(args[i].substring(0, args[i].length()-1));
    		 } else if (args[i].equals("-depth")) {
    			 i++;
    			 depth = Integer.parseInt(args[i]);
    		 } else if (args[i].equals("-pages")) {
    			 i++;
    			 pages = Integer.parseInt(args[i]);
    		 } else {
    			 // move on....
    		 }
    	 }
//    	 System.out.println(depth);
//    	 System.out.println(pages);
//    	 System.out.println(sites);
    	 if (sites.isEmpty() || depth==0 || pages ==0) {
    		 throw new InvalidParamsException();
    	 }
    	 
     }
     
     

}
