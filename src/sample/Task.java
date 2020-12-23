package sample;

import java.util.Date;

public class Task {
    private final int ID;
    private final String name;
    private final String desc;
    private final Date deadline;
    private final int priority;
    private final String dir_users;
    private final String groups;
    private final boolean stav;




    public Task(int ID, String name, String desc, Date deadline, int priority, String dir_users, String groups, boolean stav){
    this.ID = ID;
    this.name = name;
    this.desc = desc;
    this.deadline = deadline;
    this.priority = priority;
    this.dir_users = dir_users;
    this.groups = groups;
    this.stav = stav;
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
    public Date getDeadline(){ return deadline; }
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
}
