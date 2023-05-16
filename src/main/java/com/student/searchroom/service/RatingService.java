package com.student.searchroom.service;

import com.student.searchroom.entity.Rating;
import com.student.searchroom.entity.User;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.request.RatingRequest;
import com.student.searchroom.model.response.RatingResponse;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.repository.RatingRepository;
import com.student.searchroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;


    public Rating addRating(RatingRequest request, String currentUser, String usernameToRate) {
        canRating(currentUser, usernameToRate);
        User userToRate = userRepository.findById(usernameToRate).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));

        Rating ratingToSave = new Rating();
        ratingToSave.setCreatedBy(currentUser);
        ratingToSave.setCreatedAt(new Date());
        ratingToSave.setLastUpdateAt(new Date());
        ratingToSave.setUsername(usernameToRate);
        ratingToSave.setRatingValue(request.getRatingValue());
        ratingToSave.setRatingContent(request.getRatingContent());
        Rating result = ratingRepository.save(ratingToSave);

        calculateAndUpdateToUser(userToRate);
        return result;
    }

    public Rating updateRating(RatingRequest request, String currentUser, Long ratingId) {
        Rating ratingToUpdate = ratingRepository.findById(ratingId).orElseThrow(() ->
                new SearchRoomException(ErrorCode.RATING_ALREADY_EXIST));
        User userToRate = userRepository.findById(ratingToUpdate.getUsername()).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));

        if (!currentUser.equals(ratingToUpdate.getCreatedBy()))
            throw new SearchRoomException(ErrorCode.FORBIDDEN);
        ratingToUpdate.setRatingContent(request.getRatingContent());
        ratingToUpdate.setRatingValue(request.getRatingValue());
        ratingToUpdate.setLastUpdateAt(new Date());
        Rating result = ratingRepository.save(ratingToUpdate);

        calculateAndUpdateToUser(userToRate);
        return result;
    }

    public List<RatingResponse> getByUsername(String username) {
        List<Rating> ratings = ratingRepository.findAllByUsernameOrderByCreatedAt(username);
        List<RatingResponse> result = new ArrayList<>();
        ratings.forEach(rating -> {
            result.add(RatingResponse.from(rating, UserResponse.from(userRepository.getById(rating.getUsername()))));
        });
        return result;
    }

    public void deleteById(String currentUser, Long ratingId) {
        Rating ratingToDelete = ratingRepository.findById(ratingId).orElseThrow(() ->
                new SearchRoomException(ErrorCode.RATING_ALREADY_EXIST));
        User userToRate = userRepository.findById(ratingToDelete.getUsername()).orElseThrow(() ->
                new SearchRoomException(ErrorCode.USER_NOT_FOUND));
        if (!currentUser.equals(ratingToDelete.getCreatedBy())) throw new SearchRoomException(ErrorCode.FORBIDDEN);
        ratingRepository.delete(ratingToDelete);

        calculateAndUpdateToUser(userToRate);
    }

    private void calculateAndUpdateToUser(User userToRate) {
        List<Rating> ratings = ratingRepository.findAllByUsername(userToRate.getUsername());
        Double sum = 0.0;
        for (Rating rating : ratings) {
            sum = sum + rating.getRatingValue();
        }

        if (sum != 0.0) {
            Double userRatingValue = sum/ratings.size();
            userToRate.setRatingValue(userRatingValue);
            userRepository.save(userToRate);
        }
    }

    private void canRating(String currentUser, String userToRate) {
        Rating rating = ratingRepository.findByCreatedByAndUsername(currentUser, userToRate);
        if (rating != null) throw new SearchRoomException(ErrorCode.RATING_ALREADY_EXIST);
        if (currentUser.equals(userToRate)) throw new SearchRoomException(ErrorCode.BAD_REQUEST);
    }
}
