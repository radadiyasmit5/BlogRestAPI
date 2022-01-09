package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "email should not be null")
    @Email
    private String email;
    @Size(min = 10 , message = "comment body must be minimum 10 character long")
    private String body;

}
