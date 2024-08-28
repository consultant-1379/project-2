package com.ericsson.graduates.project2;
import com.ericsson.graduates.project2.mongodb.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberRepositoryImp implements IMemberRepository {
    private int nextId = 1;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member getMember(int id) {
        Optional<Member> memberExists=memberRepository.findById(id);
        return memberExists.orElse(null);
    }

    @Override
    public List<Member> getMembers() {
        return  memberRepository.findAll();
    }

    @Override
    public void insertMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void updateMember(Member member) {
        int id = member.getId();
        if (memberRepository.findById(id).isPresent()) {
        memberRepository.save(member);
        }
    }

    @Override
    public void deleteMember(int id)  {
        Member item = this.getMember(id);
        if (item != null) {
            memberRepository.delete(item);
        }
    }
}
