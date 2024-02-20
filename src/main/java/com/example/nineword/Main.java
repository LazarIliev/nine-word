package com.example.nineword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static List<String> allWords;
    static List<String> nineCharWords;

    static {
        URL wordsUrl = null;
        try {
            wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert wordsUrl != null;
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wordsUrl.openConnection().getInputStream()))) {
                allWords = bufferedReader.lines().skip(2).collect(Collectors.toList());
                nineCharWords = allWords.stream().filter(words -> words.length() == 9).collect(Collectors.toList());
                allWords.add("A");
                allWords.add("I");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> nineCharWordsWithout = new ArrayList<>();

        for (String word : nineCharWords) {
            if (isWordLadder(word)) {
                nineCharWordsWithout.add(word);
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("time elasped : " + timeElapsed);
        System.out.println(nineCharWordsWithout.size());
        System.out.println(nineCharWordsWithout);
    }


    private static boolean isWordLadder(String word) {
        if (word.length() == 1) {
            return true;
        }

        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);

            if (isValidWord(newWord) && isWordLadder(newWord)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isValidWord(String word) {
        return allWords.contains(word);
    }
}
