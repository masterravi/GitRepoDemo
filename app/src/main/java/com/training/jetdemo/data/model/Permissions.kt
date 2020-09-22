package com.training.jetdemo.data.model

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)