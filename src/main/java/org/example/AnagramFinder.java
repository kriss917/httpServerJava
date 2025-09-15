package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AnagramFinder {

    private final String filename = "words.txt";

    public List<String> findAnagrams(String word) throws IOException {
        var anagrams = new ArrayList<String>();
        var wordList = loadWordList();
        var sortedWord = sortWords(word).toLowerCase();
        wordList.forEach( w -> {
            if (sortedWord.equals(sortWords(w))) anagrams.add(w);
        });
        return anagrams;
    }

    private String sortWords(String word) {
        var characters = word.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
    }

    private Set<String> loadWordList() throws IOException {
        var wordList = new ArrayList<String>();

        try (var words = getClass().getClassLoader().getResourceAsStream(filename)) {
            var reader = new BufferedReader(new InputStreamReader(words));
            String word;
            while ((word = reader.readLine()) != null) {
                wordList.add(word.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return new HashSet<>(wordList);
    }
}

