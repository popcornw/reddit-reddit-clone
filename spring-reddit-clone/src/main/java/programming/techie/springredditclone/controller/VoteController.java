package programming.techie.springredditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programming.techie.springredditclone.dto.VoteDto;
import programming.techie.springredditclone.service.VoteService;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
@Slf4j
public class VoteController {
	private final VoteService voteService;
@PostMapping
public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
	voteService.vote(voteDto);
	 return new ResponseEntity<>(HttpStatus.OK);
	

}
}
