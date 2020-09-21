package programming.techie.springredditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
