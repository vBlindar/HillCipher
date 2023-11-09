package krsu.vblindar.hillcipherweb.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlphabetEntry {
    private String originalLetter;
    private String decodeLetter;

}
