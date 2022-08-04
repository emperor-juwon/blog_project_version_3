package site.metacoding.blog_project_version_3.web.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
