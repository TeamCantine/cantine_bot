package com.telegram.api;
import java.sql.ResultSet;
import java.sql.Statement;
import static com.telegram.connection.StatementFactory.newReadOnlyStatement;

public class UserHelper {


public static String getUser(String id){

    try(Statement st = newReadOnlyStatement()){
        try(ResultSet rs = st.executeQuery("SELECT * FROM WRKJEXP.ROLE_USER WHERE BOT_ID = '" + id + "'")){
    if(rs.next()) {
        System.out.println(rs.getString("USER_NAME"));

        return rs.getString("USER_NAME");
    }
        }
}         catch(Exception e){
         e.printStackTrace();
     }

    return null;
}


}
