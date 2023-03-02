package learn.domain.models;

import learn.entity.AppUser;
import learn.entity.AppUserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface AppUserService {

    List<AppUser> findAllUsers();

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    void save(AppUser user);

    AppUser createAccount(AppUser user, Set<AppUserRole> userRoles);

    AppUser saveUser(AppUser user);

    void enable(String username);

    void disable(String username);

    boolean validateUsernameExist(String username);

    boolean validateEmailExist(String email);

    boolean validateUserExist(String username, String email);
}
