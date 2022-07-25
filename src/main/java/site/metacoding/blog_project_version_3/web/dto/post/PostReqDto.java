package site.metacoding.blog_project_version_3.web.dto.post;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.post.Post;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostReqDto {

    private List<Post> posts;
    private List<Category> categorys;
}
