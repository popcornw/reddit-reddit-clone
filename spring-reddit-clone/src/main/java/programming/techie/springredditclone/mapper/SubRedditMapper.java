package programming.techie.springredditclone.mapper;
import programming.techie.springredditclone.model.Post;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import programming.techie.springredditclone.dto.SubredditDto;
import programming.techie.springredditclone.model.Subreddit;

@Mapper(componentModel ="spring")
public interface SubRedditMapper {
	@Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
SubredditDto mapSubredditToDto(Subreddit subreddit);

default Integer mapPosts(List<Post> numberOfPosts) {
    return numberOfPosts.size();
}
@InheritInverseConfiguration
@Mapping(target = "posts", ignore = true)
Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}