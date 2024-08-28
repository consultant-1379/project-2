package com.ericsson.graduates.project2;

import com.ericsson.graduates.project2.mongodb.MakeDb;
import com.ericsson.graduates.project2.mongodb.MemberMakeDB;
import com.ericsson.graduates.project2.mongodb.MemberRepository;
import com.ericsson.graduates.project2.mongodb.TeamRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoDbTestsForTeamRepositories {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MakeDb makeDb;

    @Autowired
    MemberMakeDB memberMakeDB;

    @Test
    public void testThatTeamRepositoryInitializedProperly()
    {
        makeDb.init();
        Optional<Team> teamOptional=teamRepository.findById(1);
        assertTrue(teamOptional.isPresent());
        assertEquals("Cygnus",teamOptional.get().getName());

    }

    @Test
    public void testThatMemberRepositoryInitialized()
    {
        memberMakeDB.init();
        Optional<Member> memberOptional=memberRepository.findById(1);
        assertTrue(memberOptional.isPresent());
        assertEquals("Darragh",memberOptional.get().getName());
    }

}
