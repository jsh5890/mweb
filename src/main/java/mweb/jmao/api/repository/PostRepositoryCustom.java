package mweb.jmao.api.repository;

import mweb.jmao.api.domain.Post;
import mweb.jmao.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
