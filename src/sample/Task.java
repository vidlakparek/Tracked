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
     * Konstruktor vytvoří objekt Task.
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
     * Metoda vrátí ID úkolu.
     * @return ID
     */
    public int getID(){
        return ID;
    }
    /**
     * Metoda vrátí název úkolu.
     * @return name
     */
    public String getName(){
        return name;
    }
    /**
     * Metoda vrátí popis úkolu.
     * @return desc
     */
    public String getDesc(){
        return desc;
    }
    /**
     * Metoda vrátí čas odevzdání úkolu.
     * @return deadline
     */
    public Timestamp getDeadline(){
        return deadline;
    }
    /**
     * Metoda vrátí prioritu úkolu.
     * @return priority
     */
    public int getPriority(){
        return priority;
    }
    /**
     * Metoda vrátí stav úkolu.
     * @return stav
     */
    public boolean getStav(){
        return stav;
    }
    /**
     * Metoda vrátí řešení úkolu.
     * @return solution
     */
    public String getSolution(){
        return solution;
    }
    /**
     * Metoda vrátí skupinu pro kterou byl úkol zadán.
     * @return group
     */
    public int getGroup(){
        return group;
    }
}
