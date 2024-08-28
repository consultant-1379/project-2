package com.ericsson.graduates.project2;

import java.util.Collection;
import java.util.List;

public class Program {
    private int id;
    private String name;
    private Collection<Team> teamsList;

    public Program(int id, String name, List<Team> teamsList) {
        this.id = id;
        this.name = name;
        this.teamsList = teamsList;
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

    public Collection<Team> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(Collection<Team> teamsList) {
        this.teamsList = teamsList;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamsList=" + teamsList +
                '}';
    }
}
