package com.telegram.api;
import com.telegram.connection.StatementFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.telegram.connection.StatementFactory.newReadOnlyStatement;

public class UserHelper {


public static String getUser(String id, Update update){

    try(Statement st = newReadOnlyStatement()){
        try(ResultSet rs = st.executeQuery("SELECT * FROM WRKJEXP.ROLE_USER WHERE BOT_ID = '" + id + "'")){
    if(rs.next()) {
        System.out.println(rs.getString("USER_NAME"));
        if(rs.getString("STATUS").equals("C")){
            return rs.getString("USER_NAME");
        }
        else
            return null;
    }
        }
}         catch(Exception e){
         e.printStackTrace();
     }

    // se non esiste , lo inserisco
    insertUser(update);
    return "inserito";
}


private static void insertUser(Update update){
    // INSERIMENTO RECORD
    String ins = "INSERT INTO WRKJEXP.ROLE_USER"  + " (BOT_ID,STATUS,USER_NAME,USER_SURNAME,AS_USER  ) VALUES(?,?,?,?,?)";

    // Creo statement
    try (PreparedStatement st = StatementFactory.newPreparedStatement(ins)) {


        String name = update.getMessage().getChat().getFirstName();
        String surname = update.getMessage().getChat().getLastName();
        System.out.println(name);
        // Chiavi
        st.setString(1, update.getMessage().getChatId().toString());
        st.setString(2, "C");
        st.setString(3, name);
        st.setString(4, surname);

        if(name==null || surname == null){
            st.setString(5, "WRKANDVIC");
        }
        else
        st.setString(5, ("WRK"+name.substring(0,3).toUpperCase()+surname.substring(0,3).toUpperCase()) );


        st.execute();

    } catch (Exception e) {
        e.printStackTrace();
    }
}






}
