package com.ericsson.graduates.project2;

import com.ericsson.graduates.project2.mongodb.MemberRepository;
import com.ericsson.graduates.project2.mongodb.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamRepositoryImp implements ITeamRepository {
    private int nextId = 1;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team getTeam(int id) {
        Optional<Team> teamExists=teamRepository.findById(id);
        return teamExists.orElse(null);
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Override
    public void insertTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public Collection<Member> getTeamMembers(int teamId)  {
        Team team = this.getTeam(teamId);
        return team.getMemberList();
    }

    @Override
    public void updateTeam(Team team) {
        int id = team.getId();
        if (teamRepository.findById(id).isPresent()) {
            teamRepository.save(team);
        }
    }

    @Override
    public void deleteTeam(int id) {
        Team item = this.getTeam(id);
        if (item != null) {
            teamRepository.delete(item);
        }
    }

}
