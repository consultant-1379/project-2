package com.ericsson.graduates.project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProgramRepositoryImplTest {

    @Autowired
    IProgramRepository programRepository;

    List<Member> members;
    List<Team> teams;
    List<Program> programs;
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
        programs.add(new Program(1, "PROGRAM 2", new ArrayList(){{
            add(teams.get(0));
            add(teams.get(1));
        }}
        ));

        programs.add(new Program(2, "PROGRAM 3", new ArrayList (){{
            add(teams.get(0));
            add(teams.get(1));
        }}
        ));
    }

    @Test
    void testGetAccessWith200RespondForProgram() throws Exception {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8080/programs", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        assertEquals(200,status);
    }

    @Test
    void contexLoads() throws Exception {
        assertThat(programRepository).isNotNull();
    }

    @Test
    void testGetProgram() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/programs";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("PROGRAM 1"));

    }
    @Test
    void testGetProgramTeam() throws URISyntaxException {
        Program teampost = new Program(1, "TESTING",teams);
        programRepository.insertProgram(teampost);
        //Verify request succeed
        assertEquals(false, programRepository.getProgramTeam(1).contains(teams));

    }

    @Test
    void testGetProgramTeamMembers() throws URISyntaxException {
        Program teampost = new Program(1, "TESTING",teams);
        programRepository.insertProgram(teampost);
        //Verify request succeed
        assertEquals(false, programRepository.getProgramTeamMembers(1).contains(members));

    }
    @Test
    void testUpdateProgram()
    {
        Program program = new Program(1, "TESTING",teams);
        programRepository.updateProgram(program);
        //Verify request succeed
        assertEquals(true, programRepository.getPrograms().contains(program));
        programRepository.deleteProgram(1);
        assertEquals(false, programRepository.getPrograms().contains(program));


    }


    @Test
    void testAddProgram() throws URISyntaxException
    {
        Program programPost = new Program(1, "TESTING", teams);
        programRepository.insertProgram(programPost);
        //Verify request succeed
        assertEquals(true, programRepository.getPrograms().contains(programPost));
    }
}