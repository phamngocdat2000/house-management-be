package com.student.searchroom.service;

import com.student.searchroom.entity.Authority;
import com.student.searchroom.entity.User;
import com.student.searchroom.exception.BannedUserException;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.RegisterUserRequest;
import com.student.searchroom.model.request.UpdateUserRequest;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.repository.AuthorityRepository;
import com.student.searchroom.repository.UserRepository;
import com.student.searchroom.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public UserResponse registrationUser(RegisterUserRequest request) {
        if (userRepository.findById(request.getUsername()).isPresent())
            throw new SearchRoomException(ErrorCode.CONFLICT_USERNAME);
        User userToSave = request.toUser();
        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));

        User result = userRepository.save(userToSave);
        Authority authority = new Authority();
        authority.setUser(result);
        authority.setRole(Authority.Role.ROLE_USER);
        authorityRepository.save(authority);
        return UserResponse.from(result);
    }

    public User getByUsername(String username) {
        return userRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public void processOAuthPostLogin(String username, String name, String avaUrl, User.Provider provider) {
        Optional<User> existUser = userRepository.findById(username);

        if (existUser.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setProvider(provider);
            user.setActive(true);
            user.setAvaUrl(avaUrl);
            user.setCreatedBy(username);
            user.setFullName(name);
            user.setProvider(User.Provider.LOCAL);
            userRepository.save(user);
        }

    }

    public UserResponse getProfile() {
        String currentUser = SecurityUtil.getCurrentUsername();
        User user = userRepository.findById(currentUser).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }


    public UserResponse getProfileByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

    public UserResponse updateUser(UpdateUserRequest request) {
        String currentUser = SecurityUtil.getCurrentUsername();
        User userToUpdate = getByUsername(currentUser);
        request.updateUser(userToUpdate);
        return UserResponse.from(userRepository.save(userToUpdate));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if (!user.get().isActive()) {
            throw new BannedUserException("Banded perform action!");
        }
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List<String> roles = user.get().getAuthorities().stream().map(authority ->
                authority.getRole().getValue()).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword() != null ? user.get().getPassword() : "",
                getAuthorities(roles));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> result =  new ArrayList<>();
        for (int i = 0; i < roles.size(); i ++) {
            result.add(new SimpleGrantedAuthority(roles.get(i)));
        }
        return result;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
