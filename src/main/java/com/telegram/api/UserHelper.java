package com.telegram.api;
import java.sql.ResultSet;
import java.sql.Statement;
import static com.telegram.connection.StatementFactory.newReadOnlyStatement;

public class UserHelper {


public static String getUser(){

    try(Statement st = newReadOnlyStatement()){
        try(ResultSet rs = st.executeQuery("SELECT * FROM WRK90MUL.GCCNT00F")){
    while(rs.next())
                System.out.println(rs.getString("CDCNCN"));
        }
} catch(Exception e){
         e.printStackTrace();
     }

    return "";
}


}
