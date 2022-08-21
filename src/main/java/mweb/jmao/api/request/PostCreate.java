package mweb.jmao.api.request;

import lombok.*;
import mweb.jmao.api.exception.InvalidRequest;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("바보")){
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }

    //빌더의 장점
    // - 가독성이 좋다, 값 생성 유연함, 필요값만 받을수있음// -> 오버로딩 가능한 조건?
    // - 객체의 불변성
}
