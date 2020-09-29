package programming.techie.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.model.Vote;
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	Optional<Vote> findByPostAndUser(Post post, User currentUser);
 
}
