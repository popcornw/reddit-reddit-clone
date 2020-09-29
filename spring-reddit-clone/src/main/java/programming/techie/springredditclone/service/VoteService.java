package programming.techie.springredditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.dto.VoteDto;
import programming.techie.springredditclone.exceptions.PostNotFoundException;
import programming.techie.springredditclone.exceptions.SpringRedditException;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.Vote;
import programming.techie.springredditclone.repository.PostRepository;
import programming.techie.springredditclone.repository.VoteRepository;
import static programming.techie.springredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;
@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post not found for the id " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findByPostAndUser(post, authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");

		}

		if (UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);

	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}
