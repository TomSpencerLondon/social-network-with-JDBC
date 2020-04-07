package carlosdelachica.infrastructure;

import carlosdelachica.model.post.Post;
import carlosdelachica.model.post.PostRepository;
import carlosdelachica.model.user.User;

import java.util.List;

public class MySqlPostRepository implements PostRepository {
    @Override
    public List<Post> postsOf(User user) {
        return null;
    }

    @Override
    public void store(Post post) {
//        User -> reference to user table
//        String message
//        long timestamp
    }
}
