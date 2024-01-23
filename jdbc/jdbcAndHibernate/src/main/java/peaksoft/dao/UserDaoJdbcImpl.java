package peaksoft.dao;

import peaksoft.configuration.JdbcConfig;
import peaksoft.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private final Connection connection = JdbcConfig.getConnection();

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        try {
            String query = """
                    create table if not exists users(
                    id serial primary key,
                    first_name varchar,
                    last_name varchar,
                    age int
                    )
                    """;
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
            }
            System.out.println("successfully created or already exists");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            String query = "drop table users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = """
                insert into users(first_name, last_name, age)
                values (?, ?, ?);
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            int i = preparedStatement.executeUpdate();
            if (i > 0) System.out.println("User successfully saved");
            else System.out.println("Failed to save the user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) System.out.println("User successfully removed");
            else System.out.println("Failed to remove the user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge((byte) resultSet.getInt("age"));
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            String query = "truncate table users";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}