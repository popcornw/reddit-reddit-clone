package programming.techie.springredditclone.service;

import java.util.Collection;
import java.util.Optional;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.repository.UserRepository;
import static java.util.Collections.singletonList;
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		User user = optionalUser.orElseThrow(()-> new UsernameNotFoundException("No user found with this "+username));
		return new org.springframework.security.core.userdetails.User
				(user.getUsername(), user.getPassword(), user.isEnabled(),true,true,true,getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		// TODO Auto-generated method stub
		return   singletonList(new SimpleGrantedAuthority(role));
	}

	


}
