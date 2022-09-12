package com.springboot.blog.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//lombok for getter and setters
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Entity


@Table(
        name = "posts" , uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} //uniq key for colum title

)
public class Post {


    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY) //autogenret id
    public long id;

    @Column(name = "title" , nullable = false)
    public String title;
    @Column(name = "description",nullable = false )
    public String description;
    @Column(name = "content" , nullable = false)
    public String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comments> comments= new HashSet<>();

    public Post() {
    }

    public Post(Long id, String title, String description , String content, Set<Comments> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Set<Comments> getComments() {
        return comments;
    }


}
