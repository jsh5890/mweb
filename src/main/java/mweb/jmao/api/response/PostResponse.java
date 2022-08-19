package mweb.jmao.api.response;


import lombok.Builder;
import lombok.Getter;
import mweb.jmao.api.domain.Post;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(),10));
        this.content = content;
    }
}
