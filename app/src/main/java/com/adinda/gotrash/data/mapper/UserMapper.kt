package com.adinda.gotrash.data.mapper

import com.adinda.gotrash.data.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toUser() =
    this?.let {
        User(
            username = this.displayName.orEmpty(),
            email = this.email.orEmpty(),
        )
    }
