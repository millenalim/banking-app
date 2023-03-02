package learn.domain;

import learn.data.AppRoleRepository;
import learn.data.AppUserRepository;
import learn.domain.models.AccountService;
import learn.domain.models.AppUserService;
import learn.entity.AppUser;
import learn.entity.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Lazy
    @Autowired
    private AccountService accountService;

    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public void save(AppUser user) {
        appUserRepository.save(user);
    }

    public AppUser createAccount(AppUser user, Set<AppUserRole> userRoles) {
        AppUser currentUser = appUserRepository.findByUsername(user.getUsername());

        if (currentUser != null) {
            System.out.printf("%s already exists.", user.getUsername());;
        } else {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);

            for (AppUserRole role : userRoles) {
                appRoleRepository.save(role.getAppRole());
            }

            user.getUserRoles().addAll(userRoles);

            user.setCheckingAccount(accountService.createCheckingAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            currentUser = appUserRepository.save(user);
        }

        return currentUser;
    }

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public void enable(String username) {
        AppUser user = findByUsername(username);
        user.setEnabled(true);
        appUserRepository.save(user);
    }

    public void disable(String username) {
        AppUser user = findByUsername(username);
        user.setEnabled(false);
        System.out.printf("status: %s", user.isEnabled());
        appUserRepository.save(user);
        System.out.printf("%s is disabled", username);
    }

    public boolean validateUsernameExist(String username) {
        return null != findByUsername(username);
    }

    public boolean validateEmailExist(String email) {
        return null != findByEmail(email);
    }

    public boolean validateUserExist(String username, String email) {
        return validateUsernameExist(username) || validateEmailExist(email);
    }
}
