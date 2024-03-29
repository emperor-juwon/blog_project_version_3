package site.metacoding.blog_project_version_3.web.dto.love;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoveRespDto {

    private Integer loveId;
    private PostDto post;

    @Data
    public class PostDto {
        private Integer postId;
        private String title;
    }
}
