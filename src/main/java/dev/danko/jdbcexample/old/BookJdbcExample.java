package dev.danko.jdbcexample.old;

import dev.danko.jdbcexample.model.Book;
import dev.danko.jdbcexample.utilits.DatabaseConnection;
import java.sql.*;

public class BookJdbcExample {

    // Старый метод препода
    public static void saveBook(Book book) {
        String sql = "INSERT INTO books (id, title, author) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения: " + book.getTitle(), e);
        }
    }

    // ТВОЯ РЕАЛИЗАЦИЯ (дописана внутрь этого же класса)
    public static Long updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publish_year = ?, publisher = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());

            if (book.getPublishYear() != null) {
                pstmt.setInt(3, book.getPublishYear());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, book.getPublisher());
            pstmt.setBigDecimal(5, book.getPrice());
            pstmt.setLong(6, book.getId());

            int affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0) ? book.getId() : null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}