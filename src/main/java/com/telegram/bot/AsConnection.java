package com.telegram.bot;




import java.sql.*;
        import java.util.*;

public class AsConnection {

    public static void main(java.lang.String[] args)
    {

        // Register both drivers.
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
        } catch (ClassNotFoundException cnf) {
            System.out.println("ERROR: One of the JDBC drivers did not load.");
            System.exit(0);
        }

        try {
            // Obtain a connection with each driver.

            Connection conn2 = DriverManager.getConnection("jdbc:as400://10.200.100.160", "WRKJEXP", "WRKJEXP");



            if (conn2 instanceof com.ibm.as400.access.AS400JDBCConnection)
                System.out.println("conn2 is running under the IBM Toolbox for Java JDBC driver.");
            else
                System.out.println("There is something wrong with conn2.");


            // do stuff

            String sql = "Select * from WRKANDVIC.BOT_HEADER";





            try (Statement st = conn2.createStatement ()) {
                try (ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next())
                    {
                        System.out.println("ID: " + rs.getInt(1) );
                        System.out.println("ID_USER: " + rs.getString(2) );
                        System.out.println("AS_USER: " + rs.getString(3) );
                    }

                }
            } catch (Exception e) {
                System.err.println(e);
            }

            conn2.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
