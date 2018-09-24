package csc.summer2018.cscc01;

import java.util.HashMap;


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
 * https://github.com/yasserg/crawler4j/blob/master/crawler4j-examples/crawler4j-examples-base/src/test/java/edu/uci/ics/crawler4j/examples/localdata/CrawlStat.java
 * 
 */


public class CrawlStat {
    private int totalProcessedPages;
    private long totalLinks;
    private long totalTextSize;
	public StringBuilder fetch;
	public StringBuilder visit;
	public StringBuilder url;
	public HashMap<String, Integer> fileSizes = new HashMap<String, Integer>();
	public HashMap<String, Integer> content = new HashMap<String, Integer>();
	public HashMap<Integer, Integer> statusCodes = new HashMap<Integer, Integer>();

	public CrawlStat() {
		fetch=new StringBuilder();
		visit=new StringBuilder();
		url=new StringBuilder();
		fileSizes.put("< 1KB: ", 0);
		fileSizes.put("1KB ~ <10KB: ", 0);
		fileSizes.put("10KB ~ <100KB: ", 0);
		fileSizes.put("100KB ~ <1MB: ", 0);
		fileSizes.put(">= 1MB: ", 0);
	}

    public int getTotalProcessedPages() {
        return totalProcessedPages;
    }

    public void setTotalProcessedPages(int totalProcessedPages) {
        this.totalProcessedPages = totalProcessedPages;
    }

    public void incProcessedPages() {
        this.totalProcessedPages++;
    }

    public long getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(long totalLinks) {
        this.totalLinks = totalLinks;
    }

    public long getTotalTextSize() {
        return totalTextSize;
    }

    public void setTotalTextSize(long totalTextSize) {
        this.totalTextSize = totalTextSize;
    }

    public void incTotalLinks(int count) {
        this.totalLinks += count;
    }

    public void incTotalTextSize(int count) {
        this.totalTextSize += count;
    }
    
    public StringBuilder getFetch() {
		return fetch;
	}

	public void setFetch(StringBuilder fetch) {
		this.fetch = fetch;
	}

	public StringBuilder getVisit() {
		return visit;
	}

	public void setVisit(StringBuilder visit) {
		this.visit = visit;
	}

	public StringBuilder getUrl() {
		return url;
	}

	public void setUrl(StringBuilder url) {
		this.url = url;
	}
	
}