package com.example.mappers;


import com.example.dto.comment.CreateCommentDto;
import com.example.entity.Comments;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comments commentDtoToComment(CreateCommentDto createCommentDto);
}
