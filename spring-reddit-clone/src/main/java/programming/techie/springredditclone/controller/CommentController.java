package programming.techie.springredditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import programming.techie.springredditclone.dto.CommentsDto;
import programming.techie.springredditclone.service.CommentService;

@Slf4j
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api/comments/")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentDto) {
		commentService.save(commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllComments(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
	}

	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName) {
return ResponseEntity.status(HttpStatus.OK).body(
	commentService.getAllCommentsForUser(userName));
 
	}
}
