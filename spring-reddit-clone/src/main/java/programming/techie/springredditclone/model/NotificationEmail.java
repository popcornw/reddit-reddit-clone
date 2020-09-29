package programming.techie.springredditclone.model;

import org.springframework.data.repository.NoRepositoryBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoRepositoryBean
public class NotificationEmail {
private  String subject ;
private String recipient;
private String body;
}
