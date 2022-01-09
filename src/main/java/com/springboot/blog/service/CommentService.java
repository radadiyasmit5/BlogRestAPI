package com.springboot.blog.service;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment (long postid,CommentDto commentDto);

    public List<CommentDto> getallcommentbypostid(long postid);

    public CommentDto getcommentbyid(long postid,long commentid);

    public CommentDto updatecomment(long postid,long commentid , CommentDto commentDto);

    public  void deletecomment(long postid, long commentid);
}
