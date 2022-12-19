package com.gohel.service;

import com.gohel.model.School;
import com.gohel.model.Student;
import com.gohel.model.User;
import com.gohel.repository.SchoolRepository;
import com.gohel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User, Long> {

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public User getByUserName(String username) {
        return userRepository
                .findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository
                .existsByUsername(username);
    }

    public User currentUser() {
        return getByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
