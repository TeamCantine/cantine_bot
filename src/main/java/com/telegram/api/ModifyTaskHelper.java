package com.telegram.api;

import com.telegram.connection.StatementFactory;

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


        String sql = "SELECT * FROM WRKJEXP.ROLE_ROW WHERE HEAD_ID =" + taskId.trim();

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
            st.setString(1, taskId.trim());
            st.setString(2, "VASO");
            st.setString(3, oldVaso.trim());
            st.setString(4, newVaso.trim());

            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

//

    public static List<String> showChanges(String botId){
        List<String> arr = new ArrayList<String>();

        String sql = "SELECT * FROM wrkjexp.ROLE_CHANGE WHERE HEAD_ID IN (SELECT ID FROM WRKJEXP.ROLE_HEAD WHERE OPERATOR = " +
                "(SELECT AS_USER  FROM WRKJEXP.ROLE_USER WHERE BOT_ID ='" + botId.trim() + "'))";

        System.out.println(sql);

        try (Statement st = newReadOnlyStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {

                    String headId = rs.getString("HEAD_ID").trim();
                    String fieldCode = rs.getString("FIELD_CODE").trim();
                    String oldValu = rs.getString("OLD_VALUE").trim();
                    String newValue = rs.getString("NEW_VALUE").trim();
                    String msg =  "<b>TASK=</b> " + headId + "     <b>CAMPO=</b> "+ fieldCode + "     <b>VECCHIO=</b> " + oldValu + "     <b>NUOVO=</b> "+ newValue + "\n\n";
                    System.out.println(msg);
                    arr.add(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static List<Change> getChange(String taskId){
        List<Change> changes = new ArrayList<>();
        String sql = "SELECT * FROM WRKJEXP.ROLE_CHANGE WHERE HEAD_ID =" + taskId.trim();

        try (Statement st = newReadOnlyStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
             while( rs.next()){
                 Change ch = new Change(rs.getString("ID").trim(),rs.getString("HEAD_ID").trim(),rs.getString("FIELD_CODE").trim(),rs.getString("OLD_VALUE").trim(),rs.getString("NEW_VALUE").trim());
                 changes.add(ch);
             }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return changes;
    }

}
