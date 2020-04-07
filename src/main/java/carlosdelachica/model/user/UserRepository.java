package carlosdelachica.model.user;

public interface UserRepository {
    User getByName(String userName);

    void register(String userName);

    boolean isRegistered(String userName);
}
