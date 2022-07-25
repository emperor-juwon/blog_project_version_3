package site.metacoding.blog_project_version_3.web.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blog_project_version_3.domain.category.Category;
import site.metacoding.blog_project_version_3.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryWriteReqDto {

    @Size(min = 1, max = 60)
    @NotBlank
    private String title;

    public Category toEntity(User principal) {
        Category category = new Category();
        category.setTitle(title);
        category.setUser(principal);
        return category;
    }
}
