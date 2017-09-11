package com.stelinno.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class StandardHTTPHelper implements HTTPHelper {
	private static final Logger logger = Logger.getLogger(StandardHTTPHelper.class.getName());

	
	public SimpleHTTPResponse postJson(String json, String targetUrl, Map<String, String> headers) {
		SimpleHTTPResponse simpleHTTPResponse = new SimpleHTTPResponse();
		HttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			//requestEntity.setContentEncoding("UTF-8");
			HttpPost postMethod = new HttpPost(targetUrl);
			// Add appid header if we are running in the google cloud, otherwise you can't call another app, locally it will fail though!
			/*if(ApiProxy.getCurrentEnvironment() != null)
				postMethod.addHeader("X-Appengine-Inbound-Appid", ApiProxy.getCurrentEnvironment().getAppId());*/
			
			if(headers != null) {
				Iterator<String> headerIter = headers.keySet().iterator();
				while(headerIter.hasNext()) {
					String key = headerIter.next();
					postMethod.addHeader(key, headers.get(key));
				}
			}
			
			postMethod.setEntity(requestEntity);
			HttpResponse httpResponse = httpClient.execute(postMethod);
			simpleHTTPResponse.statusCode = httpResponse.getStatusLine().getStatusCode();
			simpleHTTPResponse.payload = getStringContents(httpResponse);
			simpleHTTPResponse.reason = httpResponse.getStatusLine().getReasonPhrase();
			logger.log(Level.INFO, String.format("The http response from the server was %s", httpResponse.toString()));
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return simpleHTTPResponse;
	}
	
	/***
	 * https://cloud.google.com/appengine/docs/standard/java/issue-requests
	 * @param service
	 * @param targetUrl
	 * @return
	 */
	public SimpleHTTPResponse getHtml(String targetUrl, Map<String, String> headers) {
		SimpleHTTPResponse simpleHTTPResponse = new SimpleHTTPResponse();
		HttpClient httpClient = null;
		int retryCount = 0;
		boolean doRetry = true;
		
		while(doRetry) {
			try {				
				httpClient = HttpClientBuilder.create().build();
				HttpGet getMethod = new HttpGet(targetUrl);
				if(headers != null) {
					Iterator<String> headerIter = headers.keySet().iterator();
					while(headerIter.hasNext()) {
						String key = headerIter.next();
						getMethod.addHeader(key, headers.get(key));
					}
				}				
				HttpResponse httpResponse = httpClient.execute(getMethod);
				simpleHTTPResponse.statusCode = httpResponse.getStatusLine().getStatusCode();
				simpleHTTPResponse.payload = getStringContents(httpResponse);
				simpleHTTPResponse.reason = httpResponse.getStatusLine().getReasonPhrase();
				logger.log(Level.INFO, String.format("The http response from the server was %s", httpResponse.toString()));
				doRetry = false;
			}
			catch(java.net.SocketTimeoutException stex){
				retryCount++;
				if(retryCount>3)
					logger.log(Level.SEVERE, stex.getMessage(), stex);
				else
					logger.log(Level.INFO, "Retrying connect to URL...");
			}
			catch (ClientProtocolException e) {
				retryCount++;
				if(retryCount>3)
					logger.log(Level.SEVERE, e.getMessage(), e);
				else
					logger.log(Level.INFO, "Retrying connect to URL...");
			}
			catch (IOException ioe) {
				retryCount++;
				if(retryCount>3)
					logger.log(Level.SEVERE, ioe.getMessage(), ioe);
				else
					logger.log(Level.INFO, "Retrying connect to URL...");
			}
			finally {
				if(retryCount>3)
					doRetry = false;
			}
		}

		return simpleHTTPResponse;
	}
	
	private String getStringContents(HttpResponse httpResponse) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));
			StringBuffer sb = new StringBuffer();
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				sb.append(responseLine);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException("Unable to get response from server!");
		}
	}
}
