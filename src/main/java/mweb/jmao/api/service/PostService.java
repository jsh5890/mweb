package mweb.jmao.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mweb.jmao.api.domain.Post;
import mweb.jmao.api.repository.PostRepository;
import mweb.jmao.api.request.PostCreate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }
}
