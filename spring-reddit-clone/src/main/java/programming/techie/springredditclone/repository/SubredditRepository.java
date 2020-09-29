package programming.techie.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.Subreddit;
@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
	 Optional<Subreddit> findByName(String subredditName);
}
