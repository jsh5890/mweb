package mweb.jmao.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mweb.jmao.api.request.PostCreate;
import mweb.jmao.api.request.PostEdit;
import mweb.jmao.api.request.PostSearch;
import mweb.jmao.api.response.PostResponse;
import mweb.jmao.api.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) throws Exception {
        postService.write(request);
//        return Map.of("postId", postId);
    }

    /**
     * /posts -> 글 전체 조회 (검색 페이징)
     * /posts/{postId} -> 글한개 조회
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {

        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId, postEdit);
    }
}
