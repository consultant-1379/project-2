package com.ericsson.graduates.project2;

import com.ericsson.graduates.project2.mongodb.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;


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
    void testGetAllTeam()	{
        ResponseEntity<List<Team>> responseEntity = restTemplate.exchange(
                "/teams",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Team>>() {});
        List<Team> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(teamRepository.getTeams().size(), responseBody.size());
    }
    @Test
    void testGetAllTeamMembers()	{
        ResponseEntity<List<Team>> responseEntity = restTemplate.exchange(
                "/team/1/members",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Team>>() {});
        List<Team> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(teamRepository.getTeamMembers(1).size(), responseBody.size());
    }

    @Test
    void testPostDeleteTeam()	{
        Team ps = new Team( 10, "test", members);
        ResponseEntity<Team> responseEntity = restTemplate.postForEntity(
                "/team",
                ps,
                Team.class);

        Team responseBody = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println(responseBody);
        assertEquals("test",teamRepository.getTeam(10).getName());
        restTemplate.delete("/team/10");
        assertNull(teamRepository.getTeam(10));
    }

}
