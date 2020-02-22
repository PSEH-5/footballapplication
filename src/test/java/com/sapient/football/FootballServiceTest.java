package com.sapient.football;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class FootballServiceTest {

	@InjectMocks
	FootballService footBallService = new FootballService();

	@Mock
	private RestTemplate restTemplate;
	
	List<LinkedHashMap<Object, Object>> abc;
	

	@BeforeEach
	void setUp() throws Exception {
		List<Country> countryList = new ArrayList<>();
		countryList = new ArrayList<Country>();
		Country country = new Country();
		country.setCountry_id("1234");
		country.setCountry_name("INDIA");
		countryList.add(country);
		LinkedHashMap<Object, Object> abc = new LinkedHashMap<>();;
		abc.put(HttpStatus.OK, countryList);
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetCountryList() {
		String apikey = "testapi9bb66184e0c8145384fd2cc0f7b914";
		ResponseEntity<Object> valuesList = new ResponseEntity<Object>(abc,HttpStatus.OK);
		when(restTemplate.getForEntity("https://apiv2.apifootball.com/?action=get_countries&APIkey="+apikey, Object.class)).thenReturn(valuesList);
		List<Country> countryList = footBallService.countryList(apikey);
		assertNotNull(countryList);
	}

}
