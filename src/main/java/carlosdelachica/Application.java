package carlosdelachica;

import carlosdelachica.command.Command;
import carlosdelachica.command.CommandsFactory;
import carlosdelachica.delivery_mechanism.ConsoleWrapper;
import carlosdelachica.delivery_mechanism.PostFormatter;
import carlosdelachica.delivery_mechanism.TimeAgoFormatter;
import carlosdelachica.delivery_mechanism.View;
import carlosdelachica.infrastructure.Clock;
import carlosdelachica.model.input.Input;
import carlosdelachica.model.input.InputParser;
import carlosdelachica.infrastructure.InMemoryPostRepository;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.infrastructure.InMemoryUserRepository;
import carlosdelachica.model.user.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {

  public static Connection databaseConnection;

  public static void main(String[] args) throws SQLException {
    SocialNetworkingApp app = SocialNetworkAppFactory.make();
    String url1 = "jdbc:mysql://localhost:3306/social_network";
    String user = "root";
    String password = "password";

    databaseConnection = DriverManager.getConnection(url1, user, password);
    if (databaseConnection != null) {
      System.out.println("Connected to the database social_network");
    }
    String sql = "INSERT INTO users(name) "
            + "VALUES(?)";

    try {
      PreparedStatement query = Application.databaseConnection.prepareStatement(sql);
      query.setString(1, "Ewan");
      query.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    run(app);
  }

  private static void run(SocialNetworkingApp app) {
    while (true) {
      app.run();
    }
  }

  public static class SocialNetworkingApp {

    private final InputParser parser;
    private final CommandsFactory commandsFactory;
    private final View view;

    public SocialNetworkingApp(InputParser parser, CommandsFactory factory, View view) {
      this.parser = parser;
      this.commandsFactory = factory;
      this.view = view;
    }

    public void run() {
      executeUserInput(view.getUserInput());
    }

    public void executeUserInput(String userInput) {
      executeCommandFor(parse(userInput));
    }

    private Input parse(String userInput) {
      return parser.parse(userInput);
    }

    private void executeCommandFor(Input input) {
      Command command = commandsFactory.make(input);
      command.execute();
    }
  }

  private static class SocialNetworkAppFactory {

    private static SocialNetworkingApp make() {
      Clock clock = new Clock();
      InputParser parser = new InputParser();
      TimeAgoFormatter timeAgoFormatter = new TimeAgoFormatter(clock);
      ConsoleWrapper console = new ConsoleWrapper();
      PostRepository postRepo = new InMemoryPostRepository();
      UserRepository userRepo = new InMemoryUserRepository();
      PostFormatter postFormatter = new PostFormatter(timeAgoFormatter);
      View view = new View(console, postFormatter);
      CommandsFactory commandsFactory = new CommandsFactory(clock, view, postRepo, userRepo);
      return new SocialNetworkingApp(parser, commandsFactory, view);
    }
  }
}
