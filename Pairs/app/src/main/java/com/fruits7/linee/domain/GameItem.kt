package com.fruits7.linee.domain

data class GameItem(
    var imageId: Int,
    var isOpened: Boolean,
    val letter: String,
    val sameId: Int
)