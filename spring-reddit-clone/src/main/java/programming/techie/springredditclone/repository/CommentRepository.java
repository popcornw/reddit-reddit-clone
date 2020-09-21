package programming.techie.springredditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import programming.techie.springredditclone.model.Comment;
import programming.techie.springredditclone.model.Post;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
