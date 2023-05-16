package com.student.searchroom.controller;

import com.student.searchroom.entity.Rating;
import com.student.searchroom.model.request.RatingRequest;
import com.student.searchroom.model.response.RatingResponse;
import com.student.searchroom.security.SecurityUtil;
import com.student.searchroom.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/user/{usernameToRate}")
    public ResponseEntity<Rating> createRating(@RequestBody @Valid RatingRequest  ratingRequest,
                                               @PathVariable String usernameToRate) {
        String currentUser = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(ratingService.addRating(ratingRequest, currentUser, usernameToRate));
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating(@RequestBody @Valid RatingRequest ratingRequest,
                                               @PathVariable Long ratingId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        return ResponseEntity.ok(ratingService.updateRating(ratingRequest, currentUser, ratingId));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long ratingId) {
        String currentUser = SecurityUtil.getCurrentUsername();
        ratingService.deleteById(currentUser, ratingId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<RatingResponse>> getRatingByUsername(@PathVariable String username) {
        return ResponseEntity.ok(ratingService.getByUsername(username));
    }
}
