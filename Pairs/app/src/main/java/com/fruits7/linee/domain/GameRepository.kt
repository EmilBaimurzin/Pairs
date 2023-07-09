package com.fruits7.linee.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameRepository {
    suspend fun generateItems(): List<GameItem> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                val listToReturn = mutableListOf<GameItem>()
                repeat(30) {
                    listToReturn.add(
                        GameItem(
                            imageId = 0, isOpened = false, letter = "", 0
                        )
                    )
                }
                continuation.resume(createPairs(listToReturn))
            }
        }
    }

    private suspend fun createPairs(list: MutableList<GameItem>): MutableList<GameItem> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                val indexList = mutableListOf<Int>()
                repeat(30) {
                    indexList.add(it)
                }
                val pairs = mutableListOf<GameItem>(
                    GameItem(imageId = 1, isOpened = false, letter = "A", 1),
                    GameItem(imageId = 1, isOpened = false, letter = "B", 2),
                    GameItem(imageId = 1, isOpened = false, letter = "C", 3),
                    GameItem(imageId = 2, isOpened = false, letter = "A", 4),
                    GameItem(imageId = 2, isOpened = false, letter = "B", 5),
                    GameItem(imageId = 2, isOpened = false, letter = "C", 6),
                    GameItem(imageId = 3, isOpened = false, letter = "A", 7),
                    GameItem(imageId = 3, isOpened = false, letter = "B", 8),
                    GameItem(imageId = 3, isOpened = false, letter = "C", 9),
                    GameItem(imageId = 4, isOpened = false, letter = "A", 10),
                    GameItem(imageId = 4, isOpened = false, letter = "B", 11),
                    GameItem(imageId = 4, isOpened = false, letter = "C", 12),
                    GameItem(imageId = 5, isOpened = false, letter = "A", 13),
                    GameItem(imageId = 5, isOpened = false, letter = "B", 14),
                    GameItem(imageId = 5, isOpened = false, letter = "C", 15)
                )

                pairs.forEach {
                    val randomIndex = indexList.random()
                    list[randomIndex] = it
                    indexList.remove(randomIndex)

                    val randomIndex2 = indexList.random()
                    list[randomIndex2] = it.copy()
                    indexList.remove(randomIndex2)
                }
                continuation.resume(list)
            }
        }
    }
}