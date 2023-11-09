package krsu.vblindar.hillcipherweb.controllers;

import krsu.vblindar.hillcipherweb.services.ReadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReadFile readFile;

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @PostMapping("/read-file")
    public ResponseEntity<String> readFile(MultipartFile file) {

        return readFile.readFile(file);
    }



}
