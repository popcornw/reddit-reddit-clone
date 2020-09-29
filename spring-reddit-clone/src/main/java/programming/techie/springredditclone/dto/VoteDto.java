package programming.techie.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import programming.techie.springredditclone.model.VoteType;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
private VoteType voteType;
private Long postId;
}
