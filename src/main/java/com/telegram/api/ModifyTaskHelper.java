package com.telegram.api;

import com.telegram.connection.StatementFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.telegram.connection.StatementFactory.newReadOnlyStatement;

public class ModifyTaskHelper {

    //cancellare
    public static List<String> getVasoFromTask(String taskId){
        List<String> arr = new ArrayList<String>();


        String sql = "SELECT * FROM WRKJEXP.ROLE_ROW WHERE HEAD_ID =" + taskId;

        try (Statement st = newReadOnlyStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {

                    String msg = rs.getString("CODE_VASE");

                    //    System.out.println(msg);
                    arr.add(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }


    public static boolean updateVaso(String taskId,String oldVaso, String newVaso){
        String sql = "INSERT INTO WRKJEXP.ROLE_CHANGE (HEAD_ID,FIELD_CODE,OLD_VALUE,NEW_VALUE) VALUES(?,?,?,?)\n";

        // Creo statement
        try (PreparedStatement st = StatementFactory.newPreparedStatement(sql)) {
            // Chiavi
            st.setString(1, taskId);
            st.setString(2, "VASO");
            st.setString(3, oldVaso);
            st.setString(4, newVaso);

            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



    public static List<String> showChanges(String botId){
        List<String> arr = new ArrayList<String>();

        String sql = "SELECT * FROM wrkjexp.ROLE_CHANGE WHERE ID IN (SELECT ID FROM WRKJEXP.ROLE_HEAD WHERE OPERATOR = " +
                "(SELECT AS_USER  FROM WRKJEXP.ROLE_USER WHERE BOT_ID = " + botId + "))";

        try (Statement st = newReadOnlyStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {

                    String headId = rs.getString("HEAD_ID").trim();
                    String fieldCode = rs.getString("FIELD_CODE").trim();
                    String oldValu = rs.getString("OLD_VALUE").trim();
                    String newValue = rs.getString("NEW_VALUE").trim();
                    String msg =  "<b>[TASK]=</b> " + headId + "     <b>[CAMPO]=</b> "+ fieldCode + "     <b>[VECCHIO]=</b> " + oldValu + "     <b>[NUOVO]=</b> "+ newValue + "\n\n";
                    System.out.println(msg);
                    arr.add(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

}
