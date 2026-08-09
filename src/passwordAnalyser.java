import java.util.*;
import java.util.HashMap;

public class passwordAnalyser {

    public static void main(String[] args) {
        // Taking Input Using Scanner

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the password : ");
        String pass = sc.nextLine();

        // Excessive Repetions
        HashMap<Character, Integer> freq = new HashMap<>();

        for(char ch : pass.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        boolean excessiveRepetions = false;
        for(int val : freq.values()) {
            if(val >= 3) {
                excessiveRepetions = true;
                System.out.println("Excessive Repetitions -> " + excessiveRepetions);
                break;
            }
        }

        // Checking Conditions

        boolean uppercase = false;
        boolean lowercase = false;
        boolean digit = false;
        boolean specialChar = false;
        boolean whitespace = false;

        for(int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);

            if(Character.isUpperCase(ch)){
                uppercase=true;
            }
            if(Character.isLowerCase(ch)){
                lowercase=true;
            }
            if(Character.isDigit(ch)){
                digit=true;
            }
            if(!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch)){
                specialChar=true;
            }
            if(Character.isWhitespace(ch)){
                whitespace=true;
            }
        }

        // Scoring System

        int score=0;

        if(pass.length() >= 8) score++;
        if(pass.length() >= 12) score++;
        if(uppercase) score++;
        if(lowercase) score++;
        if(specialChar) score++;
        if(digit) score++;


        System.out.print("Password Strenght : ");
        if(score <= 2) {
            System.out.println("Weak");
        }else if(score <= 4) {
            System.out.println("Medium");
        }else if(score == 5) {
            System.out.println("Strong");
        }else if(score == 6) {
            System.out.println("Very Strong");
        }

        sc.close();
    }
}