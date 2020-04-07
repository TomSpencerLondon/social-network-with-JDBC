package carlosdelachica.model.post;

import carlosdelachica.model.user.User;

import java.util.List;

public interface PostRepository {
    List<Post> postsOf(User user);

    void store(Post post);
}
