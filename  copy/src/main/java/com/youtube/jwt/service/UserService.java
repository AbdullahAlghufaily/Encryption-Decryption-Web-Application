package com.youtube.jwt.service;

import com.youtube.jwt.dao.RoleDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;

    public User registerNewUser(User user){
        Role role=roleDao.findById("User").get();
        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.setRole(roles);
user.setUserPassword(passwordEncoder.encode((user.getUserPassword())));

        return userDao.save(user);
    }




    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setuserPP("admin");
        adminUser.setUserEmail("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);


    }
    public boolean isUsernameTaken(String username) {
        // Check if the username exists in your database
        return userDao.existsById(username);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public Optional<User> findByuserName(String username) {
        return userDao.findByuserName(username);
    }

    public Optional<User> findByuserEmail(String Email) {return userDao.findByuserEmail(Email);}

    public void updateUserPassword(String username, String newPassword) {
        // Assuming userDao has a method to get the user by username
        Optional<User> optionalUser = userDao.findByuserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Assuming User entity has a setPassword method
            user.setUserPassword(newPassword);
            userDao.save(user);  // Save the updated user with the new password
        } else {
            // Handle the case where the user is not found
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}

