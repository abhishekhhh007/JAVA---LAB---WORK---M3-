
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentRegistrations {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "Your_MYSQL_Password";

        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(
                 url, username, password)) {

        
            String insertSQL =
                "INSERT INTO student (id, name, course, marks) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(insertSQL);

            System.out.print("Enter student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter course: ");
            String course = sc.nextLine();

            System.out.print("Enter marks: ");
            int marks = sc.nextInt();

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setInt(4, marks);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student registered successfully!");
            }

            ps.close();

            sc.nextLine();
            System.out.print("\nEnter student ID to search: ");
            int searchId = sc.nextInt();

            String searchSQL =
                "SELECT * FROM student WHERE id = ?";

            PreparedStatement searchPS =
                con.prepareStatement(searchSQL);

            searchPS.setInt(1, searchId);

            ResultSet rs = searchPS.executeQuery();

            if (rs.next()) {
                System.out.println("\nStudent Details:");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Marks: " + rs.getInt("marks"));
            } else {
                System.out.println("Student not found!");
            }

            rs.close();
            searchPS.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
