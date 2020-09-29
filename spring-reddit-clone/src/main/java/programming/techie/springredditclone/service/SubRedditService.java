package programming.techie.springredditclone.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programming.techie.springredditclone.dto.SubredditDto;
import programming.techie.springredditclone.exceptions.SpringRedditException;
import programming.techie.springredditclone.mapper.SubRedditMapper;
import programming.techie.springredditclone.model.Subreddit;
import programming.techie.springredditclone.repository.SubredditRepository;

@Service
@AllArgsConstructor
@Slf4j

public class SubRedditService {
	private final SubredditRepository subredditRepository;
	private final SubRedditMapper subRedditMapper;
	 @Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subRedditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}
	 @Transactional(readOnly = true)
	public List<SubredditDto> getALL() {
		return subredditRepository.findAll().stream().map(subRedditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
	}

	public SubredditDto getSubreddit(Long id) {
	Subreddit subreddit  =  subredditRepository.findById(id).orElseThrow(()->new SpringRedditException("No subreddit found with the current " +id));
	return subRedditMapper.mapSubredditToDto(subreddit);
	}

}
