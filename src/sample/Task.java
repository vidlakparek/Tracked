package sample;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Task {
    // TODO: 01.01.2021 Vyřešit problém s datumem a časem
    private final int ID;
    private final String name;
    private final String desc;
    private final Timestamp deadline;
    private final int priority;
    private final String dir_users;
    private final String groups;
    private final boolean stav;
    private final String solution;




    public Task(int ID, String name, String desc, Timestamp deadline, int priority, String dir_users, String groups, boolean stav, String solution){
    this.ID = ID;
    this.name = name;
    this.desc = desc;
    this.deadline = deadline;
    this.priority = priority;
    this.dir_users = dir_users;
    this.groups = groups;
    this.stav = stav;
    this.solution = solution;

    }
    public int getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public Timestamp getDeadline(){ return deadline; }
    public int getPriority(){
        return priority;
    }
    public String getDir_users(){
        return dir_users;
    }
    public String getGroups(){
        return groups;
    }
    public boolean getStav(){
        return stav;
    }
    public String getSolution(){return solution;}
}
