package mweb.jmao.api.service;

import mweb.jmao.api.domain.Post;
import mweb.jmao.api.exception.PostNotFound;
import mweb.jmao.api.repository.PostRepository;
import mweb.jmao.api.request.PostCreate;
import mweb.jmao.api.request.PostEdit;
import mweb.jmao.api.request.PostSearch;
import mweb.jmao.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {
    //변수명 일괄변경 shift + F6
    
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        // given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }

    @Test
    @DisplayName("글 한개 조회")
    void test2(){
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

//        Long postId = 1L;

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);

        assertEquals("foo",response.getTitle());
        assertEquals("bar",response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        // given
        List<Post> postList = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("jmao 제목 : " + i)
                        .content("좌이 : " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        //sql -> select, limit, ofset
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

//        Pageable pageable = PageRequest.of(0,5, Direction.DESC,"id");

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("jmao 제목 : 30", posts.get(0).getTitle());
        assertEquals("jmao 제목 : 26", posts.get(4).getTitle());
    }
    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("쏘오갈")
                .content("아파트")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id : " + post.getId()));
        assertEquals("쏘오갈", changedPost.getTitle());
        assertEquals("아파트", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("쏘갈")
                .content("초가집")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id : " + post.getId()));
        assertEquals("쏘갈", changedPost.getTitle());
        assertEquals("초가집", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 삭제")
    void test6(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 삭제 - 존재하지 않는 글")
    void test8(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재 하지 않는 글")
    void test9(){
        // given
        Post post = Post.builder()
                .title("쏘갈")
                .content("아파트")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("쏘갈")
                .content("초가집")
                .build();

        /// expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}