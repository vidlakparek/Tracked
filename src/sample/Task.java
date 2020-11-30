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
    public static ArrayList tasks;

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
    public static String getName(Object a){
        return name;
    }
    public static void addTask(){

            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection conn = Controller.getConnection();
            try {
                PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks");
                ResultSet vysledky = dotaz.executeQuery();
                    if (!vysledky.next()) {
                        ID = vysledky.getInt(1);
                        name = vysledky.getString(2);
                        System.out.println(name);
                        desc = vysledky.getString(3);
                        sh_desc = vysledky.getString(4);
                        deadline = vysledky.getDate(5);
                        priority = vysledky.getInt(6);
                        dir_users = vysledky.getString(7);
                        groups = vysledky.getInt(8);
                        stav = vysledky.getBoolean(9);
                        tasks.add(new Task(ID,name,desc,sh_desc,deadline,priority,dir_users,groups,stav));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


    }
    public static void getNameFromArray(int index){

        System.out.println(getName(tasks.get(index)));
    }
}
