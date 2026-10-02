
import java.sql.*;
import java.math.BigDecimal;

public class JDBCTransactionExample {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "YOUR_MYSQL_PASSWORD";

        int senderId = 101;
        int receiverId = 102;
        BigDecimal amount = new BigDecimal("1000.00");

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, password);

            
            con.setAutoCommit(false);

            
            String checkSQL =
                "SELECT balance FROM bank_accounts " +
                "WHERE account_id = ? FOR UPDATE";

            BigDecimal balance;

            try (PreparedStatement ps = con.prepareStatement(checkSQL)) {
                ps.setInt(1, senderId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Sender account not found.");
                    }

                    balance = rs.getBigDecimal("balance");
                }
            }

            if (balance.compareTo(amount) < 0) {
                throw new SQLException("Insufficient balance.");
            }

            
            String debitSQL =
                "UPDATE bank_accounts " +
                "SET balance = balance - ? WHERE account_id = ?";

            try (PreparedStatement ps = con.prepareStatement(debitSQL)) {
                ps.setBigDecimal(1, amount);
                ps.setInt(2, senderId);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Sender update failed.");
                }
            }

            
            String creditSQL =
                "UPDATE bank_accounts " +
                "SET balance = balance + ? WHERE account_id = ?";

            try (PreparedStatement ps = con.prepareStatement(creditSQL)) {
                ps.setBigDecimal(1, amount);
                ps.setInt(2, receiverId);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Receiver account not found.");
                }
            }

            
            con.commit();

            System.out.println("Money transferred successfully!");
            System.out.println("Transferred Amount: Rs. " + amount);

        } catch (SQLException e) {

            System.out.println("Transaction failed: " + e.getMessage());

            if (con != null) {
                try {
                    
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackError) {
                    System.out.println(
                        "Rollback failed: " + rollbackError.getMessage()
                    );
                }
            }

        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(
                        "Connection close error: " + e.getMessage()
                    );
                }
            }
        }
    }
}
