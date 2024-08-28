package com.ericsson.graduates.project2;

import com.ericsson.graduates.project2.mongodb.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@ComponentScan("com.ericsson.graduates.project2.mongodb")
public class ProgramRepositoryImpl implements IProgramRepository{
    private int nextId = 1;

    // get the members created in the MemberRepositoryImp and assign them to teams
   @Autowired
    TeamRepository teamRepository;

    LinkedList<Team> teamsList1 = new LinkedList<>();
    LinkedList<Team> teamsList2 = new LinkedList<>();

    List<Team> teamsList ;
    private static Map<Integer, Program> programs = new HashMap<>();
    @Bean
    @DependsOn({"member_init","team_init"})
    void initProgramRepositoryImpl() {
    teamsList = (List<Team>) teamRepository.findAll();
    teamsList1.add(teamsList.get(1));
    teamsList1.add(teamsList.get(2));
    teamsList2.add(teamsList.get(1));
    teamsList2.add(teamsList.get(2));

    insertProgram(new Program(0, "PROGRAM 1", teamsList));
    insertProgram(new Program(1, "PROGRAM 2", teamsList1));
    insertProgram(new Program(2, "PROGRAM 3", teamsList2));
}

    @Override
    public Program getProgram(int id) {
        return programs.get(id);
    }

    @Override
    public List<Program> getPrograms() {
        return new ArrayList<>(programs.values());
    }

    @Override
    public void insertProgram(Program program) {
        program.setId(nextId++);
        programs.put(program.getId(),program);
    }

    @Override
    public Collection<Team> getProgramTeam(int teamId) {
        Program program = programs.get(teamId);
        List<Team> teamList = new ArrayList<>();
        if(program.getTeamsList() == null) return teamList;
        teamList.addAll(program.getTeamsList());
        return teamList;
    }

    @Override
    public Collection<Member> getProgramTeamMembers(int teamId) {
        Team team = teamsList.get(teamId);
        List<Member> memberList = new ArrayList<>();
        if(team.getMemberList() == null) return memberList;
        memberList.addAll(team.getMemberList());
        return memberList;
    }

    @Override
    public void updateProgram(Program program) {
        int id = program.getId();
        if (programs.containsKey(id)) {
            programs.put(id, program);
        }

    }


    @Override
    public void deleteProgram(int id) {
        Program item = programs.get(id);
        if (item != null) {
            programs.remove(id);
        }

    }
}