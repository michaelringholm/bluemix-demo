package com.stelinno.cloud.bluemix.cloudant;

import java.util.List;

import com.stelinno.cloud.bluemix.FootballMatch;

public class CloudantSearchResult {
	public int total_rows;
	public String bookmark;
	public List<CloudantSearchResultEntry<FootballMatch>> rows;
	//public List<Object> rows;
		
}
