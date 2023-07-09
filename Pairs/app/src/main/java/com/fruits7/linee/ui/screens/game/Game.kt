package com.fruits7.linee.ui.screens.game

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fruits7.linee.R
import com.fruits7.linee.domain.GameItem
import com.fruits7.linee.ui.screens.CustomText
import com.fruits7.linee.ui.screens.MainActivity
import kotlinx.coroutines.delay

@Composable
fun GameScreen(bid: Long, timer: Int, gameViewModel: GameViewModel, navController: NavController, activity: MainActivity) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val items = gameViewModel.items.observeAsState()
        val pairs = gameViewModel.pairs.observeAsState()
        val time = gameViewModel.timer.observeAsState()
        val sharedPreferences =
            LocalContext.current.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.clock))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        if (time.value == -1) {
            gameViewModel.startTimer(timer)
        }

        if (time.value == 0) {
            if (sharedPreferences.getBoolean("VOLUME", true)) {
                MediaPlayer.create(LocalContext.current, R.raw.sound_lose).start()
            }
            navController.navigate("lose")
        }

        if (pairs.value!! / 2  == 15) {
            if (sharedPreferences.getBoolean("VOLUME", true)) {
                MediaPlayer.create(LocalContext.current, R.raw.sound_win).start()
            }
           navController.navigate("win/${
                when (timer) {
                    120 -> (bid * 2).toLong()
                    150 -> (bid * 2.5).toLong()
                    else -> (bid * 3).toLong()
                }
            }/false")
        }

        var _stack = rememberSaveable {
            mutableStateOf(listOf<GameItem>())
        }
        if (items.value == null || items.value?.isEmpty() == true) {
            gameViewModel.generateItems()
        }
        val (list, topContainer, timer, fixLayout) = createRefs()

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
                .padding(top = 12.dp)
                .constrainAs(fixLayout) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
        ) {
            Box {
                CustomText(
                    modifier = Modifier,
                    text = "Time Left",
                    size = 20.sp
                )
            }

            Box {
                CustomText(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "Pairs Found",
                    size = 20.sp
                )
            }
        }

        Row(modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 6.dp)
            .constrainAs(topContainer) {
                top.linkTo(fixLayout.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .padding(end = 6.dp)
                    .weight(1f)
                    .paint(
                        painter = painterResource(id = R.drawable.bg_text_basic),
                        contentScale = ContentScale.FillBounds
                    ), contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = "${time.value}",
                    size = 20.sp
                )
            }
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 6.dp)
                    .weight(1f)
                    .paint(
                        painter = painterResource(id = R.drawable.bg_text_basic),
                        contentScale = ContentScale.FillBounds
                    ), contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = "${pairs.value!! / 2}",
                    size = 20.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(timer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topContainer.bottom)
                    bottom.linkTo(list.top)
                }

        ) {
            LottieAnimation(
                modifier = Modifier.size(180.dp),
                composition = composition,
                progress = { progress },
            )
        }

        LazyVerticalGrid(
            modifier = Modifier
                .constrainAs(list) {
                    bottom.linkTo(parent.bottom, margin = 60.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .width(350.dp),
            columns = GridCells.Fixed(5)
        ) {
            items(items.value?.size ?: 0) { index ->
                val item = items.value?.get(index)!!

                var isFlipped by rememberSaveable { mutableStateOf(false) }
                var currentRotation by rememberSaveable { mutableStateOf(0f) }
                val animationSpec =
                    tween<Float>(500, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
                val rotation by animateFloatAsState(
                    targetValue = if (isFlipped) 180f else 0f,
                    animationSpec = animationSpec,
                    finishedListener = {
                        currentRotation = it
                    }
                )



                LaunchedEffect(_stack.value) {
                    val stack = gameViewModel.stack.value
                    val itemId1 = stack?.getOrNull(0)?.sameId
                    val itemId2 = stack?.getOrNull(1)?.sameId
                    if (itemId1 == null || itemId2 == null) return@LaunchedEffect
                    val sameIds = itemId1 == itemId2
                    val sameItems = itemId1 == item.sameId || itemId2 == item.sameId
                    if (stack.size == 2 && sameIds && sameItems && item.isOpened) {
                        _stack.value = mutableListOf()
                        gameViewModel.addPair()
                        gameViewModel.clearStack()
                    }
                    if (stack.size == 2 && !sameIds && sameItems && item.isOpened) {
                        Log.e("effect", "process $index")
                        delay(500)
                        isFlipped = !isFlipped
                        gameViewModel.clearStack()
                        _stack.value = emptyList()
                        gameViewModel.closeItem(index)
                    }
                }

                ConstraintLayout(
                    modifier = Modifier
                        .size(70.dp)
                        .padding(2.dp)
                        .graphicsLayer {
                            rotationY = rotation
                            cameraDistance = 8 * density
                        }
                        .clickable(onClick = {
                            if (!item.isOpened && gameViewModel.stack.value!!.size != 2) {
                                if (sharedPreferences.getBoolean("VOLUME", true)) {
                                    MediaPlayer.create(activity.application, R.raw.sound_flip).start()
                                }
                                isFlipped = !isFlipped
                                gameViewModel.openCard(index)
                                val newList = _stack.value.toMutableList()
                                newList.add(item)
                                _stack.value = newList
                            }
                        })
                        .paint(
                            painter =
                            if (rotation > 90f) {
                                painterResource(id = R.drawable.bg_slot_opened)
                            } else painterResource(id = R.drawable.bg_slot_closed),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    val (image, letter) = createRefs()
                    if (rotation > 90f) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp)
                                .graphicsLayer {
                                    if (rotation > 90) {
                                        rotationY = 180f
                                    }
                                }
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                },
                            painter = painterResource(
                                id = when (item.imageId) {
                                    1 -> R.drawable.item_1
                                    2 -> R.drawable.item_2
                                    3 -> R.drawable.item_3
                                    4 -> R.drawable.item_4
                                    else -> R.drawable.item_5
                                }
                            ), contentDescription = "Card Image"
                        )

                        Box(modifier = Modifier
                            .constrainAs(letter) {
                                top.linkTo(parent.top, margin = 3.dp)
                                if (rotation > 90) {
                                    start.linkTo(parent.start, margin = 3.dp)
                                } else {
                                    end.linkTo(parent.end, margin = 3.dp)
                                }
                            }
                            .graphicsLayer {
                                if (rotation > 90) {
                                    rotationY = 180f
                                }
                            }) {
                            CustomText(text = item.letter, weight = FontWeight(800), size = 20.sp)
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(bid = 500, gameViewModel = GameViewModel(), timer = 0, navController = rememberNavController(), activity = MainActivity())
}