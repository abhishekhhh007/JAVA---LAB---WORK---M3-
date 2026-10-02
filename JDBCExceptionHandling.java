
import java.sql.*;
import java.util.Scanner;

public class JDBCExceptionHandling {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "Your_MYSQL_Password";

        Scanner sc = new Scanner(System.in);
        Connection con = null;

        try {
            
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Course: ");
            String course = sc.nextLine().trim();

            System.out.print("Enter Marks (0-100): ");
            int marks = Integer.parseInt(sc.nextLine().trim());

            if (id <= 0 || name.isEmpty() || course.isEmpty()
                    || marks < 0 || marks > 100) {
                throw new IllegalArgumentException(
                    "Invalid input. Check ID, name, course and marks."
                );
            }

            
            con = DriverManager.getConnection(
                url, username, password
            );

            System.out.println("Database connected successfully.");

            
            String sql =
                "INSERT INTO jdbc_students (id, name, course, marks) " +
                "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, course);
                ps.setInt(4, marks);

                ps.executeUpdate();

                System.out.println("Student record inserted successfully.");
            }

            
            try (Statement st = con.createStatement()) {
                st.executeQuery("SELEC * FROM jdbc_students");
            } catch (SQLException e) {
                System.out.println("Invalid SQL query: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Input error: ID and marks must be integers.");

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(
                "Duplicate record or constraint violation: "
                + e.getMessage()
            );

        } catch (SQLException e) {
            if (con == null) {
                System.out.println(
                    "Connection error: " + e.getMessage()
                );
            } else {
                System.out.println(
                    "Database error: " + e.getMessage()
                );
            }

        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(
                        "Error closing connection: " + e.getMessage()
                    );
                }
            }

            sc.close();
        }
    }
}

