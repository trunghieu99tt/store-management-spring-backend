package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.user.CustomUserDetail;
import com.projects.app.models.user.Manager;
import com.projects.app.models.user.Staff;
import com.projects.app.models.user.User;
import com.projects.app.repository.UserRepository;
import com.projects.app.repository.user.ManagerRepository;
import com.projects.app.repository.user.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username
    ) throws UsernameNotFoundException {
        User user = this.getUserByUsername(username);
        return new CustomUserDetail(user);
    }

    public Staff createStaff(Staff staff) throws BackendError {
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        if (this.getUserByUsername(staff.getUsername()) != null) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Username does exist. Please choose another username");
        }
        try {
            staff = staffRepository.save(staff);
            staff.setPassword(null);
            userRepository.save(staff);
        } catch (Exception e) {
            System.out.println("error " + e);
            e.printStackTrace();
        }
        return staff;
    }

    public Manager createManager(Manager manager) throws BackendError {
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        if (this.getUserByUsername(manager.getUsername()) != null) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Username does exist. Please choose another username");
        }
        try {
            manager = managerRepository.save(manager);
            manager.setPassword(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


    @Transactional
    public Boolean deleteOne(Long id) {
        User user = getOne(id);
        if (user != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
