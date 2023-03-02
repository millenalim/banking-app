package learn.controller;

import learn.data.AppRoleRepository;
import learn.domain.models.AppUserService;
import learn.entity.AppRole;
import learn.entity.AppUser;
import learn.entity.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody AppUser user) {
        if (appUserService.validateUserExist(user.getUsername(), user.getEmail())) {

            if (appUserService.validateUsernameExist(user.getUsername())) {
                return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
            }

            if (appUserService.validateEmailExist(user.getEmail())) {
                return new ResponseEntity<>("Email already exists in system", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        } else {
            Set<AppUserRole> userRoles = new HashSet<>();
            userRoles.add(new AppUserRole(user, appRoleRepository.findByName("USER")));

            appUserService.createAccount(user, userRoles);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        }
    }
}