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
    static String sh_desc;
    static Date deadline;
    static int priority;
    static String dir_users;
    static int groups;
    static boolean stav;
    public static ArrayList<Task> tasks;

    public Task(int ID, String name, String desc, String sh_desc, Date deadline, int priority, String dir_users, int groups, boolean stav){
    this.ID = ID;
    this.name = name;
    this.desc = desc;
    this.sh_desc = sh_desc;
    this.deadline = deadline;
    this.priority = priority;
    this.dir_users = dir_users;
    this.groups = groups;
    this.stav = stav;
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
                        int ID_g = vysledky.getInt(1);
                        String name_g = vysledky.getString(2);
                        String desc_g = vysledky.getString(3);
                        String sh_desc_g = vysledky.getString(4);
                        System.out.println(name_g);
                        Date deadline_g = vysledky.getDate(5);
                        int priority_g = vysledky.getInt(6);
                        String dir_users_g = vysledky.getString(7);
                        int groups_g = vysledky.getInt(8);
                        boolean stav_g = vysledky.getBoolean(9);
                        tasks.add(new Task(ID_g,name_g,desc_g,sh_desc_g,deadline_g,priority_g,dir_users_g,groups_g,stav_g));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


    }
    public static String getNameFromArray(int index){
        return getName(tasks.get(index));
    }
    public static String getName(Object a){
        return name;
    }
}
