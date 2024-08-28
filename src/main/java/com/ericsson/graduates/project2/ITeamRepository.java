package com.ericsson.graduates.project2;

import java.util.Collection;

public interface ITeamRepository {

    Team getTeam(int id );
    Collection<Team> getTeams();

    void insertTeam(Team team);
    Collection<Member> getTeamMembers(int teamId);
    void updateTeam(Team team);
    void deleteTeam(int id);

}
