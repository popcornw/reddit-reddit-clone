package programming.techie.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import programming.techie.springredditclone.dto.PostRequest;
import programming.techie.springredditclone.dto.PostResponse;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.Subreddit;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.repository.CommentRepository;
import programming.techie.springredditclone.repository.VoteRepository;
import programming.techie.springredditclone.service.AuthService;

@Mapper(componentModel = "spring")
public  abstract class PostMapper {
	 @Autowired
	    private CommentRepository commentRepository;
	    @Autowired
	    private VoteRepository voteRepository;
	    @Autowired
	    private AuthService authService;
	    
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
	 @Mapping(target = "subreddit", source = "subreddit")
	  @Mapping(target = "user", source = "user")
	 @Mapping(target = "voteCount", constant = "0")
    public  abstract Post  map(PostRequest postRequest, Subreddit subreddit, User user);
	
	@Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
	
	 public abstract PostResponse mapToDto(Post post);
	  Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
    
}
