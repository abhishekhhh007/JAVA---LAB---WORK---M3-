
import java.sql.*;
import java.io.*;

public class BlobClobExample {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "Your_MYSQL_Password";

        
        File imageFile = new File("image.jpg");
        File textFile = new File("document.txt");

        String insertSQL =
            "INSERT INTO documents (image_data, document_text) VALUES (?, ?)";

        String selectSQL =
            "SELECT image_data, document_text FROM documents WHERE id = ?";

        try (
            Connection con =
                DriverManager.getConnection(url, username, password)
        ) {
            
            try (
                PreparedStatement ps = con.prepareStatement(
                    insertSQL, Statement.RETURN_GENERATED_KEYS
                );
                FileInputStream imageInput =
                    new FileInputStream(imageFile);
                FileReader textInput =
                    new FileReader(textFile)
            ) {
                ps.setBinaryStream(1, imageInput, imageFile.length());
                ps.setCharacterStream(2, textInput, textFile.length());

                ps.executeUpdate();

                int id;
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    id = keys.getInt(1);
                }

                System.out.println("Image and document stored successfully.");
                System.out.println("Record ID: " + id);

                
                try (
                    PreparedStatement get = con.prepareStatement(selectSQL)
                ) {
                    get.setInt(1, id);

                    try (ResultSet rs = get.executeQuery()) {
                        if (rs.next()) {

                            
                            try (
                                InputStream imageData =
                                    rs.getBinaryStream("image_data");
                                FileOutputStream imageOutput =
                                    new FileOutputStream("retrieved_image.jpg")
                            ) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;

                                while ((bytesRead = imageData.read(buffer)) != -1) {
                                    imageOutput.write(buffer, 0, bytesRead);
                                }
                            }

                            
                            try (
                                Reader textData =
                                    rs.getCharacterStream("document_text");
                                FileWriter textOutput =
                                    new FileWriter("retrieved_document.txt")
                            ) {
                                char[] buffer = new char[4096];
                                int charsRead;

                                while ((charsRead = textData.read(buffer)) != -1) {
                                    textOutput.write(buffer, 0, charsRead);
                                }
                            }

                            System.out.println("Image retrieved as retrieved_image.jpg");
                            System.out.println("Document retrieved as retrieved_document.txt");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}

