package programming.techie.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Comment;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.User;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);

}
