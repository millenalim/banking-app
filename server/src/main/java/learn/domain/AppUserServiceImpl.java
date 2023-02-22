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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder encoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public Result<AppUser> createAccount(String username, String password, AppUser user, Set<AppUserRole> userRoles) {
        Result<AppUser> result = validate(username, password);
        AppUser currentUser = appUserRepository.findByUsername(user.getUsername());

        if (!result.isSuccess()) {
            return result;
        } else {
            if (currentUser != null) {
                result.addMessage(ActionStatus.INVALID, "The provided username already exists");
            } else {
                password = encoder.encode(user.getPassword());
                user.setPassword(password);

                for (AppUserRole userRole : userRoles) {
                    appRoleRepository.save(userRole.getAppRole());
                }

                user.getUserRoles().addAll(userRoles);

                user.setCheckingAccount(accountService.createCheckingAccount());
                user.setSavingsAccount(accountService.createSavingsAccount());
                user.setEnabled(true);

                currentUser = appUserRepository.save(user);
                result.setPayload(currentUser);
            }
        }

        return result;
    }

    public AppUser saveUser(AppUser user) {
        return null;
    }

    public Result<AppUser> checkUserValidation(String username, String email) {
        Result<AppUser> result = validateFields(username, email);
        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();

        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "username is required.");
            return result;
        }

        if (password == null) {
            result.addMessage(ActionStatus.INVALID, "password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "username must be less than 50 characters");
            return result;
        }

        if (!isValidPassword(password)) {
            result.addMessage(ActionStatus.INVALID,
                    "password must be at least 8 characters and contain a digit," +
                            " a letter, and a non-digit/non-letter");
        }

        return result;
    }

    private Result<AppUser> validateFields(String username, String email) {
        Result<AppUser> result = new Result<>();

        // check if user exists
        if (findByUsername(username) == null || findByEmail(email) == null) {
            result.addMessage(ActionStatus.INVALID, "User does not exist");
        }

        if (findByUsername(username) == null) {
            result.addMessage(ActionStatus.INVALID, "Username is required");
        }

        if (findByEmail(email) == null) {
            result.addMessage(ActionStatus.INVALID, "Email is required");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }
}
