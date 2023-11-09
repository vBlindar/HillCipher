package krsu.vblindar.hillcipherweb.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class Util {

    private final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя., ?";

    private final int SIZE = ALPHABET.length();

    private final Map<Character,Double> frequencyTable = new HashMap<>();{
        frequencyTable.put('о',0.090);
        frequencyTable.put('е',0.072);
        frequencyTable.put('ё',0.072);
        frequencyTable.put('а',0.062);
        frequencyTable.put('и',0.062);
        frequencyTable.put('т',0.053);
        frequencyTable.put('н',0.053);
        frequencyTable.put('с',0.045);
        frequencyTable.put('р',0.040);
        frequencyTable.put('в',0.038);
        frequencyTable.put('л',0.035);
        frequencyTable.put('к',0.028);
        frequencyTable.put('м',0.026);
        frequencyTable.put('д',0.025);
        frequencyTable.put('п',0.023);
        frequencyTable.put('у',0.021);
        frequencyTable.put('я',0.018);
        frequencyTable.put('ы',0.016);
        frequencyTable.put('з',0.016);
        frequencyTable.put('ъ',0.014);
        frequencyTable.put('ь',0.014);
        frequencyTable.put('б',0.014);
        frequencyTable.put('г',0.013);
        frequencyTable.put('ч',0.012);
        frequencyTable.put('й',0.010);
        frequencyTable.put('х',0.009);
        frequencyTable.put('ж',0.007);
        frequencyTable.put('ю',0.006);
        frequencyTable.put('ш',0.006);
        frequencyTable.put('ц',0.004);
        frequencyTable.put('щ',0.003);
        frequencyTable.put('э',0.002);
        frequencyTable.put('ф',0.002);
    }
}
