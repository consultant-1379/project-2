package com.ericsson.graduates.project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberRepositoryImplTest {

    @Autowired
    IMemberRepository memberRepository;

    @Autowired
    TestRestTemplate restTemplate;
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
        new Team(2, "TEAM 2", new ArrayList<Member> (){{
            add(members.get(1));
            add(members.get(2));
        }}
        );
        new Team(3, "TEAM 3", new ArrayList<Member> (){{
            add(members.get(1));
            add(members.get(3));
        }}
        );
    }

    @Test
    void testGetAccessWith200RespondForMembers() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/members", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        assertEquals(200,status);
//        assertThat(response.contains("<Collection><item><id>1</id><name>Osama</name></item><item><id>2</id><name>Jack</name></item><item><id>3</id><name>Darragh</name></item><item><id>4</id><name>Shaunak</name></item></Collection>\n"));
    }

    @Test
    void contexLoads() throws Exception {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void testGetMembers() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/members";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("Osama"));

    }

    @Test
    void testAddMember() throws URISyntaxException
    {
        Member memberPost = new Member(8, "TESTING");
        memberRepository.insertMember(memberPost);
        //Verify request succeed
        assertEquals("TESTING" ,memberRepository.getMember(8).getName());
        memberRepository.deleteMember(8);
    }

    @Test
    void testUpdateMember() throws URISyntaxException
    {
        Member memberPost = new Member(1, "TESTINGUpd");
        Member prevMember=memberRepository.getMember(1);
        memberRepository.updateMember(memberPost);
        //Verify request succeed
        assertEquals("TESTINGUpd",memberRepository.getMember(1).getName());
        memberRepository.updateMember(prevMember);

    }






}