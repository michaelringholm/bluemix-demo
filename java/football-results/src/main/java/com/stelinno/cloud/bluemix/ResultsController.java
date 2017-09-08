package com.stelinno.cloud.bluemix;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public class ResultsController {
	private static final Logger logger = Logger.getLogger(ResultsController.class.getName());
	@Autowired private String version;

	/***
	 * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"id":"5118084088070144","name":"Sports Results 97","domain":"Sports", "subDomain":"Statistics","endpoint":"http://sports-service.azure.com"}' https://search-dot-stelinno-dev.appspot.com/index/add.ctl
	 * curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"id":"5081456606969856","name":"Sports Results 4","domain":"Sports", "subDomain":"Statistics","endpoint":"http://sports-service.azure.com"}' https://search-dot-stelinno-dev.appspot.com/index/add.ctl
	 * curl -v -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"id\":\"5118084088070144\",\"name\":\"Sports Results 97\",\"domain\":\"Sports\", \"subDomain\":\"Statistics\",\"endpoint\":\"http://sports-service.azure.com\"}" https://search-dot-stelinno-dev.appspot.com/index/add.ctl
	 * @param service
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	//@RequestBody Service service
	public String add() {
		return "{}";
	}
	
	
	/***
	 * curl https://search-dot-stelinno-dev.appspot.com/index/delete.ctl?serviceId=5760820306771968
	 * curl http://localhost:8080/index/delete.ctl?serviceId=5760820306771968
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public @ResponseBody FootballMatch delete(String serviceId) {
		FootballMatch match = new FootballMatch();
		match.HomeTeam = "Liverpool";
		match.AwayTeam = "Chelsea";
		match.HomeGoals = 2;
		match.AwayGoals = 3;
		match.MatchKickOffTime = new Date();
		match.Referee = "Colina";
		return match;
	}	
	
	@RequestMapping(value="/version", method=RequestMethod.GET)
	public String version(String serviceId) {
		return version;
	}	
}
