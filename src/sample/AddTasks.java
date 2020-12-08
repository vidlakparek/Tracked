package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class AddTasks {
    public static ArrayList<Task> tasks;
    //public static Object ukol[] = new Object[100];



    public static void addTask(){

        tasks = new ArrayList<Task>();

        int DID = 0;
        String Dname = null;
        String Ddesc = null;
        Date Ddeadline = null;
        int Dpriority = 0;
        String Ddir_users = null;
        int Dgroups = 0;
        boolean Dstav = false;
        //ukol[0] = new Task(DID,Dname,Ddesc,Ddeadline,Dpriority,Ddir_users,Dgroups,Dstav);
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks");
            ResultSet vysledky = dotaz.executeQuery();

            int i = 0;
            while (vysledky.next()) {
                DID = vysledky.getInt(1);
                Dname = vysledky.getString(2);
                Ddesc = vysledky.getString(3);
                Ddeadline = vysledky.getDate(4);
                Dpriority = vysledky.getInt(5);
                Ddir_users = vysledky.getString(6);
                Dgroups = vysledky.getInt(7);
                Dstav = vysledky.getBoolean(8);
                //ukol [i] = new Task(DID,Dname,Ddesc,Ddeadline,Dpriority,Ddir_users,Dgroups,Dstav);
                tasks.add(i, new Task(DID,Dname,Ddesc,Ddeadline,Dpriority,Ddir_users,Dgroups,Dstav));
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(Task.getAll(tasks.get(0)));
    }

    public static int getLength(){
        return tasks.size();
    }
}
