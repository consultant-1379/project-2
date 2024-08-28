package com.ericsson.graduates.project2.mongodb;
import com.ericsson.graduates.project2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MemberMakeDB {

    @Autowired
    private MemberRepository memberRepository;

    @Bean("member_init")
    @PostConstruct
    public void init() {
        memberRepository.save(new Member(1, "Darragh"));
        memberRepository.save(new Member(2, "Shaunak"));
        memberRepository.save(new Member(3, "Osama"));
        memberRepository.save(new Member(4, "Jack"));
        memberRepository.save(new Member(5, "Oliver"));
        memberRepository.save(new Member(6, "Adam"));
        memberRepository.save(new Member(7, "Jose"));
    }

}

