package com.student.searchroom.service;

import com.student.searchroom.entity.Authority;
import com.student.searchroom.entity.User;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.repository.AuthorityRepository;
import com.student.searchroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public List<UserResponse> getUserAndPaging(int pageIndex, int pageSize) {
        Pageable paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<User> userPage = userRepository.findAll(paging);
        return userPage.getContent().stream().map(UserResponse::from).collect(Collectors.toList());
    }

    public void addAuthorityWithUsername(String username) {
        User userToAdd = userRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
        Authority authority = new Authority();
        authority.setRole(Authority.Role.ROLE_ADMIN);
        authority.setUser(userToAdd);
        authorityRepository.save(authority);
    }

    public UserResponse banUser(String username) {
        User userToBan = userRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
        userToBan.setActive(false);
        return UserResponse.from(userRepository.save(userToBan));
    }

}
