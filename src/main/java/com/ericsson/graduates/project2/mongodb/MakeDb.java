package com.ericsson.graduates.project2.mongodb;
import com.ericsson.graduates.project2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class MakeDb {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Bean("team_init")
    @PostConstruct
    public void init()
    {
        teamRepository.save(new Team(1, "Cygnus", new ArrayList<>(Arrays.asList(
                memberRepository.memberfindByFirstId(1),memberRepository.memberfindByFirstId(3),
                memberRepository.memberfindByFirstId(2),memberRepository.memberfindByFirstId(4)))));
        teamRepository.save(new Team(2, "Mysterion", new ArrayList<>(Arrays.asList(
                memberRepository.memberfindByFirstId(5),memberRepository.memberfindByFirstId(1),
                memberRepository.memberfindByFirstId(6),memberRepository.memberfindByFirstId(4)))));
        teamRepository.save(new Team(3, "Funky Ducks", new ArrayList<>(Arrays.asList(
                memberRepository.memberfindByFirstId(3),memberRepository.memberfindByFirstId(7),
                memberRepository.memberfindByFirstId(4),memberRepository.memberfindByFirstId(2)))));
    }

}
