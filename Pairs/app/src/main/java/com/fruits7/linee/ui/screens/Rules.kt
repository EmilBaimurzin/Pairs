package com.fruits7.linee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruits7.linee.R

@Composable
fun RulesScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .scrollable(orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    delta
                })
    ) {
        items(1) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF673400),
                                Color(0xFFA4550A)
                            )
                        ), RoundedCornerShape(6.dp)
                    )
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(height = 260.dp, width = 180.dp),
                    painter = painterResource(id = R.drawable.img_gameplay),
                    contentDescription = "gameplay"
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    CustomText(text = stringResource(id = R.string.main_rule))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF673400),
                                Color(0xFFA4550A)
                            )
                        ), RoundedCornerShape(6.dp)
                    )
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CustomText(text = stringResource(id = R.string.difficulty_rule))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF673400),
                                Color(0xFFA4550A)
                            )
                        ), RoundedCornerShape(6.dp)
                    )
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.img_2x),
                        contentDescription = "2x"
                    )
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.img_3x),
                        contentDescription = "3x"
                    )
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.img_5x),
                        contentDescription = "4x"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    CustomText(text = stringResource(id = R.string.lottery_rule))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RulesScreenPreview() {
    RulesScreen(rememberNavController())
}