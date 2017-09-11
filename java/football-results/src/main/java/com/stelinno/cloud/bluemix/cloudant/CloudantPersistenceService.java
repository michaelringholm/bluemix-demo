package com.stelinno.cloud.bluemix.cloudant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stelinno.cloud.bluemix.DocumentPersistenceService;
import com.stelinno.cloud.bluemix.FootballMatch;
import com.stelinno.http.HTTPHelper;
import com.stelinno.http.SimpleHTTPResponse;

public class CloudantPersistenceService implements DocumentPersistenceService {
	@Autowired private HTTPHelper httpHelper;
	@Autowired private String cloudantDBUrl;
	@Autowired private Gson gson;
	
	// purchase-order
	//key: 	daptepricaterallsootigno
	//pw: abf4274ae214b7a0a531bec79fc1269ce84351be
	
	// football-results
	// key: ioselseadyoutiestiesseds
	// pw: f26e24d394fe88b1d20ee4875d1aa5c4fc2988ef
	// https://developer.ibm.com/clouddataservices/cloudant-http-api/
	
	
	//get
	public void get(String id) {
		id = "53fccc65ec434d43eff6f72ab92a974c";
		String targetUrl = cloudantDBUrl + "/" + id + "?include_docs=true";
		Map<String,String> headers = new HashMap<>();
		// Should come from an environment setting for security reasons
		headers.put("authorization", "Basic aW9zZWxzZWFkeW91dGllc3RpZXNzZWRzOmYyNmUyNGQzOTRmZTg4YjFkMjBlZTQ4NzVkMWFhNWM0ZmMyOTg4ZWY=");
		SimpleHTTPResponse response =  httpHelper.getHtml(targetUrl, headers);
		System.out.println(response.payload);
	}	
	//put 
	//post
	public void store(String json) {
		String targetUrl = cloudantDBUrl;
		Map<String,String> headers = new HashMap<>();
		// Should come from an environment setting for security reasons
		headers.put("authorization", "Basic aW9zZWxzZWFkeW91dGllc3RpZXNzZWRzOmYyNmUyNGQzOTRmZTg4YjFkMjBlZTQ4NzVkMWFhNWM0ZmMyOTg4ZWY=");
		SimpleHTTPResponse response = httpHelper.postJson(json, targetUrl, headers);
		System.out.println(response.payload);
	}
	
	public List<FootballMatch> find(String query) {
		String targetUrl = cloudantDBUrl + "/" + "_design/defaultDoc/_search/defaultSearchIndex?include_docs=true&query=" + query;
		Map<String,String> headers = new HashMap<>();
		// Should come from an environment setting for security reasons
		headers.put("authorization", "Basic aW9zZWxzZWFkeW91dGllc3RpZXNzZWRzOmYyNmUyNGQzOTRmZTg4YjFkMjBlZTQ4NzVkMWFhNWM0ZmMyOTg4ZWY=");
		SimpleHTTPResponse response =  httpHelper.getHtml(targetUrl, headers);		
		System.out.println(response.payload);
		CloudantSearchResult searchResult = gson.fromJson(response.payload.toString(), CloudantSearchResult.class);
		List<FootballMatch> matches = searchResult.rows.stream().map(r -> r.doc).collect(Collectors.toList());
		return matches;
	}
	//delete
	//copy
}
