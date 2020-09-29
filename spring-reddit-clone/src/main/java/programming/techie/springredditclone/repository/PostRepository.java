package programming.techie.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.Subreddit;
import programming.techie.springredditclone.model.User;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}
