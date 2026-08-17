import os
import joblib
import pandas as pd
from collections import Counter

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

model = joblib.load(
    os.path.join(BASE_DIR, "models", "xgboost_password_model.pkl")
)

label_encoder = joblib.load(
    os.path.join(BASE_DIR, "models", "label_encoder.pkl")
)

feature_names = joblib.load(
    os.path.join(BASE_DIR, "models", "feature_names.pkl")
)


def has_sequential_pattern(password):
    ascending = 1
    descending = 1

    for i in range(1, len(password)):
        curr = ord(password[i])
        prev = ord(password[i - 1])

        if curr - prev == 1:
            ascending += 1
            descending = 1

        elif curr - prev == -1:
            descending += 1
            ascending = 1

        else:
            ascending = 1
            descending = 1

        if ascending >= 4 or descending >= 4:
            return 1

    return 0


def has_keyboard_pattern(password):
    patterns = [
        "qwerty",
        "asdfgh",
        "zxcvbn",
        "qwer",
        "asdf",
        "zxcv"
    ]

    lowercase_password = password.lower()

    for pattern in patterns:
        if pattern in lowercase_password:
            return 1

    return 0


def extract_features(password):
    if not password:
        raise ValueError("Password cannot be empty.")

    frequency = Counter(password)

    features = {
        "length": len(password),
        "uppercase_count": sum(1 for ch in password if ch.isupper()),
        "lowercase_count": sum(1 for ch in password if ch.islower()),
        "digit_count": sum(1 for ch in password if ch.isdigit()),
        "special_count": sum(1 for ch in password if not ch.isalnum()),
        "unique_char_count": len(set(password)),
        "unique_char_ratio": len(set(password)) / len(password),
        "max_char_frequency": max(frequency.values()),
        "has_sequential_pattern": has_sequential_pattern(password),
        "has_keyboard_pattern": has_keyboard_pattern(password)
    }

    return features


def predict_strength(password):
    features = extract_features(password)

    feature_df = pd.DataFrame(
        [features]
    )

    feature_df = feature_df[feature_names]

    prediction_encoded = model.predict(feature_df)

    prediction = label_encoder.inverse_transform(
        prediction_encoded
    )

    return prediction[0]


def main():
    password = input()

    if not password:
        print("ML Error: Password cannot be empty.")
        return 1

    print("ML Strength:", predict_strength(password))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
