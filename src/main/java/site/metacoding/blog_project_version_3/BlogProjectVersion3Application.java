package site.metacoding.blog_project_version_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogProjectVersion3Application {

	public static void main(String[] args) {
		SpringApplication.run(BlogProjectVersion3Application.class, args);
	}
}
