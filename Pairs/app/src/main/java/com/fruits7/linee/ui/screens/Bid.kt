package com.fruits7.linee.ui.screens

import android.content.Context.MODE_PRIVATE
import android.view.MotionEvent
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruits7.linee.R
import com.fruits7.linee.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BidScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val sliderValue = remember {
            mutableStateOf(100L)
        }

        val stickColor = TextColor
        val (fixLayout, topLayout, difficulty, logo, difficultyLayout, bidLayout, bottomLayout) = createRefs()
        val sharedPreferences =
            LocalContext.current.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val swipeableState = rememberSwipeableState(1)

        var isBif by rememberSaveable { mutableStateOf(false) }
        val animationSpec =
            tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
        val logoScale by animateFloatAsState(
            targetValue = if (isBif) 1.1f else 0.9f,
            animationSpec = animationSpec,
            finishedListener = {
                isBif = !isBif
            }
        )

        LaunchedEffect(key1 = true) {
            isBif = !isBif
        }

        Box(modifier = Modifier.constrainAs(difficulty) {
            bottom.linkTo(difficultyLayout.top, margin = 6.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            CustomText(text = "Select Difficulty", size = 30.sp)
        }

        Image(
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(topLayout.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(difficultyLayout.top)
                }
                .scale(logoScale)
                .size(width = 360.dp, height = 120.dp),
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Logo"
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
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
                    text = "Balance",
                    size = 20.sp
                )
            }

            Box {
                CustomText(
                    modifier = Modifier,
                    text = "Last Win",
                    size = 20.sp
                )
            }
        }

        Row(modifier = Modifier
            .padding(horizontal = 12.dp)
            .constrainAs(topLayout) {
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
                    text = sharedPreferences.getLong("BALANCE", 5000).toString(),
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
                    text = sharedPreferences.getLong("LAST_WIN", 0).toString(),
                    size = 20.sp
                )
            }
        }

        Row(modifier = Modifier
            .constrainAs(bidLayout) {
                start.linkTo(difficultyLayout.start)
                end.linkTo(difficultyLayout.end)
                top.linkTo(difficultyLayout.bottom)
                width = Dimension.fillToConstraints
            }
            .padding(12.dp)
            .height(70.dp)
            .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF673400),
                        Color(0xFFA4550A)
                    )
                ), RoundedCornerShape(6.dp)
            ), verticalAlignment = Alignment.CenterVertically)
        {
            val balance = sharedPreferences.getLong("BALANCE", 5000)
            Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                CustomText(text = sliderValue.value.toString(), size = 20.sp)
            }

            Slider(
                enabled = balance > 100,
                valueRange = (0f..balance.toFloat()),
                modifier = Modifier.padding(horizontal = 6.dp),
                value = if (balance > 100) sliderValue.value.toFloat() else 0f,
                onValueChange = { sliderValue.value = it.toLong() },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color(0xddFFFFFF),
                    inactiveTrackColor = Color(0x22FFFFFF)
                )
            )
        }

        Row(modifier = Modifier
            .constrainAs(difficultyLayout) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            .padding(horizontal = 12.dp)
            .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF673400),
                        Color(0xFFA4550A)
                    )
                ), RoundedCornerShape(6.dp)
            )
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                CustomText(
                    size = 18.sp,
                    weight = FontWeight(800),
                    text = "${
                        when (swipeableState.currentValue) {
                            0 -> "180"
                            1 -> "150"
                            else -> "120"
                        }
                    }sec"
                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .width(180.dp)

            ) {
                val sizePx = with(LocalDensity.current) { 180.dp.toPx() }
                val (mainStick, stick1, stick2, stick3, low, medium, hard) = createRefs()
                val anchors =
                    mapOf(-(sizePx / 2) to 0, 0f to 1, (sizePx / 2) to 2)

                Box(modifier = Modifier
                    .constrainAs(stick1) {
                        bottom.linkTo(mainStick.bottom)
                        top.linkTo(mainStick.top)
                        start.linkTo(parent.start)
                    }
                    .size(width = 2.dp, height = 10.dp)
                    .background(stickColor))

                Box(modifier = Modifier
                    .constrainAs(stick2) {
                        bottom.linkTo(mainStick.bottom)
                        top.linkTo(mainStick.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(width = 2.dp, height = 10.dp)
                    .background(stickColor))

                Box(modifier = Modifier
                    .constrainAs(stick3) {
                        bottom.linkTo(mainStick.bottom)
                        top.linkTo(mainStick.top)
                        end.linkTo(parent.end)
                    }
                    .size(width = 2.dp, height = 10.dp)
                    .background(stickColor))
                Box(modifier = Modifier
                    .constrainAs(low) {
                        bottom.linkTo(stick1.top)
                        start.linkTo(stick1.start)
                        end.linkTo(stick1.end)
                    }
                    .padding(bottom = 12.dp)) {
                    CustomText(text = "Low ")
                }

                Box(modifier = Modifier
                    .constrainAs(medium) {
                        bottom.linkTo(stick2.top)
                        start.linkTo(stick2.start)
                        end.linkTo(stick2.end)
                    }
                    .padding(bottom = 12.dp)) {
                    CustomText(text = "Medium ")
                }

                Box(modifier = Modifier
                    .constrainAs(hard) {
                        bottom.linkTo(stick3.top)
                        start.linkTo(stick3.start)
                        end.linkTo(stick3.end)
                    }
                    .padding(bottom = 12.dp)) {
                    CustomText(text = "Hard ")
                }
                Box(modifier = Modifier
                    .constrainAs(mainStick) {
                        bottom.linkTo(parent.bottom)
                    }
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        orientation = Orientation.Horizontal
                    )
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(stickColor)
                    )
                    Box(modifier = Modifier
                        .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                        .size(12.dp)
                        .background(Color(0xFFFFD700), RoundedCornerShape(6.dp)))
                }
            }
        }
        Row(modifier = Modifier
            .padding(12.dp)
            .constrainAs(bottomLayout) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth()) {
            val isSmallBtnBack = remember { mutableStateOf(true) }
            val sizeBtnBack =
                animateFloatAsState(targetValue = if (isSmallBtnBack.value) 1f else 0.9f)

            val isSmallBtnConfirm = remember { mutableStateOf(true) }
            val sizeBtnConfirm =
                animateFloatAsState(targetValue = if (isSmallBtnConfirm.value) 1f else 0.9f)
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                isSmallBtnBack.value = !isSmallBtnBack.value
                            }

                            MotionEvent.ACTION_UP -> {
                                isSmallBtnBack.value = !isSmallBtnBack.value
                                navController.popBackStack("main", false)
                            }

                            else -> false
                        }
                        true
                    }
                    .scale(sizeBtnBack.value)
                    .height(60.dp)
                    .weight(1f)
                    .paint(
                        painter = painterResource(id = R.drawable.bg_game_button),
                        contentScale = ContentScale.FillBounds
                    ), contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = stringResource(id = R.string.back_to_menu),
                    weight = FontWeight(800),
                    size = 22.sp
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                isSmallBtnConfirm.value = !isSmallBtnConfirm.value
                            }

                            MotionEvent.ACTION_UP -> {
                                CoroutineScope(Dispatchers.Main).launch {
                                    isSmallBtnConfirm.value = !isSmallBtnConfirm.value
                                    val balance = sharedPreferences.getLong("BALANCE", 5000)
                                    val bid = sliderValue.value
                                    if (sliderValue.value != 0L && balance - bid > 0
                                    ) {
                                        sharedPreferences
                                            .edit()
                                            .putLong("BALANCE", balance - bid)
                                            .apply()
                                        navController.navigate(
                                            "game/${bid}/${
                                                when (swipeableState.currentValue) {
                                                    0 -> 180
                                                    1 -> 150
                                                    else -> 150
                                                }
                                            }"
                                        )
                                    }
                                }
                            }

                            else -> false
                        }
                        true
                    }
                    .scale(sizeBtnConfirm.value)
                    .height(60.dp)
                    .weight(1f)
                    .paint(
                        painter = painterResource(id = R.drawable.bg_game_button),
                        contentScale = ContentScale.FillBounds
                    ), contentAlignment = Alignment.Center
            ) {
                CustomText(
                    text = stringResource(id = R.string.confirm),
                    weight = FontWeight(800),
                    size = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BidScreenPreview() {
    BidScreen(navController = rememberNavController())
}