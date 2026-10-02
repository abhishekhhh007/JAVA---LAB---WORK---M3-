
import java.sql.*;

public class DatabaseMetadataExample {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "aBi@#4558/";

        try (
            Connection con = DriverManager.getConnection(
                url, username, password
            )
        ) {
            
            DatabaseMetaData md = con.getMetaData();

            System.out.println("DATABASE INFORMATION");
            System.out.println("--------------------");

            System.out.println("Database Name: "
                    + md.getDatabaseProductName());

            System.out.println("Database Version: "
                    + md.getDatabaseProductVersion());

            System.out.println("Driver Name: "
                    + md.getDriverName());

            System.out.println("Driver Version: "
                    + md.getDriverVersion());

            System.out.println("URL: "
                    + md.getURL());

            System.out.println("Username: "
                    + md.getUserName());

            
            System.out.println("\nSUPPORTED FEATURES");
            System.out.println("------------------");

            System.out.println("Transactions Supported: "
                    + md.supportsTransactions());

            System.out.println("Batch Updates Supported: "
                    + md.supportsBatchUpdates());

            System.out.println("Stored Procedures Supported: "
                    + md.supportsStoredProcedures());

            System.out.println("Savepoints Supported: "
                    + md.supportsSavepoints());

            
            System.out.println("\nAVAILABLE TABLES");
            System.out.println("----------------");

            try (
                ResultSet rs = md.getTables(
                    con.getCatalog(),
                    null,
                    "%",
                    new String[]{"TABLE"}
                )
            ) {
                while (rs.next()) {
                    System.out.println(
                        rs.getString("TABLE_NAME")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

