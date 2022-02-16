package com.telegram.api;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.telegram.connection.StatementFactory.newReadOnlyStatement;


public class Taskhelper {



    public static ArrayList<String> getTaskDetails(String id){

        String sql = "SELECT\n" +
                "\tA.TIPO_OP,\n" +
                "\tB.ROW_TYPE ,B.CODE_VASE ,B.QUANTITY \n" +
                "FROM\n" +
                "\twrkjexp.ROLE_HEAD AS A\n" +
                "JOIN wrkjexp.ROLE_ROW AS B ON\n" +
                "\tA.ID = B.HEAD_ID\n" +
                "WHERE\n" +
                "\tA.OPERATOR = (SELECT AS_USER FROM WRKJEXP.ROLE_USER WHERE BOT_ID =" + id+  ") ";

    //    System.out.println(sql);

        ArrayList<String> arr =   new ArrayList<String>();

        try(Statement st = newReadOnlyStatement()){
            try(ResultSet rs = st.executeQuery(sql)){
                while(rs.next()) {

String msg = "(" + rs.getString("TIPO_OP") + ") "
        + rs.getString("ROW_TYPE")  + " " +
        rs.getString("CODE_VASE") + " " + rs.getString("QUANTITY");
                   System.out.println(msg);
                    arr.add(msg);
                }
            }
        }         catch(Exception e){
            e.printStackTrace();
        }



        return arr;
    }


    public static ArrayList<String> getMyTask(String id){

        String sql = "SELECT * FROM WRKJEXP.ROLE_HEAD WHERE OPERATOR = (SELECT AS_USER FROM WRKJEXP.ROLE_USER WHERE BOT_ID=" + id + ")";

        //    System.out.println(sql);

        ArrayList<String> arr =   new ArrayList<String>();

        try(Statement st = newReadOnlyStatement()){
            try(ResultSet rs = st.executeQuery(sql)){
                while(rs.next()) {

                    String msg =rs.getString("ID") +  ")      " + rs.getString("CANTINA") + " "
                            + rs.getString("TIPO_OP");

                    System.out.println(msg);
                    arr.add(msg);
                }
            }
        }         catch(Exception e){
            e.printStackTrace();
        }



        return arr;
    }


    //

    public static  ArrayList<String>  getMyTaskDetail(String taskId){

        String sql = "SELECT A.TIPO_OP , B.ROW_TYPE ,B.CODE_VASE, B.QUANTITY FROM wrkjexp.ROLE_HEAD AS A JOIN wrkjexp.ROLE_ROW AS B ON A.ID = B.HEAD_ID WHERE A.ID =" + taskId + "";

          //  System.out.println(sql);

        ArrayList<String> arr =   new ArrayList<String>();

        try(Statement st = newReadOnlyStatement()){
            try(ResultSet rs = st.executeQuery(sql)){
                while(rs.next()) {

                    String msg =rs.getString("TIPO_OP") +  ")      " + rs.getString("ROW_TYPE") + " "
                            + rs.getString("CODE_VASE") + " "+  rs.getString("QUANTITY")  ;

                //    System.out.println(msg);
                    arr.add(msg);
                }
            }
        }         catch(Exception e){
            e.printStackTrace();
        }



        return arr;
    }





}
