package pl.tablehub.mobile.fragments.signup.composables

import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import pl.tablehub.mobile.R

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
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
    private fun getStr(id: Int, context: Context): String {
        return context.getString(id)
    }
    private fun validateUsername(username: String, context: Context): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(false, getStr(R.string.error_password_empty, context))
            username.length < 3 -> ValidationResult(false, getStr(R.string.error_username_too_short, context))
            username.length > 20 -> ValidationResult(false, getStr(R.string.error_username_too_long, context))
            !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> ValidationResult(false, getStr(R.string.error_username_invalid_characters, context))
            else -> ValidationResult(true)
        }
    }

    private fun validateEmail(email: String, context: Context): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, getStr(R.string.error_email_empty, context))
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, getStr(R.string.error_email_invalid, context))
            else -> ValidationResult(true)
        }
    }

    private fun validatePassword(password: String, context: Context): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, getStr(R.string.error_password_empty, context))
            else -> ValidationResult(true)
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String, context: Context): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult(false, getStr(R.string.error_confirm_password_empty, context))
            password != confirmPassword -> ValidationResult(false, getStr(R.string.error_passwords_dont_match, context))
            else -> ValidationResult(true)
        }
    }

    fun validateSignUpForm(
        username: String,
        email: String,
        password: String,
        confirmPassword: String, context: Context
    ): SignUpValidationState {
        return SignUpValidationState(
            username = validateUsername(username, context),
            email = validateEmail(email, context),
            password = validatePassword(password, context),
            confirmPassword = validateConfirmPassword(password, confirmPassword, context)
        )
    }
}