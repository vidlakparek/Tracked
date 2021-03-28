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


    /**
     * Parametrický konstruktor objektu Task
     * @param ID
     * @param name
     * @param desc
     * @param deadline
     * @param priority
     * @param stav
     * @param solution
     * @param group
     */
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

    /**
     * Getter pro parametr ID
     * @return
     */
    public int getID(){
        return ID;
    }

    /**
     * Getter pro parametr Name
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Getter pro parametr Desc
     * @return
     */
    public String getDesc(){
        return desc;
    }

    /**
     * Getter pro parametr Deadline
     * @return
     */
    public Timestamp getDeadline(){ return deadline; }

    /**
     * Getter pro parametr Priority
     * @return
     */
    public int getPriority(){
        return priority;
    }

    /**
     * Getter pro parametr Stav
     * @return
     */
    public boolean getStav(){
        return stav;
    }

    /**
     * Getter pro parametr Solution
     * @return
     */
    public String getSolution(){return solution;}

    /**
     * Getter pro parametr Group
     * @return
     */
    public int getGroup(){return group;}
}
