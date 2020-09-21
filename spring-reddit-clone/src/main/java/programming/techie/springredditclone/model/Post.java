package programming.techie.springredditclone.model;

import java.net.URL;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;



import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	@NotBlank(message = "Post Name cannot be null or empty")
	private String postName;
	@Nullable
	private String url;
	@Nullable
	@Lob
	private String description ;
	private Integer voteCount ;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "userId",referencedColumnName = "userId")
	private User  user ;
	private Instant createdDate;
	@ManyToOne
	@JoinColumn(name="id",referencedColumnName ="id")
	private Subreddit subreddit ;
	
	
	
	
	
}
