package lk.codebridge.app.core.util;

import java.util.Random;

public class AccountNumberGenerate {

    public static String generateAccountNumber() {
        Random random = new Random();

        // First digit should not be 0
        int firstDigit = random.nextInt(9) + 1;

        // Generate remaining 11 digits
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(firstDigit);

        for (int i = 0; i < 11; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }
}
