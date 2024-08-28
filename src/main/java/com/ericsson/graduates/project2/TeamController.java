package com.ericsson.graduates.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TeamController {

    @Autowired
    private ITeamRepository teams;


    // Get all Teams.
    @GetMapping(value="/teams", produces={"application/json","application/xml"})
    public Collection<Team> getTeams() {
        return teams.getTeams();
    }

    // Get team by id
    @GetMapping(value="/team/{id}", produces={"application/json","application/xml"})
    public Team getTeam(@PathVariable int id) {
        return teams.getTeam(id);
    }

    // get team members by the team id
    @GetMapping(value="/team/{id}/members", produces={"application/json","application/xml"})
    public Collection<Member> getteamMember(@PathVariable int id) {
        return teams.getTeamMembers(id);
    }


    // Insert a new team.
    @PostMapping(value="/team",
            consumes={"application/json","application/xml"},
            produces={"application/json","application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Team addTeam(@RequestBody Team team) {
        teams.insertTeam(team);
        return team;
    }

    // Update an existing team.
    @PutMapping(value="/team/{id}", consumes={"application/json","application/xml"})
    public void modifyTeam(@PathVariable int id, @RequestBody Team item) {
        teams.updateTeam(item);
    }

    // Delete an existing team.
    @DeleteMapping("/team/{id}")
    public void deleteTeam(@PathVariable int id) {
        teams.deleteTeam(id);
    }
}
