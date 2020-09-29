package programming.techie.springredditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.dto.CommentsDto;
import programming.techie.springredditclone.exceptions.PostNotFoundException;
import programming.techie.springredditclone.mapper.CommentMapper;
import programming.techie.springredditclone.model.Comment;
import programming.techie.springredditclone.model.NotificationEmail;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.repository.CommentRepository;
import programming.techie.springredditclone.repository.PostRepository;
import programming.techie.springredditclone.repository.UserRepository;

@Service
@AllArgsConstructor
public class CommentService {
	private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);
		String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post." +POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername()+ " Commented on your post", user.getEmail(),message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("the following post is not found" + postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
	User user = userRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}
}
