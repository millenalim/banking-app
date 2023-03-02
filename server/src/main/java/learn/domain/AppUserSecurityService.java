package learn.domain;

import learn.data.AppUserRepository;
import learn.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserSecurityService implements UserDetailsService{

        @Autowired
        private AppUserRepository appUserRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser appUser = appUserRepository.findByUsername(username);
            if (null == appUser) {
                throw new UsernameNotFoundException(username + " not found");
            }
            return appUser;
        }
}

