package com.telegram.api;

import com.telegram.connection.StatementFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.telegram.connection.StatementFactory.newReadOnlyStatement;

public class PendingHelper {

    //cancellare
    public static Pending getPendingTask(String telId){

        String sql = "SELECT * FROM WRKJEXP.ROLE_PENDING_TASK WHERE IDTEL =" + telId;

        try (Statement st = newReadOnlyStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    //String msg = rs.getString("CODE_VASE");
                    //    System.out.println(msg);
                 return  new Pending(rs.getString("IDTEL"), rs.getString("TASKID"),
                            rs.getString("OPERATON"), rs.getString("OLDVALUE"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //cancellare
    public static boolean deletePendingTask(String telId){

        String sql = "DELETE FROM WRKJEXP.ROLE_PENDING_TASK WHERE IDTEL =" + telId;

        try (Statement st = newReadOnlyStatement()) {
            st.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void insertPending(String idTelegram, String taskId, String operation, String oldValue){
        // INSERIMENTO RECORD
        String ins = "INSERT INTO WRKJEXP.ROLE_PENDING_TASK"  + "  VALUES(?,?,?,?)";

        // Creo statement
        try (PreparedStatement st = StatementFactory.newPreparedStatement(ins)) {

            st.setString(1, idTelegram);
            st.setString(2, taskId);
            st.setString(3, operation);
            st.setString(4, oldValue);

            st.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
