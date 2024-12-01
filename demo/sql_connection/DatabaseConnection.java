package sql_connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        // Создаем объект конфигурации HikariCP
        HikariConfig config = new HikariConfig();

        // Указываем JDBC URL для подключения к базе данных
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres"); // Замените на ваши данные
        config.setUsername("licette."); // Замените на ваше имя пользователя
        config.setPassword("123123"); // Замените на ваш пароль

        // Указываем имя класса драйвера
        config.setDriverClassName("org.postgresql.Driver");

        // Дополнительные настройки (по желанию)
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000); // 30 секунд

        // Создаем источник данных
        HikariDataSource dataSource = new HikariDataSource(config);

        // Теперь вы можете использовать dataSource для получения соединений
        try (Connection connection = dataSource.getConnection()) {
            // Ваш код для работы с базой данных
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрываем источник данных при завершении работы
            dataSource.close();
        }
    }
}
