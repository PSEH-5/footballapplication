package com.sapient.football;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<Country> countryList(String apikey) {
		List<Country> countryList = new ArrayList<>();
		ResponseEntity<Object> valuesList = restTemplate.getForEntity("https://apiv2.apifootball.com/?action=get_countries&APIkey="+apikey, Object.class);
		System.out.println(valuesList);
		List<LinkedHashMap<Object, Object>> values =  (List<LinkedHashMap<Object, Object>>) valuesList.getBody();
		if(!CollectionUtils.isEmpty(values)) {
			for (LinkedHashMap<Object, Object> linkedHashMap : values) {
				Country country = new Country();
				 country.setCountry_id(linkedHashMap.get("country_id").toString());
				 country.setCountry_name(linkedHashMap.get("country_name").toString());
				 countryList.add(country);
				 
			}	
		}
		
		return countryList;
	}

	public List<League> getLeaguesBasedOnCountryName(String apikey, String country_name) {
		List<Country> countryList = countryList(apikey);
		List<League> leagueList = new ArrayList<>();
		String country_id = null;
		for (Country country : countryList) {
			if(country.getCountry_name().equals(country_name)) {
				country_id = country.getCountry_id();
			}
		}
		//Get League Ids for a comtry Name/country id
		if(!StringUtils.isEmpty(country_id)) {
			System.out.println("country_id="+country_id);
			ResponseEntity<Object> valuesList = restTemplate.getForEntity("https://apiv2.apifootball.com/?action=get_leagues&APIkey="+apikey+"&country_id="+country_id, Object.class);
			System.out.println(valuesList);
			List<LinkedHashMap<Object, Object>> values =  (List<LinkedHashMap<Object, Object>>) valuesList.getBody();
			for (LinkedHashMap<Object, Object> linkedHashMap : values) {
				League league = new League();
				league.setCountry_id(linkedHashMap.get("country_id").toString());
				league.setCountry_name(linkedHashMap.get("country_name").toString());
				league.setLeague_id(linkedHashMap.get("league_id").toString());
				league.setLeague_name(linkedHashMap.get("league_name").toString());
				leagueList.add(league);
				 
			}
		}
		return leagueList;
	}

	public List<Standing> getStandingsBasedOnCountryAndLeague(String apikey, String country_name, 
			String league_name, String team_name) {
		List<Standing> standingList = new ArrayList<>();
		List<League> leagueList = getLeaguesBasedOnCountryName(apikey,country_name);
		String league_id=null;
		Map<String, String> leagueToCountryId = new HashMap<>();
		for (League league : leagueList) {
			if(league.getCountry_name().equals(country_name) && league.getLeague_name().equals(league_name)) {
				//If both countryName and leagueName matches do an API call to standings based on leagueId
				league_id = league.getLeague_id();
				leagueToCountryId.put(league.getLeague_id(), league.getCountry_id());
			}
		}
		
		if(!StringUtils.isEmpty(league_id)) {
			//standings call
			ResponseEntity<Object> valuesList = restTemplate.getForEntity("https://apiv2.apifootball.com/?action=get_standings&APIkey="+apikey+"&league_id="+league_id, Object.class);			
			System.out.println(valuesList);
			List<LinkedHashMap<Object, Object>> values =  (List<LinkedHashMap<Object, Object>>) valuesList.getBody();
			for (LinkedHashMap<Object, Object> linkedHashMap : values) {
				if(linkedHashMap.get("country_name").toString().equals(country_name)
						&& linkedHashMap.get("league_name").toString().equals(league_name)
						&& linkedHashMap.get("team_name").toString().equals(team_name)) {
					Standing standing = new Standing();
					standing.setCountry_id(leagueToCountryId.get(linkedHashMap.get("league_id").toString()));
					standing.setCountry_name(linkedHashMap.get("country_name").toString());
					standing.setLeague_id(linkedHashMap.get("league_id").toString());
					standing.setLeague_name(linkedHashMap.get("league_name").toString());
					standing.setTeam_id(linkedHashMap.get("team_id").toString());
					standing.setTeam_name(linkedHashMap.get("team_name").toString());
					standing.setOverall_league_position(linkedHashMap.get("overall_league_position").toString());
					standingList.add(standing);
				}
			}
		}
		return standingList;
	}

	public List<Team> getTeamListBasedOnLeagueId(String apikey, String league_id) {
		// TODO Auto-generated method stub
		List<Team> teamList = new ArrayList<>();
		ResponseEntity<Object> Teamvalues = restTemplate.getForEntity("https://apiv2.apifootball.com/?action=get_teams&APIkey="+apikey+"&league_id="+league_id, Object.class);			
		System.out.println(Teamvalues);
		List<LinkedHashMap<Object, Object>> teamValues =  (List<LinkedHashMap<Object, Object>>) Teamvalues.getBody();
		for (LinkedHashMap<Object, Object> linkedHashMap : teamValues) {
			Team team = new Team();
			team.setTeam_id(linkedHashMap.get("team_key").toString());
			team.setTeam_name(linkedHashMap.get("team_name").toString());
			teamList.add(team);
		}
		return teamList;
	}
	
	
}
