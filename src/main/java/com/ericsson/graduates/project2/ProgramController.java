package com.ericsson.graduates.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ProgramController {

    @Autowired
    private IProgramRepository programRepository;

    // Get all program.
    @GetMapping(value="/programs", produces={"application/json"})
    public Collection<Program> getPrograms() {
        return programRepository.getPrograms();
    }

    // Get a program by id
    @GetMapping(value="/program/{id}", produces={"application/json","application/xml"})
    public Program getProgram(@PathVariable int id) {
        return programRepository.getProgram(id);
    }

    //Get teams work on an actual  program by the program id
    @GetMapping(value="/program/{id}/teams", produces={"application/json"})
    public Collection<Team> getProgramTeams(@PathVariable int id) {
        return programRepository.getProgramTeam(id);
    }

    @GetMapping(value="/program/{id}/teams/members", produces={"application/json"})
    public Collection<Member> getProgramTeamsMembers(@PathVariable int id) {
        return programRepository.getProgramTeamMembers(id);
    }


    // Insert a Program.
    @PostMapping(value="/program",
            consumes={"application/json"},
            produces={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Program addProgram(@RequestBody Program program) {
        programRepository.insertProgram(program);
        return program;
    }


    // Update an existing Program.
    @PutMapping(value="/program/{id}", consumes={"application/json","application/xml"})
    public void modifyProgram(@PathVariable int id, @RequestBody Program program) {
        programRepository.updateProgram(program);
    }

    // Delete an existing Program.
    @DeleteMapping("/program/{id}")
    public void deletePrgram(@PathVariable int id) {
        programRepository.deleteProgram(id);
    }


}
