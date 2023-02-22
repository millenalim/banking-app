package learn.domain.models;

import learn.domain.Result;
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

//    void save(AppUser user);

    Result<AppUser> createAccount(String username, String password, AppUser user, Set<AppUserRole> userRoles);

    AppUser saveUser(AppUser user);

    Result<AppUser> checkUserValidation(String username, String email);
}
