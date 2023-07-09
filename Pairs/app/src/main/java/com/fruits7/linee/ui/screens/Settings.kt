package com.fruits7.linee.ui.screens

import android.content.Context.MODE_PRIVATE
import android.view.MotionEvent
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Snackbar
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun SettingsScreen(navController: NavController, activity: MainActivity) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (btnVolume, btnReset, btnExit, imgLogo, character, btnBack, snackBar) = createRefs()
        val isSmallBtnGame = remember { mutableStateOf(true) }
        val isSmallBtnSettings = remember { mutableStateOf(true) }
        val isSmallBtnRules = remember { mutableStateOf(true) }
        val sizeBtnGame = animateFloatAsState(targetValue = if (isSmallBtnGame.value) 1f else 0.9f)
        val sizeBtnRules =
            animateFloatAsState(targetValue = if (isSmallBtnRules.value) 1f else 0.9f)
        val sizeBtnSettings =
            animateFloatAsState(targetValue = if (isSmallBtnSettings.value) 1f else 0.9f)
        val sharedPreferences =
            LocalContext.current.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)

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


        val volumeState = remember {
            mutableStateOf(true)
        }

        val snackBarState = remember {
            mutableStateOf(false)
        }

        volumeState.value = sharedPreferences.getBoolean("VOLUME", true)

        Image(
            modifier = Modifier
                .constrainAs(btnBack) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(parent.start, margin = 12.dp)
                }
                .clickable {
                    navController.popBackStack()
                }
                .size(width = 50.dp, height = 50.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "back")

        Image(
            modifier = Modifier
                .constrainAs(imgLogo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(btnVolume.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 360.dp, height = 120.dp)
                .scale(logoScale),
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Logo")

        Box(
            modifier = Modifier
                .constrainAs(btnVolume) {
                    bottom.linkTo(btnReset.top, margin = 6.dp)
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
                            sharedPreferences
                                .edit()
                                .putBoolean("VOLUME", !volumeState.value)
                                .apply()
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
                text = "Volume ${if (volumeState.value) "On" else "Off"}",
                weight = FontWeight(800),
                size = 30.sp
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(btnReset) {
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
                            sharedPreferences
                                .edit()
                                .putLong("BALANCE", 5000)
                                .apply()
                            snackBarState.value = true
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
                text = stringResource(id = R.string.reset_balance),
                weight = FontWeight(800),
                size = 30.sp
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(btnExit) {
                    top.linkTo(btnReset.bottom, margin = 6.dp)
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
                            activity.finish()
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
                text = stringResource(id = R.string.exit_game),
                weight = FontWeight(800),
                size = 28.sp
            )
        }
        Image(
            modifier = Modifier
                .constrainAs(character) {
                    top.linkTo(btnExit.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(180.dp),
            painter = painterResource(id = R.drawable.img_character),
            contentDescription = "Character")

        if (snackBarState.value) {
            Snackbar(
                action = {
                    Box(modifier = Modifier
                        .clickable { snackBarState.value = false }
                        .padding(end = 12.dp)) {
                        CustomText(text = "Close")
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(snackBar) {
                        bottom.linkTo(parent.bottom)
                    },

            ) {
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    CustomText(text = "Your balance has been reset")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController(), activity = MainActivity())
}