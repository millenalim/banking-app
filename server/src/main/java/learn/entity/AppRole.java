package learn.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppRole {

    @Id
    private int roleId;
    private String name;
    @OneToMany(mappedBy = "app_role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AppUserRole> userRoles = new HashSet<>();

    public AppRole() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<AppUserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
