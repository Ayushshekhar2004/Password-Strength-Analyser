# Password Strength Analyzer

Password Strength Analyzer combines a Java rule-based checker with an XGBoost model served by a small Python command-line program.

## Checks

- Length, uppercase and lowercase letters, digits, and special characters
- Sequential character patterns such as `abcd` and `4321`
- Keyboard patterns such as `qwerty` and `asdf`
- Common passwords
- Characters repeated three or more times
- Whitespace
- ML-based classification as Weak, Medium, or Strong

Detected patterns, repetitions, and whitespace reduce the rule-based score. A known common password is always classified as Weak by the rule-based checker.

## Project structure

```text
java/PasswordAnalyzer.java       Java application
ml/predict.py                    ML prediction entry point
ml/models/                       Trained model and encoders
ml/notebooks/password_ml.ipynb   Model-training notebook
requirement.txt                  Python dependencies
```

## Setup

From the repository root, create a virtual environment and install the Python dependencies:

```bash
python3 -m venv .venv
source .venv/bin/activate
python -m pip install -r requirement.txt
```

On Windows, activate the environment with:

```powershell
.venv\Scripts\Activate.ps1
```

## Run

Compile and run from the repository root:

```bash
javac -d build java/PasswordAnalyzer.java
java -cp build PasswordAnalyzer
```

The application locates `.venv` and `ml/predict.py` relative to the project root. If an IDE uses a working directory outside the repository, set `PASSWORD_ANALYZER_ROOT` to the repository's absolute path.

The Python predictor can also be run directly:

```bash
python ml/predict.py
```

## Test

```bash
python -m unittest discover -s ml -p "test_*.py"
```

## Limitations

- The common-password check uses a small built-in list rather than a breach database.
- The application does not estimate password cracking time.
- The model is intended for educational use and should not be treated as a security guarantee.

## Possible improvements

- Check passwords against the Have I Been Pwned API using its privacy-preserving range search
- Add password entropy/cracking-time estimates
- Add a Swing, JavaFX, or web interface
- Add a password generator

## Author

Ayush Shekhar Singh
