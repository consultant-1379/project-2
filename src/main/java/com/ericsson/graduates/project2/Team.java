package com.ericsson.graduates.project2;

import org.springframework.data.annotation.Id;

import java.util.Collection;

public class Team {
    @Id
    private int id;
    private String name;
    private Collection<Member> memberList;


    public Team(int id, String name, Collection<Member> memberList) {
        this.id = id;
        this.name = name;
        this.memberList = memberList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Collection<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(Collection<Member> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
