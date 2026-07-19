# Password Analyzer

## Overview
Password Analyzer is a Java-based application that evaluates the strength of a password based on common security standards. It helps users identify weak passwords and provides suggestions for creating stronger, more secure passwords.

## Features
- Checks password length
- Detects uppercase and lowercase letters
- Detects numeric digits
- Detects special characters
- Estimates password strength (Weak, Medium, Strong)
- Provides recommendations to improve password security

## Technologies Used
- Java
- VS Code
- Java Collections
- Regular Expressions (Regex)

## Project Structure
```
PasswordAnalyzer/
│── src/
│   └── PasswordAnalyzer.java
│── README.md
```

## How to Run
1. Clone the repository.
2. Open the project in your Java IDE.
3. Compile the Java file:
   ```
   javac PasswordAnalyzer.java
   ```
4. Run the program:
   ```
   java PasswordAnalyzer
   ```

## Password Evaluation Criteria
| Criteria | Points |
|----------|--------|
| Length ≥ 8 | ✔ |
| Contains uppercase letter | ✔ |
| Contains lowercase letter | ✔ |
| Contains number | ✔ |
| Contains special character | ✔ |

## Example

Input:
```
Password@123
```

Output:
```
Password Strength: Strong
Suggestions:
- None
```

Input:
```
abc123
```

Output:
```
Password Strength: Weak
Suggestions:
- Increase password length
- Add uppercase letters
- Add special characters
```

## Limitations
- Does not check passwords against leaked password databases.
- Does not estimate password cracking time.
- Intended for educational purposes.

## Future Improvements
- GUI using Java Swing or JavaFX
- Password entropy calculation
- Detection of common dictionary words
- Integration with Have I Been Pwned API
- Password generation feature

## Author
Arjun
