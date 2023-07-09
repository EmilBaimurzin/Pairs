package com.fruits7.linee.ui.screens

import android.view.MotionEvent
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (btnGame, btnSettings, btnRules, imgLogo, character) = createRefs()
        val isSmallBtnGame = remember { mutableStateOf(true) }
        val isSmallBtnSettings = remember { mutableStateOf(true) }
        val isSmallBtnRules = remember { mutableStateOf(true) }
        val sizeBtnGame = animateFloatAsState(targetValue = if (isSmallBtnGame.value) 1f else 0.9f)
        val sizeBtnRules =
            animateFloatAsState(targetValue = if (isSmallBtnRules.value) 1f else 0.9f)
        val sizeBtnSettings =
            animateFloatAsState(targetValue = if (isSmallBtnSettings.value) 1f else 0.9f)

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

        Image(
            modifier = Modifier
                .constrainAs(imgLogo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(btnGame.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 360.dp, height = 120.dp)
                .scale(logoScale),
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Logo")

        Box(
            modifier = Modifier
                .constrainAs(btnGame) {
                    bottom.linkTo(btnSettings.top, margin = 6.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isSmallBtnGame.value = !isSmallBtnGame.value
                        }

                        MotionEvent.ACTION_UP -> {
                            isSmallBtnGame.value = !isSmallBtnGame.value
                            navController.navigate("bid")
                        }

                        else -> false
                    }
                    true
                }
                .scale(sizeBtnGame.value)
                .size(width = 280.dp, height = 80.dp)
                .paint(
                    painter = painterResource(id = R.drawable.bg_game_button),
                    contentScale = ContentScale.FillBounds
                ), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(id = R.string.start),
                weight = FontWeight(800),
                size = 30.sp
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(btnSettings) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isSmallBtnSettings.value = !isSmallBtnSettings.value
                        }

                        MotionEvent.ACTION_UP -> {
                            isSmallBtnSettings.value = !isSmallBtnSettings.value
                            navController.navigate("settings")
                        }

                        else -> false
                    }
                    true
                }
                .scale(sizeBtnSettings.value)
                .size(width = 280.dp, height = 80.dp)
                .paint(
                    painter = painterResource(id = R.drawable.bg_game_button),
                    contentScale = ContentScale.FillBounds
                ), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(id = R.string.settings),
                weight = FontWeight(800),
                size = 30.sp
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(btnRules) {
                    top.linkTo(btnSettings.bottom, margin = 6.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isSmallBtnRules.value = !isSmallBtnRules.value
                        }

                        MotionEvent.ACTION_UP -> {
                            isSmallBtnRules.value = !isSmallBtnRules.value
                            navController.navigate("rules")
                        }

                        else -> false
                    }
                    true
                }
                .scale(sizeBtnRules.value)
                .size(width = 280.dp, height = 80.dp)
                .paint(
                    painter = painterResource(id = R.drawable.bg_game_button),
                    contentScale = ContentScale.FillBounds
                ), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(id = R.string.game_rules),
                weight = FontWeight(800),
                size = 28.sp
            )
        }
        Image(
            modifier = Modifier
                .constrainAs(character) {
                    top.linkTo(btnRules.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(180.dp),
            painter = painterResource(id = R.drawable.img_character),
            contentDescription = "character")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}