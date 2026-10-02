
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetNavigation {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        try (
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            Statement st = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = st.executeQuery(
                "SELECT * FROM student ORDER BY id"
            )
        ) {
            
            System.out.println("Using next():");
            if (rs.next()) {
                display(rs);
            } else {
                System.out.println("No student records found.");
                return;
            }

            
            System.out.println("\nUsing previous():");
            if (rs.previous()) {
                display(rs);
            } else {
                System.out.println("No previous record.");
            }

            
            System.out.println("\nUsing first():");
            if (rs.first()) {
                display(rs);
            }

            
            System.out.println("\nUsing last():");
            if (rs.last()) {
                display(rs);
            }

            
            System.out.println("\nUsing absolute(2):");
            if (rs.absolute(2)) {
                display(rs);
            } else {
                System.out.println("Second record does not exist.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static void display(ResultSet rs) throws SQLException {
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Course: " + rs.getString("course"));
        System.out.println("Marks: " + rs.getInt("marks"));
    }
}
