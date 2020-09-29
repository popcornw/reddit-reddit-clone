package programming.techie.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SubredditDto {
private Long id ; 
private String name ; 
private  String description ; 
private Integer numberOfPosts;
}
