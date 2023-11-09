package krsu.vblindar.hillcipherweb.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ReadFile {

    public ResponseEntity<String> readFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return ResponseEntity.ok(content.toString().toLowerCase());
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Файл пустой или не был выбран.");
            }
        }
        return ResponseEntity.badRequest().body("Файл пустой или не был выбран.");
    }
}
