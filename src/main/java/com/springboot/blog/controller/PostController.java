package com.springboot.blog.controller;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.Postservice;
import com.springboot.blog.utils.AppConstant;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private Postservice postservice;


    public PostController(Postservice postservice) {
        this.postservice = postservice;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createpost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postservice.createpost(postDto),  HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getallposts(@RequestParam (value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NO,required = false) int pageno,
                                    @RequestParam(value = "pageSize" ,defaultValue = AppConstant.DEFAULT_PAGE_SIZE ,required = false) int pageSize,
                                    @RequestParam(value="sortBy" ,defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortby,
                                    @RequestParam (value = "sortDir" ,defaultValue = AppConstant.DEFAULT_SORT_ORDER , required = false) String sortdir){

        return postservice.getallposts(pageno,pageSize,sortby,sortdir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getpostbyid(@PathVariable(name="id") long id){

        return new ResponseEntity(postservice.getpostbyid(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatebyid (@Valid @RequestBody PostDto postdto , @PathVariable(name = "id") long id) {
            PostDto responsedto = postservice.updatepost(postdto, id);

            return new ResponseEntity(responsedto , HttpStatus.OK);


    }

    @PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
    public ResponseEntity<String> deletepost(@PathVariable (name = "id") long id){
        postservice.deletpost(id);
        return  new ResponseEntity("Entity deleted successfully" , HttpStatus.OK );
    }}
