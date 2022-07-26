package site.metacoding.blog_project_version_3.web.dto.post;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.post.Post;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRespDto {
    private Page<Post> posts;
    private List<Category> categorys;
}
