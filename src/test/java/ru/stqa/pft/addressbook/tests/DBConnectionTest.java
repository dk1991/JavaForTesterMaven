package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DBConnectionTest {

    private static final String URL = "jdbc:mysql://localhost:3306/addressbook?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String SQL = "SELECT group_id, group_name, group_header, group_footer FROM group_list";

    @Test(enabled = false)
    public void testDBConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            Groups groups = new Groups();
            while (rs.next()) {
                groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name"))
                        .withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
            }
            rs.close();
            statement.close();
            conn.close();

            System.out.println(groups);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
