package sample;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static ArrayList<Task> tasks;

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

    public static String getDir_users(Object a){
        return dir_users;
    }

    public static int getGroups(Object a){
        return groups;
    }

    public static boolean getStav(Object a){
        return stav;
    }

    public static String getNameFromArray(int index){
        return getName(tasks.get(index));
    }

    public static String getDescFromArray(int index){
        return getDesc(tasks.get(index));
    }

    public static Date getDeadlineFromArray(int index){
        return getDeadLine(tasks.get(index));
    }

    public static int getPriorityFromArray(int index){
        return getPriority(tasks.get(index));
    }

    public static String getDir_usersFromArray(int index){
        return getDir_users(tasks.get(index));
    }

    public static int getGroupsFromArray(int index){
        return getGroups(tasks.get(index));
    }

    public static boolean getStavFromArray(int index){
        return getStav(tasks.get(index));
    }

    public static void addTask(){
        tasks = new ArrayList<Task>();
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection conn = Controller.getConnection();
            try {
                PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks");
                ResultSet vysledky = dotaz.executeQuery();
                    while (vysledky.next()) {
                        ID = vysledky.getInt(1);
                        name = vysledky.getString(2);
                        System.out.println(name);
                        desc = vysledky.getString(3);
                        deadline = vysledky.getDate(4);
                        priority = vysledky.getInt(5);
                        dir_users = vysledky.getString(6);
                        groups = vysledky.getInt(7);
                        stav = vysledky.getBoolean(8);
                        tasks.add(new Task(ID,name,desc,deadline,priority,dir_users,groups,stav));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


    }

    public static int getLength(){
        return tasks.size();
    }
}
