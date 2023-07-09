package com.fruits7.linee.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fruits7.linee.R
import com.fruits7.linee.ui.screens.game.GameScreen
import com.fruits7.linee.ui.screens.game.GameViewModel
import com.fruits7.linee.ui.screens.lottery.LotteryScreen
import com.fruits7.linee.ui.screens.lottery.LotteryViewModel
import com.fruits7.linee.ui.theme.CricketStarEmilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketStarEmilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,

                    ) {
                    BoxWithConstraints {
                        StartNavHost(
                            activity = this@MainActivity,
                            width = this.constraints.maxWidth / 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StartNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    activity: MainActivity,
    width: Int
) {
    activity.onBackPressedDispatcher.addCallback(activity, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.popBackStack("main", inclusive = false)
        }
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_game_portrait),
                contentScale = ContentScale.FillBounds
            ),
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bg_anim))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            progress = { progress },
        )

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable("main"
               ) {
                navController.previousBackStackEntry?.viewModelStore?.clear()
                MainScreen(navController)
            }
            composable("bid",

            ) {
                navController.previousBackStackEntry?.viewModelStore?.clear()
                BidScreen(navController)
            }
            composable(
                "settings",
            ) {
                navController.previousBackStackEntry?.viewModelStore?.clear()
                SettingsScreen(navController, activity)
            }
            composable("rules") {
                navController.previousBackStackEntry?.viewModelStore?.clear()
                RulesScreen(navController)
            }
            composable("lose",) {
                navController.previousBackStackEntry?.viewModelStore?.clear()
                LoseScreen(navController)
            }
            composable(
                "lottery/{reward}",
                arguments = listOf(
                    navArgument("reward") { type = NavType.LongType },
                )
            ) { backStackEntry ->
                navController.previousBackStackEntry?.viewModelStore?.clear()
                val viewModel: LotteryViewModel = viewModel()
                LotteryScreen(
                    navController,
                    lotteryViewModel = viewModel,
                    reward = backStackEntry.arguments?.getLong("reward") ?: 0
                )
            }
            composable(
                "win/{reward}/{isLottery}",
                arguments = listOf(
                    navArgument("reward") { type = NavType.LongType },
                    navArgument("isLottery") { type = NavType.BoolType },
                ),
            ) { backStackEntry ->
                navController.previousBackStackEntry?.viewModelStore?.clear()
                WinScreen(
                    navController,
                    reward = backStackEntry.arguments?.getLong("reward") ?: 0,
                    isLottery = backStackEntry.arguments?.getBoolean("isLottery") ?: true
                )
            }
            composable(
                "game/{bid}/{timer}",
                arguments = listOf(
                    navArgument("bid") { type = NavType.LongType },
                    navArgument("timer") { type = NavType.IntType },
                )
            ) { backStackEntry ->
                navController.previousBackStackEntry?.viewModelStore?.clear()
                val viewModel: GameViewModel = viewModel()
                GameScreen(
                    activity = activity,
                    bid = backStackEntry.arguments?.getLong("bid") ?: 0,
                    timer = backStackEntry.arguments?.getInt("timer") ?: 0,
                    gameViewModel = viewModel,
                    navController = navController,
                )
            }
        }
    }
}