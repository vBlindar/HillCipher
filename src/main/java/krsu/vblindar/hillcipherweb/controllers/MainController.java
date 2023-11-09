package krsu.vblindar.hillcipherweb.controllers;

import krsu.vblindar.hillcipherweb.services.FrequencyService;
import krsu.vblindar.hillcipherweb.services.ReadFile;
import krsu.vblindar.hillcipherweb.utils.AlphabetEntry;
import krsu.vblindar.hillcipherweb.utils.AlphabetTable;
import krsu.vblindar.hillcipherweb.utils.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReadFile readFile;
    private final FrequencyService frequency;

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @PostMapping("/read-file")
    public ResponseEntity<String> readFile(MultipartFile file) {

        return readFile.readFile(file);
    }

    @GetMapping("/frequency-table")
    public String getFrequencyTable(@RequestParam String input, Model model){
        var alphabetTable = frequency.getFrequencyData(input);

        List<AlphabetEntry> alphabetEntries = frequency.getAlphabetEntryesList(alphabetTable);

        model.addAttribute("alphabetEntries", alphabetEntries);

        return "tables";
    }



    @GetMapping("/frequency-maps")
    public ResponseEntity<AlphabetTable> getMaps(@RequestParam String input){
        var value = frequency.getFrequencyData(input);
        return ResponseEntity.ok(value);
    }


}
