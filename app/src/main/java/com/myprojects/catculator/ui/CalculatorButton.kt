package com.myprojects.catculator.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.catculator.ButtonContent
import com.myprojects.catculator.ui.theme.ShadowColor
import com.myprojects.catculator.ui.theme.sairaFF


@Composable
fun CalculatorButton(
    content: ButtonContent,
    modifier: Modifier = Modifier,
    darkColor: Color = Color.DarkGray,
    mediumColor: Color = Color.Gray,
    lightColor: Color = Color.LightGray,
    textColor: Color = Color.White,
    onClick: () -> Unit,
    catMode: Boolean = false
) {
    var animationStarted by remember {
        mutableStateOf(false)
    }
    var contentAnimationStarted by remember {
        mutableStateOf(false)
    }

    val animatedPadding by animateDpAsState(
        targetValue = if (animationStarted) 2.dp else 0.dp,
        animationSpec = keyframes {
            durationMillis = 100
            if (animationStarted) 0.dp else 2.dp at 0
            if (animationStarted) 2.dp else 0.dp at 90
        },
        label = "animatedPadding",
        finishedListener = { animationStarted = false }
    )

    val animatedContentScale by animateFloatAsState(
        targetValue = if (animationStarted) 0.9f else 1f,
        animationSpec = keyframes {
            durationMillis = 100
            if (animationStarted) 1f else 0.9f at 0
            if (animationStarted) 0.9f else 1f at 90
        },
        label = "animatedContentScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(animatedPadding)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(darkColor, lightColor)
                )
            )
            .clickable {
                animationStarted = true
                contentAnimationStarted = true
                onClick()
            },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(lightColor, mediumColor)
                    )
                )
        ) {
            Box(modifier = Modifier.scale(animatedContentScale)) {
                if (content.drawableRes != null && catMode) {
                    Image(
                        painter = painterResource(id = content.drawableRes),
                        contentDescription = content.text,
                        modifier = Modifier
                            .alpha(0.9f)
                            .blur(0.2.dp)
                            .padding(16.dp)
                            .shadow(
                                8.dp,
                                CircleShape,
                                spotColor = ShadowColor,
                                ambientColor = ShadowColor
                            )
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text(
                        text = content.text.uppercase(),
                        fontSize = 30.sp,
                        fontFamily = sairaFF,
                        fontWeight = FontWeight.Light,
                        color = textColor,
                        modifier = Modifier
                            .shadow(
                                8.dp,
                                CircleShape,
                                spotColor = ShadowColor,
                                ambientColor = ShadowColor
                            )
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}