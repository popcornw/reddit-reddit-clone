package programming.techie.springredditclone.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.dto.PostRequest;
import programming.techie.springredditclone.dto.PostResponse;
import programming.techie.springredditclone.exceptions.PostNotFoundException;
import programming.techie.springredditclone.exceptions.SpringRedditException;
import programming.techie.springredditclone.exceptions.SubredditNotFoundException;
import programming.techie.springredditclone.mapper.PostMapper;
import programming.techie.springredditclone.model.Post;
import programming.techie.springredditclone.model.Subreddit;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.repository.PostRepository;
import programming.techie.springredditclone.repository.SubredditRepository;
import programming.techie.springredditclone.repository.UserRepository;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository ;

	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException("subreddit name "+postRequest.getSubredditName()));
		
		
		postRepository.save(postMapper.map(postRequest,subreddit,authService.getCurrentUser()));

	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {	
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
}
