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

import com.sun.mail.imap.protocol.BODY;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programming.techie.springredditclone.dto.SubredditDto;
import programming.techie.springredditclone.service.SubRedditService;

@RestController
@RequestMapping("api/subreddit/")
@Slf4j
@AllArgsConstructor
public class SubRedditController {
	private final SubRedditService SubredditService ;
@PostMapping
public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
	return ResponseEntity.status(HttpStatus.CREATED)
	.body(SubredditService.save(subredditDto));
	
}
@GetMapping
public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
	return ResponseEntity.status(HttpStatus.OK).body(SubredditService.getALL());
	
	
}

@GetMapping("/{id}")
public ResponseEntity<SubredditDto>getSubreddit(@PathVariable Long id){
	return ResponseEntity.status(HttpStatus.OK).body(SubredditService.getSubreddit(id));
	
	
}


}
