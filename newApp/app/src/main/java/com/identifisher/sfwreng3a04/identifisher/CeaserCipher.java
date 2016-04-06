package com.identifisher.sfwreng3a04.identifisher;

import java.util.Arrays;

/**
 * Created by Christopher on 2016-04-05.
 */
public class CeaserCipher {

    final static char[] ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String encode(String toEncode, int key) {
        String toReturn = "";
        for (char c : toEncode.toCharArray()) {
            toReturn += ALPHABET[ (Arrays.binarySearch(ALPHABET, c) + key + ALPHABET.length*9000 ) % ALPHABET.length];
        }
        return toReturn;
    }

    public static String decode(String toDecode, int key) {
        String toReturn = "";
        for (char c : toDecode.toCharArray()) {
            toReturn += ALPHABET[ (Arrays.binarySearch(ALPHABET, c) - key + ALPHABET.length*9000) % ALPHABET.length];
        }
        return toReturn;
    }

    public static String[] encode(String toEncode[], int key) {
        String[] toReturn = new String[toEncode.length];
        int i = 0;
        for(String s : toEncode) {
            toReturn[i] = encode(s,key);
            i++;
        }
        return toReturn;
    }

    public static String[] decode(String toDecode[], int key) {
        String[] toReturn = new String[toDecode.length];
        int i = 0;
        for(String s : toDecode) {
            toReturn[i] = decode(s,key);
            i++;
        }
        return toReturn;
    }
}
