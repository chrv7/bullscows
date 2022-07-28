package bullscows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class UserInteraction {

    protected static int sendNumOfLength() {
        System.out.println("Input the length of the secret code:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int length;
            String answer = reader.readLine();
            try {
                length = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", answer);
                return 0;
            }
            if (length > 36 || length == 0) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
                throw new IOException();
            } else {
                return length;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    protected static int sendNumOfSymbols() {
        System.out.println("Input the number of possible symbols in the code:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String answer = reader.readLine();
            int length = Integer.parseInt(answer);
            if (length > 36 || length == 0) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", length);
                throw new IOException();
            } else {
                return length;
            }
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    protected static char[] generateNewCode(int length, int symbols) {
        List<Character> fill = new ArrayList<>();

        for (int i = 48; i <= 57; i++) {
            fill.add((char) i);
        }
        if (symbols > 10) {
            for (int i = 97; i < 97 + symbols - 10; i++) {
                fill.add((char) i);
            }
        }

        Collections.shuffle(fill);

        Set<Character> set = new HashSet<>();
        while (set.size() != length) {
            Collections.shuffle(fill);
            set.add(fill.get(0));
        }

        char[] code = new char[length];
        int it = 0;
        for (Character c : set) {
            if (it == length) {
                break;
            }
            code[it] = c;
            it++;
        }


        return code;
    }

    protected static void preparedMessage(int length, int symbols) {
        String stars = "";
        for (int i = 0; i < length; i ++) {
            stars += "*";
        }
        char startChar = 97;
        char endChar = (char) (startChar + symbols - 10 - 1);
        if (symbols > 10) {
            System.out.printf("The secret is prepared: %s (0-9, %c-%c).\n", stars, startChar, endChar);
        } else {
            System.out.printf("The secret is prepared: %s (0-9).\n", stars);
        }
    }

    protected static char[] sendCode(int length) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String[] answer = reader.readLine().split("");
            char[] code = new char[length];
            if (answer.length != length) {
                throw new IOException();
            } else {
                int i = 0;
                for (String s : answer) {
                    code[i] = s.charAt(0);
                    i++;
                }
                return code;
            }
        } catch (IOException e) {
            System.out.println("Wrong code format");
            return null;
        }
    }

    protected static int checkCows(char[] secretCode, char[] playerCode) {
        int count = 0;
        for (int i = 0; i < secretCode.length; i++) {
            for (int j = 0; j < playerCode.length; j++) {
                if (playerCode[j] == secretCode[i]) {
                    playerCode[j] = 58;
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    protected static int checkBulls(char[] secretCode, char[] playerCode) {
        int count = 0;
        for (int i = 0; i < secretCode.length; i++) {
            if (secretCode[i] == playerCode[i]) {
                playerCode[i] = 58;
                secretCode[i] = 59;
                count++;
            }
        }
        return count;
    }

    protected static int sendResult(int length, int cows, int bulls) {
        if (cows == 0 && bulls == 0) {
            System.out.print("Grade: None\n");
        } else if (bulls == length) {
            System.out.printf("Grade: %d bull(s)\n", bulls);
            return 1;
        } else if (cows == 0) {
            System.out.printf("Grade: %d bull(s)\n", bulls);
        } else if (bulls == 0) {
            System.out.printf("Grade: %d cow(s)\n", cows);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s)\n", bulls, cows);
        }
        return 0;
    }
}