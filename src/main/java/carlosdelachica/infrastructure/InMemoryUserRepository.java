package carlosdelachica.infrastructure;

import carlosdelachica.model.user.User;
import carlosdelachica.model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

  private final List<User> users = new ArrayList<>();

  @Override
  public User getByName(String userName) {
    return getUser(userName).orElseGet(() -> {
      throw new UserNotRegistered();
    });
  }

  @Override
  public void register(String userName) {
    users.add(new User(userName));
  }

  @Override
  public boolean isRegistered(String userName) {
    return getUser(userName).isPresent();
  }

  private Optional<User> getUser(String userName) {
    return users.stream().
        filter(user -> user.hasName(userName)).
        findFirst();
  }

  public class UserNotRegistered extends RuntimeException {
  }
}
