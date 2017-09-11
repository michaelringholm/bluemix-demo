package com.stelinno.cloud.bluemix;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.stelinno.http.HTTPHelper;
import com.stelinno.http.SimpleHTTPResponse;

public class CloudantPersistenceService implements DocumentPersistenceService {
	@Autowired private HTTPHelper httpHelper;
	@Autowired private String cloudantDBUrl;
	
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
	//delete
	//copy
}
