package learn.data;

import learn.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    List<AppUser> findAll();

    List<AppUser> findAllUsers();

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    AppUser createUser(AppUser user);
}
