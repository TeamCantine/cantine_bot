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

}
