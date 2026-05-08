package com.rytways.helper;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

    private static final String INPUT_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(INPUT_STRING.length());
            char randomChar = INPUT_STRING.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

   
}