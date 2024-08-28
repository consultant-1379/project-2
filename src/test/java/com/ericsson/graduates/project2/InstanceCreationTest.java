package com.ericsson.graduates.project2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;

class InstanceCreationTest {

    @Autowired
    IProgramRepository programRepository;

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
    void testTheMemberCreation(){
        Member member1 = members.get(0);
        assertEquals(1 , member1.getId());
        assertEquals("osama" , member1.getName());
        assertEquals(true, member1.toString().contains("osama"));
    }

    @Test
    void testTheTeamCreation(){
        Team team = new Team(1, "TEAM", members);
        assertEquals(1 , team.getId());
        assertEquals("TEAM" , team.getName());
        assertEquals(true, team.toString().contains("TEAM"));

    }

    @Test
    void testTheTeamMembersReturn(){
        Team team = new Team(1, "TEAM", new ArrayList (){{
            add(members.get(1));
            add(members.get(2));
        }});
        assertEquals(new ArrayList (){{
            add(members.get(1));
            add(members.get(2));
        }}, team.getMemberList());
        team.setName("TESTNAME");
        assertEquals("TESTNAME", team.getName());
        team.setMemberList(members);
        assertEquals(members, team.getMemberList());

    }

    @Test
    void testTheProgramCreation(){
        Program program = new Program(1,"PROGRAM",teams);
        assertEquals(1 , program.getId());
        assertEquals("PROGRAM" , program.getName());
        program.setName("TESTNAME");
        program.setTeamsList(teams);
        assertEquals("TESTNAME", program.getName());
        assertEquals(true, program.toString().contains("TESTNAME"));
        assertEquals(teams,program.getTeamsList());

    }

    @Test
    void testTheProgramTeamsReturn(){
        Program program = new Program(1,"PROGRAM",teams);
        assertEquals(teams, program.getTeamsList());
    }

}
