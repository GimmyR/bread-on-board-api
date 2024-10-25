package mg.breadOnBoard.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mg.breadOnBoard.service.FileNotFoundException;
import mg.breadOnBoard.service.ImageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/images/{filename:.+}")
	public ResponseEntity<Resource> get(@PathVariable String filename) {
		
		ResponseEntity<Resource> result = null;
		
		try {
			
			Resource resource = imageService.get(filename);
			result = ResponseEntity
						.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			
		} catch (MalformedURLException | FileNotFoundException e) {

			e.printStackTrace();
			
		} return result;
		
	}

}
