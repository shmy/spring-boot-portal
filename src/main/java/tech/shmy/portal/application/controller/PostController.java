package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.Post;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.service.LocaleService;
import tech.shmy.portal.application.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private LocaleService localeService;
    @GetMapping("")
    public ResultBean<Post[]> index() {
        Post[] postEntities = this.postService.list();
        return ResultBean.success(postEntities);
    }
    @GetMapping("{id}")
    public ResultBean<Post> detail(@PathVariable("id") String id) {
        try {
            Post postEntity = this.postService.detail(id);
            return ResultBean.success(postEntity);
        } catch (Exception e) {
            return ResultBean.error(localeService.get("post.notExist"));
        }
    }

}
