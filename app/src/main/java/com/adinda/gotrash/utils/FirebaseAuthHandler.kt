package com.adinda.gotrash.utils

object FirebaseAuthExceptionHandler {
    fun getErrorMessage(exceptionMessage: String): String {
        return when {
            exceptionMessage.contains("FirebaseAuthInvalidCredentialsException") -> "Incorrect email or password."
            exceptionMessage.contains("FirebaseAuthUserCollisionException") -> "This email is already in use by another account."
            exceptionMessage.contains("FirebaseAuthWeakPasswordException") -> "The password is too weak."
            exceptionMessage.contains("FirebaseAuthInvalidUserException") -> "User not found."
            exceptionMessage.contains("FirebaseAuthRecentLoginRequiredException") -> "Recent login required."
            exceptionMessage.contains("FirebaseAuthEmailException") -> "Invalid email."
            exceptionMessage.contains("FirebaseAuthActionCodeException") -> "Invalid action code."
            exceptionMessage.contains("FirebaseAuthMultiFactorException") -> "Multi-factor authentication required."
            else -> "An error occurred, please try again."
        }
    }
}
