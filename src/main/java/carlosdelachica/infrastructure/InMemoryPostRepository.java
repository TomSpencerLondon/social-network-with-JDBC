package carlosdelachica.infrastructure;

import carlosdelachica.model.post.Post;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.model.user.User;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryPostRepository implements PostRepository {

  private List<Post> posts = new ArrayList<>();

  @Override
  public List<Post> postsOf(User user) {
    return posts.
        stream().
        filter(post -> post.isFrom(user)).
        collect(toList());
  }

  @Override
  public void store(Post post) {
    posts.add(post);
  }
}
