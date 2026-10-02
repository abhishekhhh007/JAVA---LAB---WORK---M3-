
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CallableProcedure {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(
                 url, username, password)) {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            CallableStatement cs =
                con.prepareCall("{CALL GetStudent(?)}");

            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                System.out.println("\nStudent Details");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Marks: " + rs.getInt("marks"));
            } else {
                System.out.println("Student not found!");
            }

            rs.close();
            cs.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
