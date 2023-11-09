package krsu.vblindar.hillcipherweb.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class AlphabetTable {


    public AlphabetTable(Map<Character, Double> decodeAlphabet) {
        this.decodeAlphabet = decodeAlphabet;

    }

    private Map<Character, Double> originalAlphabet = new HashMap<>();

    {
        originalAlphabet.put('о', 10.98);
        originalAlphabet.put('е', 8.45);
        originalAlphabet.put('а', 8.01);
        originalAlphabet.put('и', 7.37);
        originalAlphabet.put('н', 6.70);
        originalAlphabet.put('т', 6.32);
        originalAlphabet.put('с', 5.47);
        originalAlphabet.put('р', 4.75);
        originalAlphabet.put('в', 4.54);
        originalAlphabet.put('л', 4.34);
        originalAlphabet.put('к', 3.49);
        originalAlphabet.put('м', 3.21);
        originalAlphabet.put('д', 2.98);
        originalAlphabet.put('п', 2.81);
        originalAlphabet.put('у', 2.62);
        originalAlphabet.put('я', 2.01);
        originalAlphabet.put('ы', 1.90);
        originalAlphabet.put('ь', 1.74);
        originalAlphabet.put('г', 1.69);
        originalAlphabet.put('з', 1.64);
        originalAlphabet.put('б', 1.59);
        originalAlphabet.put('ч', 1.45);
        originalAlphabet.put('й', 1.21);
        originalAlphabet.put('х', 0.97);
        originalAlphabet.put('ж', 0.94);
        originalAlphabet.put('ш', 0.72);
        originalAlphabet.put('ю', 0.64);
        originalAlphabet.put('ц', 0.49);
        originalAlphabet.put('щ', 0.36);
        originalAlphabet.put('э', 0.33);
        originalAlphabet.put('ф', 0.27);
        originalAlphabet.put('ъ', 0.04);
        originalAlphabet.put('ё', 0.02);
        originalAlphabet.put(' ', 0.02);
        originalAlphabet.put('?', 0.02);
        originalAlphabet.put('.', 0.02);
        originalAlphabet.put(',', 0.02);


    }

    private Map<Character, Double> decodeAlphabet;

}
