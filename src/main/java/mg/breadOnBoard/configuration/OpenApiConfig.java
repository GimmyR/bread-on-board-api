package mg.breadOnBoard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openApi() {
		
		Info info = new Info().title("Documentation for Bread on Board API");
		
		return new OpenAPI().info(info);
		
	}

}
