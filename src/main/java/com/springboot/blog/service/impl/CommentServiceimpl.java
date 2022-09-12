package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentsRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {

    private PostRepository postRepository;
    private CommentsRepository commentsRepository;

    private ModelMapper modelMapper;


    public CommentServiceimpl(PostRepository postRepository, CommentsRepository commentsRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postid, CommentDto commentDto) {
        Comments comments = maptoEntity(commentDto);

       Post post= postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("post","id",postid));

       comments.setPost(post);

       commentsRepository.save(comments);
       return maptoDto(comments);
    }

    @Override
    public List<CommentDto> getallcommentbypostid(long postid) {

        List<Comments> comments = commentsRepository.findByPostId(postid);

      return   comments.stream().map(comment->maptoDto(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getcommentbyid(long postid, long commentid) {
        Post post = postRepository.findById(postid).orElseThrow(()->new ResourceNotFoundException("post","postid",postid));

        Comments comment = commentsRepository.findById(commentid).orElseThrow(()->new ResourceNotFoundException("comment","commentid",commentid));

        if(!comment.getPost().getId().equals(post.getId())){
              throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }

        return maptoDto(comment);
    }

    @Override
    public CommentDto updatecomment(long postid, long commentid, CommentDto commentDto) {
        Post post = postRepository.findById(postid).orElseThrow(()->new ResourceNotFoundException("post","postid",postid));

        Comments comment = commentsRepository.findById(commentid).orElseThrow(()->new ResourceNotFoundException("comment","commentid",commentid));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

       Comments rescomment= commentsRepository.save(comment);
       return maptoDto(rescomment);

    }

    @Override
    public void deletecomment(long postid, long commentid) {
        Post post = postRepository.findById(postid).orElseThrow(()->new ResourceNotFoundException("post","postid",postid));

        Comments comment = commentsRepository.findById(commentid).orElseThrow(()->new ResourceNotFoundException("comment","commentid",commentid));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }

     commentsRepository.delete(comment);
    }


    private CommentDto maptoDto(Comments comments){
        CommentDto commentDto = modelMapper.map(comments,CommentDto.class);
//        commentDto.setBody(comments.getBody());
//        commentDto.setId(comments.getId());
//        commentDto.setEmail(comments.getEmail());
//        commentDto.setName(comments.getName());

        return commentDto;
    }

    private  Comments maptoEntity(CommentDto commentDto){

        Comments comments = modelMapper.map(commentDto,Comments.class);
        //        comments.setBody(commentDto.getBody());
//        comments.setEmail(commentDto.getEmail());
//        comments.setId(commentDto.getId());
//        comments.setName(commentDto.getName());

        return comments;
    }
}

