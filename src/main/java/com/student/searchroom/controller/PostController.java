package com.student.searchroom.controller;

import com.student.searchroom.entity.House;
import com.student.searchroom.model.request.RegistrationPostRequest;
import com.student.searchroom.model.request.UpdatePostRequest;
import com.student.searchroom.model.response.HouseResponse;
import com.student.searchroom.service.PostService;
import com.student.searchroom.solr.entity.HouseSolr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<HouseResponse> registrationPost(@RequestBody @Valid RegistrationPostRequest request) {
        return ResponseEntity.ok(postService.registrationPost(request));
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<HouseResponse> updatePost(@RequestBody UpdatePostRequest request,
                                            @PathVariable Long postId) {
        return ResponseEntity.ok(postService.updatePost(request, postId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<HouseResponse> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping("/posts/user/{username}")
    public ResponseEntity<List<HouseResponse>> getPostsByUsername(@PathVariable String username,
                                                          @RequestParam(required = false, defaultValue = "1", name = "page_index") int pageIndex,
                                                          @RequestParam(required = false, defaultValue = "10", name = "page_size") int pageSize) {
        return ResponseEntity.ok(postService.getAllByCreator(username, pageIndex, pageSize));
    }

    @GetMapping("/posts/distance")
    public ResponseEntity<List<HouseResponse>> getPostByDistance(@RequestParam double lat,
                                                                 @RequestParam double lnp,
                                                                 @RequestParam double distance) {
        return ResponseEntity.ok(postService.getByDistance(lat, lnp, distance));
    }

}
