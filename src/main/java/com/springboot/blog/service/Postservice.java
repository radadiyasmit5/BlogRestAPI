package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface Postservice {


    public PostDto createpost(PostDto postdto);

     public PostResponse getallposts(int pageno, int pageSize,String sortBy,String sortdir);

  public  PostDto getpostbyid(long id);

  public PostDto updatepost(PostDto postDto , long id);

  public void deletpost(long id);

}
