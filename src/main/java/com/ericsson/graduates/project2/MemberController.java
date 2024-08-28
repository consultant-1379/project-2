package com.ericsson.graduates.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MemberController {

    @Autowired
    private IMemberRepository teams;


    // Get all members.
    @GetMapping(value="/members", produces={"application/json","application/xml"})
    public Collection<Member> getMembers() {
        return teams.getMembers();
    }

    // Get the member by id
    @GetMapping(value="/member/{id}", produces={"application/json","application/xml"})
    public Member getMember(@PathVariable int id) {
            return teams.getMember(id);
    }


    // Insert a new Member.
    @PostMapping(value="/member",
            consumes={"application/json","application/xml"},
            produces={"application/json","application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Member addMember(@RequestBody Member member) {
        teams.insertMember(member);
        return member;
    }

    // Update an existing Member.
    @PutMapping(value="/member/{id}", consumes={"application/json","application/xml"})
    public void modifyMember(@PathVariable int id, @RequestBody Member item) {
        teams.updateMember(item);
    }

    // Delete an existing Member.
    @DeleteMapping("/member/{id}")
    public void deleteMember(@PathVariable int id) {
            teams.deleteMember(id);
    }



}
