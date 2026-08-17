import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

        String lowercasePassword = password.toLowerCase(Locale.ROOT);

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

        String lowercasePassword = password.toLowerCase(Locale.ROOT);

        for(String commonPassword : commonPasswords) {
            if(lowercasePassword.equals(commonPassword)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasExcessiveRepetitions(String password) {
        HashMap<Character, Integer> frequencies = new HashMap<>();
        for (char character : password.toCharArray()) {
            int frequency = frequencies.getOrDefault(character, 0) + 1;
            if (frequency >= 3) {
                return true;
            }
            frequencies.put(character, frequency);
        }
        return false;
    }

    private static Path findProjectRoot() throws IOException {
        String configuredRoot = System.getenv("PASSWORD_ANALYZER_ROOT");
        if (configuredRoot != null && !configuredRoot.isBlank()) {
            Path root = Paths.get(configuredRoot).toAbsolutePath().normalize();
            if (Files.isRegularFile(root.resolve("ml/predict.py"))) {
                return root;
            }
            throw new IOException("PASSWORD_ANALYZER_ROOT does not contain ml/predict.py");
        }

        Path directory = Paths.get("").toAbsolutePath().normalize();
        while (directory != null) {
            if (Files.isRegularFile(directory.resolve("ml/predict.py"))) {
                return directory;
            }
            directory = directory.getParent();
        }
        throw new IOException(
                "Project root not found. Run from the project directory or set PASSWORD_ANALYZER_ROOT."
        );
    }

    private static Path findPython(Path projectRoot) throws IOException {
        Path[] candidates = {
                projectRoot.resolve(".venv/bin/python"),
                projectRoot.resolve(".venv/Scripts/python.exe")
        };
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
        }
        throw new IOException("Virtual-environment Python was not found. Create .venv and install requirements.");
    }

    public static String getMLPrediction(String password) {
        try {
            Path projectRoot = findProjectRoot();
            Path python = findPython(projectRoot);
            Path script = projectRoot.resolve("ml/predict.py");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    python.toString(), script.toString()
            );
            processBuilder.directory(projectRoot.toFile());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(password);
                writer.newLine();
            }

            if (!process.waitFor(30, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                return "Prediction Error: Python process timed out";
            }

            String prediction = null;
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!output.isEmpty()) {
                        output.append(System.lineSeparator());
                    }
                    output.append(line);
                    if (line.startsWith("ML Strength:")) {
                        prediction = line.substring("ML Strength:".length()).trim();
                    }
                }
            }

            if (process.exitValue() != 0) {
                return "Prediction Error: " + (output.isEmpty() ? "Python exited unsuccessfully" : output);
            }
            if (prediction == null || prediction.isBlank()) {
                return "Prediction Error: Python returned no prediction";
            }
            return prediction;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return "Prediction Error: interrupted while waiting for Python";
        } catch (IOException exception) {
            return "Prediction Error: " + exception.getMessage();
        }
    }

    public static void main(String[] args) {
        // Taking Input Using Scanner

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the password : ");
        String password = sc.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            sc.close();
            return;
        }

        boolean excessiveRepetitions = hasExcessiveRepetitions(password);
        boolean sequentialPattern = hasSequentialPattern(password);
        boolean keyboardPattern = hasKeyboardPattern(password);
        boolean commonPassword = isCommonPassword(password);

        System.out.println("Sequential pattern: " + sequentialPattern);
        System.out.println("Keyboard pattern: " + keyboardPattern);
        System.out.println("Common password: " + commonPassword);
        System.out.println("Excessive repetitions: " + excessiveRepetitions);

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
            if (!Character.isLetterOrDigit(ch)) {
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

        if (sequentialPattern)
            score--;
        if (keyboardPattern)
            score--;
        if (excessiveRepetitions)
            score--;
        if (hasWhitespace)
            score--;
        if (commonPassword)
            score = 0;

        score = Math.max(0, score);

        System.out.print("Password Strength : ");
        if (score <= 2) {
            System.out.println("Weak");
        } else if (score <= 4) {
            System.out.println("Medium");
        } else if (score == 5) {
            System.out.println("Strong");
        } else {
            System.out.println("Very Strong");
        }

        String mlStrength = getMLPrediction(password);
        System.out.println("ML Strength : " + mlStrength);

        sc.close();
    }
}
