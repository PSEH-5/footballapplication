package com.sapient.football;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class FootballControllerTest {

	@InjectMocks
	FootballController footBallControllerTest = new FootballController();
	
	@Mock
	private FootballService footballService;
	
	List<Country> countryList;
	
	@BeforeEach
	void setUp() throws Exception {
	
		countryList = new ArrayList<Country>();
		Country country = new Country();
		country.setCountry_id("1234");
		country.setCountry_name("INDIA");
		countryList.add(country);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllcountries() {
		String keyAPI = "testapi9bb66184e0c8145384fd2cc0f7b914";
		when(footballService.countryList(keyAPI)).thenReturn(countryList);
		ResponseEntity<List<Country>> responseList = footBallControllerTest.getCountryName(keyAPI);
		assertNotNull(responseList);
		assertEquals(responseList.getStatusCode(), HttpStatus.OK);
		assertEquals(countryList.get(0).getCountry_id(), responseList.getBody().get(0).getCountry_id());
		assertEquals(countryList.get(0).getCountry_name(), responseList.getBody().get(0).getCountry_name());
			
	}

	@Test
	void testGetTeamDetails() {
		String keyAPI = "testapi9bb66184e0c8145384fd2cc0f7b914";
		String league_id = "2345";
		
		List<Team> teamList = new ArrayList<>();
		Team team = new Team();
		team.setTeam_id("4567");
		team.setTeam_name("team123");
		teamList.add(team);
		when(footballService.getTeamListBasedOnLeagueId(keyAPI,league_id)).thenReturn(teamList);
		ResponseEntity<List<Team>> responseList  = footBallControllerTest.getTeamDetails(keyAPI, league_id);
		assertNotNull(responseList);
		assertEquals(responseList.getStatusCode(), HttpStatus.OK);
		assertEquals(teamList.get(0).getTeam_id(), responseList.getBody().get(0).getTeam_id());
		assertEquals(teamList.get(0).getTeam_name(), responseList.getBody().get(0).getTeam_name());

	}

	@Test
	void testGetStandingBasedOnCnAndLeagueNameAndTeamName() {
		String apikey = "testapi9bb66184e0c8145384fd2cc0f7b914",country_name="India",league_name="league1",team_name="team1";
		List<Standing> standingList = new ArrayList<>();
		Standing standing = new Standing();
		standing.setCountry_id("322");
		standing.setCountry_name("India");
		standing.setLeague_id("345");
		standing.setLeague_name("league1");
		standing.setTeam_name("team1");
		standingList.add(standing);
		when(footballService.getStandingsBasedOnCountryAndLeague(apikey, country_name, league_name, team_name)).thenReturn(standingList );
		ResponseEntity<List<Standing>> responseList= footBallControllerTest.getStandingBasedOnCnAndLeagueNameAndTeamName(apikey, 
				country_name, league_name, team_name);
		assertNotNull(responseList);
		assertEquals(responseList.getStatusCode(), HttpStatus.OK);
		assertEquals(standingList.get(0).getTeam_id(), responseList.getBody().get(0).getTeam_id());
		assertEquals(standingList.get(0).getTeam_name(), responseList.getBody().get(0).getTeam_name());
		assertEquals(standingList.get(0).getCountry_id(), responseList.getBody().get(0).getCountry_id());
		assertEquals(standingList.get(0).getCountry_name(), responseList.getBody().get(0).getCountry_name());
		assertEquals(standingList.get(0).getLeague_id(), responseList.getBody().get(0).getLeague_id());
		assertEquals(standingList.get(0).getLeague_name(), responseList.getBody().get(0).getLeague_name());

	}

	@Test
	void testGetStandingBasedOnCnAndLeagueNameAndTeamNameForInvalidFilter() {
		String apikey = "testapi9bb66184e0c8145384fd2cc0f7b914",country_name="India",league_name="league1",team_name="team1";
		List<Standing> standingList = new ArrayList<>();
		Standing standing = new Standing();
		standing.setCountry_id("322");
		standing.setCountry_name("India");
		standing.setLeague_id("345");
		standing.setLeague_name("league1");
		standing.setTeam_name("team1");
		standingList.add(standing);
		when(footballService.getStandingsBasedOnCountryAndLeague(apikey, country_name, league_name, team_name)).thenReturn(standingList );
		ResponseEntity<List<Standing>> responseList= footBallControllerTest.getStandingBasedOnCnAndLeagueNameAndTeamName(apikey, 
				country_name, league_name, "team2");
		assertNotNull(responseList);
		assertEquals(responseList.getStatusCode(), HttpStatus.OK);
		
	}
}
