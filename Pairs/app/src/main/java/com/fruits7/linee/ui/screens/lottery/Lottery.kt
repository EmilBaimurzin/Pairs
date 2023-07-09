package com.fruits7.linee.ui.screens.lottery

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruits7.linee.R
import com.fruits7.linee.ui.screens.CustomText
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun LotteryScreen(navController: NavController, reward: Long, lotteryViewModel: LotteryViewModel) {
    val items = lotteryViewModel.items.observeAsState()
    val context = LocalContext.current
    val sharedPreferences =
        context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    if (items.value!!.isEmpty()) {
        lotteryViewModel.generateItems()
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topLayout, list) = createRefs()

        var switch by remember {
            mutableStateOf(true)
        }

        val openedCards = items.value!!.filter { it.isOpened }
        val openedCardsValues = openedCards.map { it.itemValue }
        val sameCardsAmount2x = getAmountOfSameValuesInList<Int>(openedCardsValues, 2)
        val sameCardsAmount3x = getAmountOfSameValuesInList<Int>(openedCardsValues, 3)
        val sameCardsAmount5x = getAmountOfSameValuesInList<Int>(openedCardsValues, 5)
        val maxSames = listOf(sameCardsAmount2x, sameCardsAmount3x, sameCardsAmount5x)

        LaunchedEffect(switch) {
            if (openedCards.size == 5 && maxSames.max() < 3) {
                delay(500)
                if (sharedPreferences.getBoolean("VOLUME", true)) {
                    MediaPlayer.create(context, R.raw.sound_lose).start()
                }
                navController.navigate("lose")
            } else {
                Log.e("size", openedCards.size.toString())
            }

            if (openedCards.size == 5 && maxSames.max() == 3) {
                delay(500)
                if (sharedPreferences.getBoolean("VOLUME", true)) {
                    MediaPlayer.create(context, R.raw.sound_win).start()
                }
                when {
                    maxSames[0] == 3 -> {
                        navController.navigate("win/${reward * 2}/true")
                    }
                    maxSames[1] == 3 -> {
                        navController.navigate("win/${reward * 3}/true")
                    }
                    maxSames[2] == 3 -> {
                        navController.navigate("win/${reward * 5}/true")
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .constrainAs(topLayout) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(20.dp)
                .fillMaxWidth()
                .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF673400),
                            Color(0xFFA4550A)
                        )
                    ), RoundedCornerShape(6.dp)
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                CustomText(text = stringResource(id = R.string.find_multipliers))
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .constrainAs(list) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(topLayout.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .width(330.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(items.value!!.size) { index ->
                val item = items.value!![index]
                var isClean by rememberSaveable { mutableStateOf(false) }
                var currentAlpha by rememberSaveable { mutableStateOf(0f) }
                val animationSpec =
                    tween<Float>(500, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
                val alpha by animateFloatAsState(
                    targetValue = if (isClean) 0f else 1f,
                    animationSpec = animationSpec,
                    finishedListener = {
                        currentAlpha = it
                    }
                )

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .padding(3.dp)
                        .paint(
                            painter = painterResource(id = R.drawable.bg_slot_opened),
                            contentScale = ContentScale.FillBounds
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(60.dp),
                        painter = when (item.itemValue) {
                            2 -> painterResource(id = R.drawable.img_2x)
                            3 -> painterResource(id = R.drawable.img_3x)
                            else -> painterResource(id = R.drawable.img_5x)
                        }, contentDescription = "multiplier"
                    )
                }

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .padding(3.dp)
                        .alpha(alpha)
                        .paint(
                            painter = painterResource(id = R.drawable.bg_slot_closed),
                            contentScale = ContentScale.FillBounds
                        )
                        .clickable {
                            val openedCards2 = items.value!!.filter { it.isOpened }
                            if (!item.isOpened && openedCards2.size < 5) {
                                if (sharedPreferences.getBoolean("VOLUME", true)) {
                                    MediaPlayer.create(context, R.raw.sound_erase).start()
                                }
                                switch = !switch
                                isClean = !isClean
                                lotteryViewModel.openItem(index)
                            }
                        }
                )
            }
        }
    }
}

fun <T> getAmountOfSameValuesInList(list: Collection<T?>, value: T?): Int {
    return Collections.frequency(list, value)
}

@Preview(showBackground = true)
@Composable
fun LotteryScreenPreview() {
    LotteryScreen(navController = rememberNavController(), reward = 500, LotteryViewModel())
}