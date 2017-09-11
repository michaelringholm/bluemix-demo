package com.stelinno.cloud.bluemix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.stelinno.http.HTTPHelper;
import com.stelinno.http.SimpleHTTPResponse;

public class BoldDKFootballResultsCrawler implements FootballResultsCrawler {
	@Autowired private HTTPHelper httpHelper;	
	private List<String> urls = new ArrayList<>();

	public BoldDKFootballResultsCrawler() {
		urls.add("http://www.bold.dk/fodbold/resultater/gaarsdagens/");
	}
	
	@Override
	public List<FootballMatch> crawl() {
		List<FootballMatch> matches = new ArrayList<>();
		Document doc = null;
		
		for(String url : urls) {
			SimpleHTTPResponse response = httpHelper.getHtml(url, null);
			doc = Jsoup.parse(response.payload.toString());
			Elements matchElements = doc.select(".matches .livematch");
			for(Element matchElement : matchElements) {
				try {
					String homeTeam = matchElement.select(".club1name").text().trim();
					String awayTeam = matchElement.select(".club2name").text().trim();
					String result = matchElement.select(".score").text().trim().replaceAll("[^\\x00-\\x7F]", " ");
					String time = matchElement.select(".game_time").text().trim();
					int timeSeparatorIndex = time.indexOf(":");
					int hours = Integer.parseInt(time.substring(0, timeSeparatorIndex).trim());
					int minutes = Integer.parseInt(time.substring(timeSeparatorIndex+1).trim());
					Calendar kickOffTime = Calendar.getInstance();
					kickOffTime.add(Calendar.DAY_OF_YEAR, -1);
					kickOffTime.set(Calendar.HOUR_OF_DAY, hours);
					kickOffTime.set(Calendar.MINUTE, minutes);
					kickOffTime.set(Calendar.SECOND, 0);
					FootballMatch match = new FootballMatch();
					match.HomeTeam = homeTeam;
					match.AwayTeam = awayTeam;
					int scoreSeparatorIndex = result.indexOf(" ");
					match.HomeGoals = Integer.parseInt(result.substring(0, scoreSeparatorIndex).trim());
					match.AwayGoals = Integer.parseInt(result.substring(scoreSeparatorIndex+1).trim());
					match.MatchKickOffTime = kickOffTime.getTime();
					matches.add(match);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
		return matches;
	}

}
