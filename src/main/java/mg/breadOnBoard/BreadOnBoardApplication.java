package mg.breadOnBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mg.breadOnBoard.service.ImageService;

@SpringBootApplication
public class BreadOnBoardApplication implements CommandLineRunner {
	
	@Autowired
	private ImageService imageService;

	public static void main(String[] args) {
		SpringApplication.run(BreadOnBoardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		imageService.createDirectory();
		
	}

}
