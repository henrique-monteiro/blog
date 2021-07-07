package com.spring.blog.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.blog.model.Post;
import com.spring.blog.service.BlogService;

@Controller
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@RequestMapping(value="/posts", method=RequestMethod.GET)
    public ModelAndView getPosts(){
        ModelAndView mv = new ModelAndView("posts");	//"posts é o nome do arquivo em html"
        List<Post> posts = blogService.findAll();	//retorna a lista de posts
        mv.addObject("posts", posts);
        return mv;
    }
	
	@RequestMapping(value="/posts/{id}", method=RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") long id){    //recebe o parâmetro (PathVariable("id"), tipo do id)
        ModelAndView mv = new ModelAndView("postDetails");	//"postDetails é o nome do arquivo em html"
        Post post = blogService.findById(id);	//retorna um post específico
        mv.addObject("post", post);	//"post" é o nome da variavel no arquivo html
        return mv;
    }
	
	@RequestMapping(value="/newpost", method=RequestMethod.GET)
    public String getPostForm(){	//metodo apenas chama o form para incluir um novo post
        return "postForm";
    }
	
	@RequestMapping(value="/newpost", method=RequestMethod.POST)
    public String savePost(Post post, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/newpost";
        }
        post.setData(LocalDate.now());
        blogService.save(post);
        return "redirect:/posts";
    }
	

}
