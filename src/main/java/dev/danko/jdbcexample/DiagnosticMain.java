package dev.danko.jdbcexample;

import dev.danko.jdbcexample.utilits.DatabaseConnection;
import java.sql.*;

public class DiagnosticMain {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ДИАГНОСТИКИ ===");
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("ПОДКЛЮЧЕНО УСПЕШНО!");

            // Создаем таблицу, чтобы список не был пустым
            stmt.execute("CREATE TABLE books (id SERIAL PRIMARY KEY, title VARCHAR(255))");
            System.out.println("Таблица 'books' создана в памяти.");

            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'");

            System.out.println("Список таблиц:");
            while (rs.next()) {
                System.out.println(" - " + rs.getString("table_name"));
            }

            System.out.println("=== ТЕСТ ЗАВЕРШЕН УСПЕШНО ===");

        } catch (SQLException e) {
            System.out.println("ОШИБКА: " + e.getMessage());
        }
    }
}