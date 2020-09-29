package programming.techie.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostRequest {
private Long postId;
private String subredditName;
private String url;
private String postName ;

private String description;

}
