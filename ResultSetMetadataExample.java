
import java.sql.*;

public class ResultSetMetadataExample {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        String query = "SELECT * FROM student";

        try (
            Connection con = DriverManager.getConnection(
                url, username, password
            );
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)
        ) {
            
            ResultSetMetaData rsmd = rs.getMetaData();

          
            int count = rsmd.getColumnCount();

            System.out.println("TABLE METADATA");
            System.out.println("--------------");
            System.out.println("Column Count: " + count);

            for (int i = 1; i <= count; i++) {

                System.out.println("\nColumn " + i);
                System.out.println("Column Name: "
                        + rsmd.getColumnName(i));

                System.out.println("Column Label: "
                        + rsmd.getColumnLabel(i));

                System.out.println("Data Type: "
                        + rsmd.getColumnTypeName(i));

                System.out.println("SQL Type Code: "
                        + rsmd.getColumnType(i));

                System.out.println("Column Size: "
                        + rsmd.getColumnDisplaySize(i));

                System.out.println("Table Name: "
                        + rsmd.getTableName(i));

                System.out.println("Nullable: "
                        + rsmd.isNullable(i));

                System.out.println("Auto Increment: "
                        + rsmd.isAutoIncrement(i));

                System.out.println("Case Sensitive: "
                        + rsmd.isCaseSensitive(i));

                System.out.println("Writable: "
                        + rsmd.isWritable(i));
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
