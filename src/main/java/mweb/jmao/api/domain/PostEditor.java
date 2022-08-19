package mweb.jmao.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    public final String title;
    public final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
