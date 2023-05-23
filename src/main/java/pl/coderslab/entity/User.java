package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class User {
    public static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?,?,?)";
    public static final String READ_USER_WITH_ID_QUERY = "SELECT * FROM users WHERE id=?";
    public static final String READ_ALL_USERS = "SELECT * FROM users";
    public static final String UPDATE_USER_WITH_ID_QUERY = "UPDATE users SET email=?, username=?, password=? where id=?";
    public static final String UPDATE_USERNAME_FOR_USER_ID_QUERY = "UPDATE users SET username=? where id=?";
    public static final String UPDATE_EMAIL_FOR_USER_ID_QUERY = "UPDATE users SET email=? where id=?";
    public static final String UPDATE_PASSWORD_FOR_USER_ID_QUERY = "UPDATE users SET password=? where id=?";
    public static final String DELETE_USER_WITH_ID_QUERY = "DELETE FROM users WHERE id=?";

    private Integer id;
    private String userName;
    private String email;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    PreparedStatement preStmt;


    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_WITH_ID_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                return user;
            } else {
                System.out.println("Nie istnieje User o podanym id: " + userId);
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public User update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_WITH_ID_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            int i = statement.executeUpdate();
            if (i == 1) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_WITH_ID_QUERY);
            statement.setInt(1, userId);
            int i = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {

        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(READ_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private User[] addToArray(User newUser, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = newUser;
        return tmpUsers;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
