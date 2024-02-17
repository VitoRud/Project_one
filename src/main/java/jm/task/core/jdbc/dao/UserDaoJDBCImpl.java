package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Util a = new Util();

    int id = 0;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            PreparedStatement preparedStatement = a.con().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Users (id int, name varchar(150), " +
                            "lastname varchar(150), age int);");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try {
            PreparedStatement preparedStatement = a.con().prepareStatement("DROP TABLE IF EXISTS Users;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        id++;
        try {
            PreparedStatement preparedStatement = a.con().prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = a.con().prepareStatement("DELETE FROM Users WHERE id=?");
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> people = new ArrayList<>();
        try {
            Statement statement = a.con().createStatement();
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge((byte) resultSet.getInt("age"));
                people.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public void cleanUsersTable() {
        try {
            PreparedStatement preparedStatement = a.con().prepareStatement("DELETE FROM Users WHERE id>=0");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
