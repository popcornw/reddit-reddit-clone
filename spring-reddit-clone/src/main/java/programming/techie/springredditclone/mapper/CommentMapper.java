package programming.techie.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import programming.techie.springredditclone.dto.CommentsDto;
import programming.techie.springredditclone.model.Comment;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	
	    @Mapping(target = "id", ignore = true)
	    @Mapping(target = "text", source = "commentsDto.text")
	    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	    @Mapping(target = "post", source = "post")
	    @Mapping(target = "user", source = "user")
	    Comment map(CommentsDto commentsDto, Post post, User user);

	    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	    CommentsDto mapToDto(Comment comment);
}
