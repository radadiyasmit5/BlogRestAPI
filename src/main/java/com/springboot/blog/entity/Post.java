package com.springboot.blog.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//lombok for getter and setters
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity


@Table(
        name = "posts" , uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} //uniq key for colum title

)
public class Post {


    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY) //autogenret id
    public Long id;

    @Column(name = "title" , nullable = false)
    public String title;
    @Column(name = "description",nullable = false )
    public String description;
    @Column(name = "content" , nullable = false)
    public String content;

@OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comments> comments= new HashSet<>();
}
