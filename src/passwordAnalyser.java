import java.util.*;

public class passwordAnalyser {
    public static boolean hasSequentialPatterns(String password) {
        int accending = 1;
        int decending = 1;

        for (int i = 1; i < password.length(); i++) {
            char curr = password.charAt(i);
            char prev = password.charAt(i - 1);
            if (curr - prev == 1) {
                accending++;
                decending = 1;
            } else if (prev - curr == -1) {
                accending = 1;
                decending++;
            } else {
                accending = 1;
                decending = 1;
            }

            if (accending >= 4 || decending >= 4) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasKeyboardPatterns(String password) {
        String[] patterns = {
                "qwerty",
                "asdfgh",
                "zxcvbn",
                "qwer",
                "asdf",
                "zxcv"
        };

        String newPass = password.toLowerCase();

        for (String pattern : patterns) {
            if (newPass.contains(pattern)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasCommonPasswords(String password) {
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

        String newPass = password.toLowerCase();

        for(String cPass : commonPasswords) {
            if(newPass.equals(cPass)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // Taking Input Using Scanner

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the password : ");
        String pass = sc.nextLine();

        // Excessive Repetions
        HashMap<Character, Integer> freq = new HashMap<>();

        for (char ch : pass.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        boolean excessiveRepetions = false;
        for (int val : freq.values()) {
            if (val >= 3) {
                excessiveRepetions = true;
                System.out.println("Excessive Repetitions -> " + excessiveRepetions + "for value : " + val);
                break;
            }
        }

        System.out.println(hasSequentialPatterns(pass));
        System.out.println(hasKeyboardPatterns(pass));
        System.out.println(hasCommonPasswords(pass));

        // Checking Conditions

        boolean uppercase = false;
        boolean lowercase = false;
        boolean digit = false;
        boolean specialChar = false;
        boolean whitespace = false;

        for (int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);

            if (Character.isUpperCase(ch)) {
                uppercase = true;
            }
            if (Character.isLowerCase(ch)) {
                lowercase = true;
            }
            if (Character.isDigit(ch)) {
                digit = true;
            }
            if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)) {
                specialChar = true;
            }
            if (Character.isWhitespace(ch)) {
                whitespace = true;
            }
        }

        // Scoring System

        int score = 0;

        if (pass.length() >= 8)
            score++;
        if (pass.length() >= 12)
            score++;
        if (uppercase)
            score++;
        if (lowercase)
            score++;
        if (specialChar)
            score++;
        if (digit)
            score++;

        System.out.print("Password Strenght : ");
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