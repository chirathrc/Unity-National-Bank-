package lk.codebridge.app.core.util;

import java.security.SecureRandom;

public class UsernamePasswordGenerator {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final int PASSWORD_LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHAR_SET.length());
            password.append(CHAR_SET.charAt(index));
        }
        return password.toString();
    }

    public static String generateUsername(String firstName, String lastName, String nic) {
        if (firstName == null || lastName == null || nic == null) {
            throw new IllegalArgumentException("Required fields for username generation are missing");
        }

        String fInitial = firstName.trim().substring(0, 1).toLowerCase();
        String lName = lastName.trim().toLowerCase();

        // Get last 4 digits of NIC
        String cleanedNic = nic.replaceAll("[^0-9]", ""); // remove non-digit characters
        String nicSuffix = cleanedNic.length() >= 4 ? cleanedNic.substring(cleanedNic.length() - 4) : cleanedNic;

        return fInitial + lName + nicSuffix;
    }

}
