package com.ericsson.graduates.project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TeamRepositoryImplTest {

    @Autowired
    ITeamRepository teamRepository;

    List<Member> members;
    List<Team> teams;
    @BeforeEach
    void setup() {
        members = new ArrayList<>();
        members.add(new Member(1,"osama"));
        members.add(new Member(2, "Jack"));
        members.add(new Member(3, "Darragh"));
        members.add(new Member(4, "Shaunak"));

        teams = new ArrayList<>();
        new Team(1, "TEAM 1", members);
        new Team(2, "TEAM 2", new ArrayList (){{
            add(members.get(1));
            add(members.get(2));
        }}
        );
        new Team(3, "TEAM 3", new ArrayList (){{
            add(members.get(1));
            add(members.get(3));
        }}
        );
    }

    @Test
    void testGetAccessWith200RespondForTeam() throws Exception {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8080/teams", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        assertEquals(200,status);
    }



    @Test
    void contexLoads() throws Exception {
        assertThat(teamRepository).isNotNull();
    }

    @Test
    void testGetTeams() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/teams";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("Cygnus"));

    }

    @Test
    void testAddTeam() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/teams";
        Team teamPost = new Team(10, "TESTING", members);
        teamRepository.insertTeam(teamPost);
        //Verify request succeed
        assertEquals("TESTING",teamRepository.getTeam(10).getName());
        teamRepository.deleteTeam(10);
    }

    @Test
    void testGetTeamMembers()
    {
        Team teamPost = new Team(8, "TESTING", members);
        teamRepository.insertTeam(teamPost);
        //Verify request succeed
        assertEquals(4, teamRepository.getTeamMembers(8).size());
    }
    @Test
    void testUpdateTeam()
    {
        Team teampost = new Team(3, "TESTING",members);
        Team currentTeam=teamRepository.getTeam(3);
        teamRepository.updateTeam(teampost);
        //Verify request succeed
        assertEquals("TESTING", teamRepository.getTeam(3).getName());
        teamRepository.updateTeam(currentTeam);
    }


}