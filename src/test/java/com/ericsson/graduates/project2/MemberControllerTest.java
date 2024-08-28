package com.ericsson.graduates.project2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IMemberRepository memberRepository;


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
    void testGetAllMembers()	{
        ResponseEntity<List<Member>> responseEntity = restTemplate.exchange(
                "/members",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Member>>() {});
        List<Member> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(memberRepository.getMembers().size(), responseBody.size());
    }

    @Test
    void testPostDeleteMember()	{
        Member ps = new Member( 10, "test");
        ResponseEntity<Member> responseEntity = restTemplate.postForEntity(
                "/member",
                ps,
                Member.class);

        Member responseBody = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println(responseBody);
        assertEquals("test",memberRepository.getMember(10).getName());
        restTemplate.delete("/member/10");
        assertNull(memberRepository.getMember(10));
    }

}
