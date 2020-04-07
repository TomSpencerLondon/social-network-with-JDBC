package carlosdelachica.infrastructure;

import carlosdelachica.Application;
import carlosdelachica.model.user.User;
import carlosdelachica.model.user.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlUserRepository implements UserRepository {
    @Override
    public User getByName(String userName) {
        return null;
    }

    @Override
    public void register(String userName) {
        String sql = "INSERT INTO users(name) "
                + "VALUES(?)";

        try {
            PreparedStatement query = Application.databaseConnection.prepareStatement(sql);
            query.setString(1, userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRegistered(String userName) {
        return false;
    }
}
