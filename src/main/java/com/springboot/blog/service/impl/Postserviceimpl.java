package com.springboot.blog.service.impl;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResosurceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.Postservice;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class Postserviceimpl implements Postservice {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public Postserviceimpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }


    @Override
    public PostDto createpost(PostDto postdto) {


        Post post = maptoentity(postdto);

        Post savepost = postRepository.save(post);

        PostDto postdtoresponse = maptodto(savepost);

        return postdtoresponse;
    }

    @Override
    public PostResponse getallposts(int pageno, int pageSize, String sortBy, String sortdir) {


        Sort sort = sortdir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageble = PageRequest.of(pageno, pageSize, sort);
        Page<Post> postlist = postRepository.findAll(pageble);
        List<Post> listofpost = postlist.getContent();

        List<PostDto> content = listofpost.stream().map(p -> maptodto(p)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageno(postlist.getNumber());
        postResponse.setPagesize(postlist.getSize());
        postResponse.setTotalElement(postlist.getTotalElements());
        postResponse.setLast(postlist.isLast());
        postResponse.setTotalPages(postlist.getTotalPages());

        return postResponse;

    }

    @Override
    public PostDto getpostbyid(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResosurceNotFoundException("post", "id", id));
        return maptodto(post);
    }

    @Override
    public PostDto updatepost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResosurceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post postresponse = postRepository.save(post);
        return maptodto(postresponse);
    }

    @Override
    public void deletpost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResosurceNotFoundException("post", "id", id));

        postRepository.delete(post);

    }


    //convert from dto to post
    private Post maptoentity(PostDto postdto) {

        Post post = modelMapper.map(postdto,Post.class);
//        post.setId(postdto.getId());
//        post.setTitle(postdto.getTitle());
//        post.setContent(postdto.getContent());
//        post.setDescription(postdto.getDescription());
        return post;

    }

    //convert from post to dto
    private PostDto maptodto(Post post) {
        PostDto postdtoresponse = modelMapper.map(post,PostDto.class);
//        postdtoresponse.setId(post.getId());
//        postdtoresponse.setTitle(post.getTitle());
//        postdtoresponse.setContent(post.getContent());
//        postdtoresponse.setDescription(post.getDescription());
        return postdtoresponse;
    }
}
