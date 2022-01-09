package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private  CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postid}/comment")
    public ResponseEntity<CommentDto> createcomments(@PathVariable(value = "postid")  long postid ,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity(commentService.createComment(postid,commentDto), HttpStatus.CREATED);
    }


    @GetMapping("/post/{postid}/comments")
    public ResponseEntity<CommentDto> getallcommentsbypostid(@PathVariable (value = "postid") long postid){

        return new ResponseEntity(commentService.getallcommentbypostid(postid),HttpStatus.OK);

    }

    @GetMapping("/post/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> getcommentbyid(@PathVariable (value = "postid") long postid ,@PathVariable (value = "commentid") long commentid){
        return new ResponseEntity(commentService.getcommentbyid(postid,commentid),HttpStatus.OK);
    }

    @PutMapping("/post/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> updatecomment(@PathVariable (value = "postid") long postid ,@PathVariable (value = "commentid") long commentid,
                                                    @Valid @RequestBody CommentDto commentdto){
        return new ResponseEntity(commentService.updatecomment(postid,commentid,commentdto),HttpStatus.OK);
    }

    @DeleteMapping("/post/{postid}/comments/{commentid}")
    public String deletecomment(@PathVariable (value = "postid") long postid ,@PathVariable (value = "commentid") long commentid){
        commentService.deletecomment(postid,commentid);

        return "Comment deleted successfully";
    }


}
