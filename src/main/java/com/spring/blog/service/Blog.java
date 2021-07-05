package com.spring.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.model.Post;
import com.spring.blog.repository.PostRepository;

@Service //gerenciado pelo Spring
public class Blog {
	
	@Autowired
    PostRepository postRepository;
    
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(long id) {
        return postRepository.findById(id).get();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }


}
