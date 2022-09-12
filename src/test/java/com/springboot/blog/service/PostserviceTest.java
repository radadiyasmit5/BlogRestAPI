package com.springboot.blog.service;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.impl.Postserviceimpl;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostserviceTest {

    @Autowired
    private Postservice postservice;


    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostRepository testrepository;



    @Test
    void whenGetPostById_then_shouldReturnPost() {
        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        Mockito.when(postRepository.findById(1l)).thenReturn(java.util.Optional.ofNullable(post));
        Long id = 1l;
       PostDto postDto = postservice.getpostbyid(id);

       assertEquals(id,postDto.getId());
    }

    @Test
    void whenGetPostById_then_shouldThrowResourceNotFoundException() {
        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        Mockito.when(postRepository.findById(1l)).thenReturn(java.util.Optional.ofNullable(post));
        Long id = 3l;
        assertThrows(ResourceNotFoundException.class,()->postservice.getpostbyid(id),
                "should throw ResourceNotFoundException");

    }

    @Test
    void WhenUpdatePost_By_Id_then_return_updated_post(){
        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        PostDto postDtoInput = PostDto.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();
        PostDto postDtoUpdated = PostDto.builder()
                .id(1l)
                .title("updated title")
                .description("updated")
                .content("updated post")
                .comments(null)
                .build();

        long id = 1l;
        Mockito.when(postRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(post));
        Mockito.when(postRepository.save(post)).thenReturn(post);

        PostDto updatedpost = postservice.updatepost(postDtoInput,id);
        System.out.println( updatedpost + " updatedpost");
        assertEquals(updatedpost.getId(),postDtoInput.getId());
        assertEquals(updatedpost.getContent(),postDtoInput.getContent());
        assertNotNull(updatedpost);

    }

    @Test
    void WhenUpdate_postby_id_then_throwResourceNotFound_exception(){
       long id=2l;

        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        PostDto postDtoInput = PostDto.builder()
                .id(2l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        Mockito.when(postRepository.findById(1l)).thenReturn(java.util.Optional.ofNullable(post));
           Mockito.when(postRepository.save(post)).thenReturn(post);

        assertThrows(ResourceNotFoundException.class,
                ()-> postservice.updatepost(postDtoInput,id),"should throw resource not found exception");
    }


    @Test
    void whendelete_postby_Id(){
        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();

        Mockito.when(postRepository.findById(1l)).thenReturn(java.util.Optional.ofNullable(post));
//        Mockito.when(postRepository.delete(post))
    postservice.deletpost(1l);
       assertEquals(0,postRepository.count());

    }

    @Test
    void WhenDeletePostBy_Id_Then_Throw_ResourceNotFound_Exception(){
        Post post = Post.builder()
                .id(1l)
                .title("test title")
                .description("test desc")
                .content("test content")
                .comments(null)
                .build();
        Mockito.when(postRepository.findById(1l)).thenReturn(java.util.Optional.ofNullable(post));


        assertThrows(ResourceNotFoundException.class,()->postservice.deletpost(2l),"should throw Resource not found exception");
    }

    @Test
    void WhenCreatePost_By_PostDto_Then_Return_SavedPost(){

        CommentDto comments = CommentDto.builder()
                .id(1l)
                .name("sdgvfsdjpovj")
                .body("sdvbsdvb")
                .email("sadvsadvsda").build();

        Set<CommentDto> setofcomments = new HashSet<>();
                setofcomments.add(comments);

        Post post = Post.builder()
                .id(100l)
                .title("bnmghmdfbhfdbfdb,")
                .description("tfdbfbdfbdfbdfb")
                .content("dfbdfbdfbdfbdfbdfb")
                .comments(null)
                .build();

        PostDto postDtoUpdated = PostDto.builder()
                .id(100l)
                .title("updated title")
                .description("updated")
                .content("updated post")
                .comments(null)
                .build();


    Mockito.when(postRepository.save(post)).thenReturn(post);

    PostDto  savedpostdto  = postservice.createpost(postDtoUpdated);





      assertEquals(savedpostdto.getId(),post.getId());


    }

private PostDto maptodto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(postDto.getContent());
        postDto.setComments(postDto.getComments());
        return  postDto;
}


}