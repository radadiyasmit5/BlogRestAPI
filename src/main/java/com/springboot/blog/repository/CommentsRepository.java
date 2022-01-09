package com.springboot.blog.repository;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long > {

    List<Comments> findByPostId(long postid);

}
