package sample;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Task {
    static int ID;
    static String name;
    static String desc;
    static Date deadline;
    static int priority;
    static String dir_users;
    static int groups;
    static boolean stav;



    public Task(int ID, String name, String desc, Date deadline, int priority, String dir_users, int groups, boolean stav){
    Task.ID = ID;
    Task.name = name;
    Task.desc = desc;
    Task.deadline = deadline;
    Task.priority = priority;
    Task.dir_users = dir_users;
    Task.groups = groups;
    Task.stav = stav;
    }

    public static String getName(Object a){
        return name;
    }

    public static String getDesc(Object a){
        return desc;
    }

    public static Date getDeadLine(Object a){
        return deadline;
    }

    public static int getPriority(Object a){
        return priority;
    }

    public static String getDir_users(Object a){return dir_users;}

    public static String getAll(Task a){return name + desc;}

    public static int getGroups(Object a){
        return groups;
    }

    public static boolean getStav(Object a){
        return stav;
    }

    public static String getNameFromArray(int index){
        return getName(AddTasks.tasks.get(index));
    }

    public static String getDescFromArray(int index){
        return getDesc(AddTasks.tasks.get(index));
    }

    public static Date getDeadlineFromArray(int index){
        return getDeadLine(AddTasks.tasks.get(index));
    }

    public static int getPriorityFromArray(int index){
        return getPriority(AddTasks.tasks.get(index));
    }

    public static String getDir_usersFromArray(int index){
        return getDir_users(AddTasks.tasks.get(index));
    }

    public static int getGroupsFromArray(int index){
        return getGroups(AddTasks.tasks.get(index));
    }

    public static boolean getStavFromArray(int index){
        return getStav(AddTasks.tasks.get(index));
    }

    public static String getAllfromArray(int index){return getAll(AddTasks.tasks.get(index));}
}
