
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCRUD {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        try {
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            Statement st = con.createStatement();

            
            st.executeUpdate(
                "INSERT INTO student VALUES (1, 'Abhishek', 'BCA', 85)"
            );

            st.executeUpdate(
                "INSERT INTO student VALUES (2, 'Abhinav', 'BCA', 80)"
            );

            System.out.println("Students inserted successfully!");

            
            st.executeUpdate(
                "UPDATE student SET marks = 90 WHERE id = 1"
            );

            System.out.println("Student updated successfully!");

            
            System.out.println("\nStudent Details:");

            ResultSet rs = st.executeQuery(
                "SELECT * FROM student"
            );

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Course: " + rs.getString("course") +
                    ", Marks: " + rs.getInt("marks")
                );
            }
            rs.close();

            
            st.executeUpdate(
                "DELETE FROM student WHERE id = 1"
            );

            System.out.println("\nStudent deleted successfully!");

            st.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
