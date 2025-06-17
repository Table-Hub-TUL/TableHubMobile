package pl.tablehub.mobile.fragments.signup.composables

import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import pl.tablehub.mobile.R

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: Int? = null
)

data class SignUpValidationState(
    val username: ValidationResult = ValidationResult(true),
    val email: ValidationResult = ValidationResult(true),
    val password: ValidationResult = ValidationResult(true),
    val confirmPassword: ValidationResult = ValidationResult(true)
) {
    val isAllValid: Boolean
        get() = username.isValid && email.isValid && password.isValid && confirmPassword.isValid
}

object SignUpValidator {
    private fun validateUsername(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(false, R.string.error_password_empty)
            username.length < 3 -> ValidationResult(false, R.string.error_username_too_short)
            username.length > 20 -> ValidationResult(false, R.string.error_username_too_long)
            !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> ValidationResult(false, R.string.error_username_invalid_characters)
            else -> ValidationResult(true)
        }
    }

    private fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, R.string.error_email_empty)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, R.string.error_email_invalid)
            else -> ValidationResult(true)
        }
    }

    private fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, R.string.error_password_empty)
            else -> ValidationResult(true)
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(false, R.string.error_confirm_password_empty)
            password != confirmPassword -> ValidationResult(false, R.string.error_passwords_dont_match)
            else -> ValidationResult(true)
        }
    }

    fun validateSignUpForm(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): SignUpValidationState {
        return SignUpValidationState(
            username = validateUsername(username),
            email = validateEmail(email),
            password = validatePassword(password),
            confirmPassword = validateConfirmPassword(password, confirmPassword)
        )
    }
}