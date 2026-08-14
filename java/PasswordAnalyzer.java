import java.util.*;

public class PasswordAnalyzer {
    public static boolean hasSequentialPattern(String password) {
        int ascending = 1;
        int descending = 1;

        for (int i = 1; i < password.length(); i++) {
            char curr = password.charAt(i);
            char prev = password.charAt(i - 1);
            if (curr - prev == 1) {
                ascending++;
                descending = 1;
            } else if (curr - prev  == -1) {
                ascending = 1;
                descending++;
            } else {
                ascending = 1;
                descending = 1;
            }

            if (ascending >= 4 || descending >= 4) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasKeyboardPattern(String password) {
        String[] patterns = {
                "qwerty",
                "asdfgh",
                "zxcvbn",
                "qwer",
                "asdf",
                "zxcv"
        };

        String lowercasePassword = password.toLowerCase();

        for (String pattern : patterns) {
            if (lowercasePassword.contains(pattern)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCommonPassword(String password) {
        String[] commonPasswords = {
                "password",
                "123456",
                "123456789",
                "12345678",
                "qwerty",
                "abc123",
                "password123",
                "admin",
                "letmein",
                "welcome",
                "iloveyou",
                "monkey",
                "dragon",
                "football"
        };

        String lowercasePassword = password.toLowerCase();

        for(String commonPassword : commonPasswords) {
            if(lowercasePassword.equals(commonPassword)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // Taking Input Using Scanner

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the password : ");
        String password = sc.nextLine();

        // Excessive Repetitions
        HashMap<Character, Integer> charFrequency = new HashMap<>();

        for (char ch : password.toCharArray()) {
            charFrequency.put(ch, charFrequency.getOrDefault(ch, 0) + 1);
        }

        boolean excessiveRepetitions = false;
        for (int val : charFrequency.values()) {
            if (val >= 3) {
                excessiveRepetitions = true;
                System.out.println("Excessive Repetitions -> " + excessiveRepetitions + " for value : " + val);
                break;
            }
        }

        System.out.println(hasSequentialPattern(password));
        System.out.println(hasKeyboardPattern(password));
        System.out.println(isCommonPassword(password));

        // Checking Conditions

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        boolean hasWhitespace = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            }
            if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            }
            if (Character.isDigit(ch)) {
                hasDigit = true;
            }
            if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)) {
                hasSpecialCharacter = true;
            }
            if (Character.isWhitespace(ch)) {
                hasWhitespace = true;
            }
        }

        // Scoring System

        int score = 0;

        if (password.length() >= 8)
            score++;
        if (password.length() >= 12)
            score++;
        if (hasUppercase)
            score++;
        if (hasLowercase)
            score++;
        if (hasSpecialCharacter)
            score++;
        if (hasDigit)
            score++;

        System.out.print("Password Strength : ");
        if (score <= 2) {
            System.out.println("Weak");
        } else if (score <= 4) {
            System.out.println("Medium");
        } else if (score == 5) {
            System.out.println("Strong");
        } else if (score == 6) {
            System.out.println("Very Strong");
        }

        sc.close();
    }
}