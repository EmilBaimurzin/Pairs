package com.fruits7.linee.ui.screens

import android.view.MotionEvent
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.fruits7.linee.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoseScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, claim, lottery, text, character) = createRefs()

        val isSmallBtnClaim = remember { mutableStateOf(true) }
        val sizeBtnClaim =
            animateFloatAsState(targetValue = if (isSmallBtnClaim.value) 1f else 0.9f)

        val isSmallBtnTry = remember { mutableStateOf(true) }
        val sizeBtnTry = animateFloatAsState(targetValue = if (isSmallBtnTry.value) 1f else 0.9f)

        var isBif by rememberSaveable { mutableStateOf(false) }
        val animationSpec =
            tween<Float>(500, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
        val loseScale by animateFloatAsState(
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
                .constrainAs(character) {
                    top.linkTo(lottery.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .size(140.dp),
            painter = painterResource(id = R.drawable.img_character),
            contentDescription = "character")

        Image(
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(claim.top)
                }
                .padding(bottom = 40.dp)
                .size(width = 360.dp, height = 120.dp),
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Logo")

        Box(
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(logo.bottom)
                    bottom.linkTo(claim.top)
                }
                .scale(loseScale), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = "You Lost",
                size = 45.sp
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(claim) {
                    top.linkTo(parent.top, margin = 80.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isSmallBtnClaim.value = !isSmallBtnClaim.value
                        }

                        MotionEvent.ACTION_UP -> {
                            isSmallBtnClaim.value = !isSmallBtnClaim.value
                            navController.popBackStack(
                                route = "bid",
                                inclusive = false,
                                saveState = false
                            )
                        }

                        else -> false
                    }
                    true
                }
                .scale(sizeBtnClaim.value)
                .size(width = 280.dp, height = 80.dp)
                .paint(
                    painter = painterResource(id = R.drawable.bg_game_button),
                    contentScale = ContentScale.FillBounds
                ), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(id = R.string.try_again),
                weight = FontWeight(800),
                size = 30.sp
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(lottery) {
                    top.linkTo(claim.bottom, margin = 6.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isSmallBtnTry.value = !isSmallBtnTry.value
                        }

                        MotionEvent.ACTION_UP -> {
                            isSmallBtnTry.value = !isSmallBtnTry.value
                            navController.popBackStack(
                                route = "main",
                                inclusive = false,
                                saveState = false
                            )
                        }

                        else -> false
                    }
                    true
                }
                .scale(sizeBtnTry.value)
                .size(width = 280.dp, height = 80.dp)
                .paint(
                    painter = painterResource(id = R.drawable.bg_game_button),
                    contentScale = ContentScale.FillBounds
                ), contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = stringResource(id = R.string.main_menu),
                weight = FontWeight(800),
                size = 30.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoseScreenPreview() {
    LoseScreen(navController = rememberNavController())
}