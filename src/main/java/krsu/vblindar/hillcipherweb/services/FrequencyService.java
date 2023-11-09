package krsu.vblindar.hillcipherweb.services;


import krsu.vblindar.hillcipherweb.utils.AlphabetEntry;
import krsu.vblindar.hillcipherweb.utils.AlphabetTable;
import krsu.vblindar.hillcipherweb.utils.MapUtil;
import krsu.vblindar.hillcipherweb.utils.Util;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FrequencyService {

    public List<AlphabetEntry> getAlphabetEntryesList(AlphabetTable alphabetTable) {
        List<AlphabetEntry> alphabetEntries = new ArrayList<>();
        for (Map.Entry<Character,Double> entry : alphabetTable.getOriginalAlphabet().entrySet()) {
            alphabetEntries.add(AlphabetEntry.builder().originalLetter(String.format("%s %.2f%%",entry.getKey(), entry.getValue())).build());
        }
        int count = 0;
        for (Map.Entry<Character,Double> entry : alphabetTable.getDecodeAlphabet().entrySet()) {
            var object = alphabetEntries.get(count);
            object.setDecodeLetter(String.format("%s %.2f%%",entry.getKey(), entry.getValue()));
            alphabetEntries.remove(count);
            alphabetEntries.add(count,object);
            count++;
        }
        return alphabetEntries;
    }
    public AlphabetTable getFrequencyData(String text,boolean check){
        int totalLetters = text.length();
        Map<Character, Integer> letterCounts = countLetterFrequencies(text);
        Map<Character, Double> decodeAlphabet = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
            char letter = entry.getKey();
            int count = entry.getValue();
            double frequency = (double) count / totalLetters * 100.0;
            decodeAlphabet.put(letter, frequency);
        }
        var tables = new AlphabetTable(MapUtil.sortByValue(decodeAlphabet));

        for (char key : tables.getOriginalAlphabet().keySet()) {
            if (!decodeAlphabet.containsKey(key)) {
                decodeAlphabet.put(key, 0.0);
            }
        }
        if(!check) {
            tables.setDecodeAlphabet(MapUtil.sortByValue(decodeAlphabet));
            tables.setOriginalAlphabet(MapUtil.sortByValue(tables.getOriginalAlphabet()));
        }else{
            tables.setDecodeAlphabet(MapUtil.sortByValueDes(decodeAlphabet));
            tables.setOriginalAlphabet(MapUtil.sortByValueDes(tables.getOriginalAlphabet()));
        }

        return tables;
    }
//    public AlphabetTable getFrequencyData(String text){
//        int totalLetters = text.length();
//        Map<Character, Integer> letterCounts = countLetterFrequencies(text);
//        Map<Character, Double> decodeAlphabet = new HashMap<>();
//        for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
//            char letter = entry.getKey();
//            int count = entry.getValue();
//            double frequency = (double) count / totalLetters * 100.0;
//            decodeAlphabet.put(letter,frequency);
//        }
//
//        var tables = new AlphabetTable(MapUtil.sortByValue(decodeAlphabet));
//
//        for (char key : tables.getOriginalAlphabet().keySet()) {
//            if (!decodeAlphabet.containsKey(key)) {
//                decodeAlphabet.put(key, 0.0);
//            }
//        }
//        return new AlphabetTable(MapUtil.sortByValue(decodeAlphabet));
//    }


    public static Map<Character, Integer> countLetterFrequencies(String text) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (char letter : text.toCharArray()) {

                letter = Character.toLowerCase(letter); // Преобразование в строчную букву
                letterCounts.put(letter, letterCounts.getOrDefault(letter, 0) + 1);

        }
        return letterCounts;
    }

    public static double calculateLikelihood(String text) {
        Util alphabet = new Util();
        var table = alphabet.getFrequencyTable();

        double likelihood = 0.0;
        for (char letter : text.toCharArray()) {
            String letterStr = String.valueOf(letter).toLowerCase(); // Преобразуем букву в строчный формат
            if (table.containsKey(letterStr.charAt(0))) {
                double letterFrequency = table.get(letterStr.charAt(0));
                likelihood += letterFrequency; // Используем логарифм вероятности для избежания переполнения
            } else {

                double defaultFrequency = 0.01; // Например, 1%
                likelihood += defaultFrequency;
            }
        }

        return likelihood;
    }


}
