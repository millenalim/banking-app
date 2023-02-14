package learn.entity;

import javax.persistence.*;

@Entity
@Table(name = "app_user_role")
public class AppUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userRoleId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_role_id")
    private AppRole appRole;

    public AppUserRole() {
    }

    public AppUserRole(AppUser appUser, AppRole appRole) {
        this.appUser = appUser;
        this.appRole = appRole;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppRole getAppRole() {
        return appRole;
    }

    public void setAppRole(AppRole appRole) {
        this.appRole = appRole;
    }
}
