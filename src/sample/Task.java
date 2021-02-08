package sample;

import java.sql.Timestamp;

public class Task {
    private final int ID;
    private final String name;
    private final String desc;
    private final Timestamp deadline;
    private final int priority;
    private final boolean stav;
    private final String solution;
    private final int group;




    public Task(int ID, String name, String desc, Timestamp deadline, int priority, boolean stav, String solution, int group){
    this.ID = ID;
    this.name = name;
    this.desc = desc;
    this.deadline = deadline;
    this.priority = priority;
    this.stav = stav;
    this.solution = solution;
    this.group = group;

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
    public boolean getStav(){
        return stav;
    }
    public String getSolution(){return solution;}
    public int getGroup(){return group;}
}
