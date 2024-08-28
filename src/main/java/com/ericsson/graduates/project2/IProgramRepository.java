package com.ericsson.graduates.project2;
import java.util.Collection;

public interface IProgramRepository {
    Program getProgram(int id );
    Collection<Program> getPrograms();

    void insertProgram(Program program);
    Collection<Team> getProgramTeam(int teamId);
    Collection<Member> getProgramTeamMembers(int teamId);

    void updateProgram(Program program);
    void deleteProgram(int id);

}
