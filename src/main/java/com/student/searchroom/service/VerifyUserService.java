package com.student.searchroom.service;

import com.student.searchroom.entity.User;
import com.student.searchroom.entity.VerifyUser;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.VerifyUserRequest;
import com.student.searchroom.model.response.VerifyUserResponse;
import com.student.searchroom.repository.VerifyUserRepository;
import com.student.searchroom.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VerifyUserService {
    @Autowired
    private VerifyUserRepository verifyUserRepository;
    @Autowired
    private UserService userService;

    public VerifyUser registrationVerifyUser(VerifyUserRequest verifyUserRequest) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        if (verifyUserRequest.getImagesUrl() == null || verifyUserRequest.getImagesUrl().isEmpty())
            throw new SearchRoomException(ErrorCode.IMAGES_IS_REQUIRED);
        canSendVerifyRequest();

        return verifyUserRepository.save(verifyUserRequest.toVerifyUser(currentUsername));
    }

    public VerifyUser updateVerifyUser(VerifyUserRequest request) {
        String currentUsername = SecurityUtil.getCurrentUsername();
        VerifyUser verifyUserToUpdate = verifyUserRepository.findById(currentUsername).orElseThrow(() ->
                new SearchRoomException(ErrorCode.VERIFY_REQUEST_NOT_FOUND));

        canSendVerifyRequest();
        return verifyUserRepository.save(request.update(verifyUserToUpdate));
    }

    public VerifyUser getVerifyUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();

        User currentUser = userService.getByUsername(currentUsername);
        if (currentUser.getIsVerified())
            throw new SearchRoomException(ErrorCode.ACCOUNT_IS_VERIFIED);
        VerifyUser existVerifyUser = verifyUserRepository.findById(currentUsername).orElseThrow(() ->
                new SearchRoomException(ErrorCode.VERIFY_REQUEST_NOT_FOUND));
        return existVerifyUser;
    }

    public List<VerifyUserResponse> getVerifyRequestByAdmin(int pageIndex, int pageSize) {
        Pageable paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        return verifyUserRepository.findAll(paging).getContent().stream()
                .map(verifyUser -> VerifyUserResponse.from(verifyUser, userService.getByUsername(verifyUser.getUsername())))
                .collect(Collectors.toList());
    }

    public void acceptVerifyUser(String username) {
        VerifyUser existVerifyUser = verifyUserRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.VERIFY_REQUEST_NOT_FOUND));
        User userToUpdate = userService.getByUsername(username);
        verifyUserRepository.delete(existVerifyUser);
        userToUpdate.setIsVerified(true);
        userService.save(userToUpdate);
    }

    public void adminRequestUpdate(String username) {
        VerifyUser existVerifyUser = verifyUserRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.VERIFY_REQUEST_NOT_FOUND));
        existVerifyUser.setStatus(VerifyUser.Status.UPDATE_REQUEST);
        verifyUserRepository.save(existVerifyUser);
    }

    private void canSendVerifyRequest() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        User currentUser = userService.getByUsername(currentUsername);
        if (currentUser.getIsVerified())
            throw new SearchRoomException(ErrorCode.ACCOUNT_IS_VERIFIED);
    }
}
