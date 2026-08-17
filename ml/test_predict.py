import unittest

from predict import (
    extract_features,
    has_keyboard_pattern,
    has_sequential_pattern,
    predict_strength,
)


class PredictTests(unittest.TestCase):
    def test_empty_password_is_rejected(self):
        with self.assertRaisesRegex(ValueError, "cannot be empty"):
            extract_features("")

    def test_patterns_are_detected_case_insensitively(self):
        self.assertEqual(has_sequential_pattern("xxabcdyy"), 1)
        self.assertEqual(has_sequential_pattern("xx4321yy"), 1)
        self.assertEqual(has_keyboard_pattern("xxQWERTYyy"), 1)

    def test_feature_values(self):
        features = extract_features("Aa1!Aa")
        self.assertEqual(features["length"], 6)
        self.assertEqual(features["uppercase_count"], 2)
        self.assertEqual(features["lowercase_count"], 2)
        self.assertEqual(features["digit_count"], 1)
        self.assertEqual(features["special_count"], 1)
        self.assertEqual(features["unique_char_count"], 4)
        self.assertEqual(features["max_char_frequency"], 2)

    def test_model_returns_known_label(self):
        self.assertIn(predict_strength("Example123!"), {"Weak", "Medium", "Strong"})


if __name__ == "__main__":
    unittest.main()
