package com.sapient.football;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FootballController {

	@Autowired
	private FootballService footballService;
	
	
	@GetMapping(value="/getallcountries")
	public ResponseEntity<List<Country>> getCountryName(@RequestParam(value="APIkey") String apikey) {
		List<Country> countryList  = footballService.countryList(apikey);
		System.out.println(countryList);
		return new ResponseEntity<List<Country>>(countryList, HttpStatus.OK);
	}
	
	@GetMapping(value="/getLeagueDetails")
	public ResponseEntity<List<League>> getLeagueDetails(
			@RequestParam(value="APIkey") String apikey,
			@RequestParam(value="country_name") String country_name) {
		List<League> leagueList  = footballService.getLeaguesBasedOnCountryName(apikey,country_name);
		System.out.println(leagueList);
		return new ResponseEntity<List<League>>(leagueList, HttpStatus.OK);
	}
	
	@GetMapping(value="/getTeamDetails")
	public ResponseEntity<List<Team>> getTeamDetails(
			@RequestParam(value="APIkey") String apikey,
			@RequestParam(value="league_id") String league_id) {
		List<Team> teamList  = footballService.getTeamListBasedOnLeagueId(apikey,league_id);
		return new ResponseEntity<List<Team>>(teamList, HttpStatus.OK);
	}
	
	@GetMapping(value="/getStandingBasedOnCnAndLeagueNameAndTeamName")
	public ResponseEntity<List<Standing>> getStandingBasedOnCnAndLeagueNameAndTeamName(
			@RequestParam(value="APIkey") String apikey,
			@RequestParam(value="country_name") String country_name,
			@RequestParam(value="league_name") String league_name,
			@RequestParam(value="team_name") String team_name) {
		List<Standing> standingList  = footballService.getStandingsBasedOnCountryAndLeague(apikey,country_name,league_name,team_name);
		System.out.println(standingList);
		return new ResponseEntity<List<Standing>>(standingList, HttpStatus.OK);
	}
	
}
