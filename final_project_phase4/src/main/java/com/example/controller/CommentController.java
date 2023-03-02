package com.example.controller;


import com.example.dto.comment.CreateCommentDto;
import com.example.entity.Comments;
import com.example.mappers.CommentMapperImpl;
import com.example.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comment")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final CommentMapperImpl commentMapper;


    @PostMapping("/create")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<String> create(@Valid @RequestBody CreateCommentDto createCommentDto) {
        Comments comments = commentMapper.commentDtoToComment(createCommentDto);
        commentService.create(comments);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
