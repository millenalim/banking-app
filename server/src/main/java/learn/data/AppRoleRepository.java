package learn.data;

import learn.entity.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole, Integer> {

    AppRole findByName(String name);
}
