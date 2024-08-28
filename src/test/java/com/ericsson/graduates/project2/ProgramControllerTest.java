package com.ericsson.graduates.project2;

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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProgramControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    ProgramController programController;


    List<Member> members;
    List<Team> teams;
    List <Program> programs;

    @BeforeEach
    void setup() {
        members = new ArrayList<>();
        members.add(new Member(1,"osama"));
        members.add(new Member(2, "Jack"));
        members.add(new Member(3, "Darragh"));
        members.add(new Member(4, "Shaunak"));

        teams = new ArrayList<>();
        teams.add(new Team(1, "TEAM 1", members));
        teams.add(new Team(2, "TEAM 2", new ArrayList (){{
            add(members.get(1));
            add(members.get(2));
        }}
        ));
        teams.add(new Team(3, "TEAM 3", new ArrayList (){{
            add(members.get(1));
            add(members.get(3));
        }}
        ));

        programs = new ArrayList<>();
        programs.add(new Program(0, "PROGRAM 1",teams ));
        programs.add(new Program(1, "PROGRAM 2",new ArrayList (){{
            add(teams.get(0));
            add(teams.get(1));
        }} ));
        programs.add(new Program(2, "PROGRAM 3",new ArrayList (){{
            add(teams.get(1));
            add(teams.get(2));
        }} ));
    }


    @Test
    void testGetAllPrograms()	{
        ResponseEntity<List<Program>> responseEntity = restTemplate.exchange(
                "/programs",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Program>>() {});
        Collection<Program> responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        System.out.println(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(programController.getPrograms().size(), responseBody.size());
    }

    @Test
    void testGetAllProgramTeamsMembers()	{
        ResponseEntity<List<Program>> responseEntity = restTemplate.exchange(
                "/program/1/teams/members",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Program>>() {});
        Collection<Program> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(programController.getProgramTeamsMembers(1)).isNotEmpty();
    }

    @Test
    void testPostDeleteTeam()	{
        Program ps = new Program( 0, "test", teams);
        ResponseEntity<Program> responseEntity = restTemplate.postForEntity(
                "/program",
                programController.addProgram(ps),
                Program.class);

        Program responseBody = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(programController.getProgram(responseBody.getId()).toString(), responseBody.toString());
        programController.modifyProgram(0,ps);
        programController.deletePrgram(responseBody.getId());
        assertNull(programController.getProgram(responseBody.getId()));
    }

}
