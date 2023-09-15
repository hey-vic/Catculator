package com.myprojects.catculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.catculator.ButtonContent
import com.myprojects.catculator.CalculatorAction
import com.myprojects.catculator.CalculatorState
import com.myprojects.catculator.R
import com.myprojects.catculator.ui.theme.BackgroundColor
import com.myprojects.catculator.ui.theme.DarkGray
import com.myprojects.catculator.ui.theme.DarkOrange
import com.myprojects.catculator.ui.theme.DisplayShadowColor
import com.myprojects.catculator.ui.theme.DisplayTextColor
import com.myprojects.catculator.ui.theme.SurfaceColorDark
import com.myprojects.catculator.ui.theme.SurfaceColorLight
import com.myprojects.catculator.ui.theme.SurfaceColorMedium
import com.myprojects.catculator.ui.theme.ExtraLightGray
import com.myprojects.catculator.ui.theme.LightGray
import com.myprojects.catculator.ui.theme.LightOrange
import com.myprojects.catculator.ui.theme.MediumGray
import com.myprojects.catculator.ui.theme.MediumOrange
import com.myprojects.catculator.ui.theme.SwitchColorLight
import com.myprojects.catculator.ui.theme.SwitchColorMedium
import com.myprojects.catculator.ui.theme.TextColorLight
import com.myprojects.catculator.ui.theme.sairaFF

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit
) {
    val combinedItems = mutableListOf<ButtonContent>()
    val minSize = minOf(state.numbers.size, state.operations.size)
    for (i in 0 until minSize) {
        combinedItems.addAll(state.numbers[i])
        combinedItems.add(state.operations[i])
    }
    if (state.numbers.size > state.operations.size) {
        combinedItems.addAll(state.numbers[minSize])
    } else if (state.numbers.size < state.operations.size) {
        combinedItems.add(state.operations[minSize])
    }

    var catMode by remember {
        mutableStateOf(false)
    }

    val lazyRowListState = rememberLazyListState()
    LaunchedEffect(key1 = combinedItems.size, key2 = catMode) {
        if (combinedItems.size > 0) {
            lazyRowListState.animateScrollToItem(combinedItems.size - 1)
        }
    }

    val colorStops = arrayOf(
        0.0f to SurfaceColorLight,
        0.49f to SurfaceColorLight,
        0.51f to SurfaceColorMedium,
        1f to SurfaceColorMedium
    )

    Box(modifier = modifier) {
        Switch(
            modifier = Modifier.align(Alignment.TopEnd),
            checked = catMode,
            onCheckedChange = { catMode = it },
            thumbContent = {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (catMode) R.drawable.ic_cat_filled else R.drawable.ic_cat_outline
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            },
            colors = SwitchDefaults.colors(
                checkedIconColor = LightOrange,
                checkedBorderColor = SwitchColorMedium,
                checkedThumbColor = SwitchColorLight,
                checkedTrackColor = SurfaceColorDark,
                uncheckedIconColor = SwitchColorMedium,
                uncheckedBorderColor = SwitchColorMedium,
                uncheckedThumbColor = BackgroundColor,
                uncheckedTrackColor = SurfaceColorDark
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .requiredHeight(180.dp)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.linearGradient(
                            colorStops = colorStops,
                            start = Offset(0f, 100f),
                            end = Offset(300f, 720f)
                        )
                    )
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceColorDark)
                    .padding(8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                state = lazyRowListState
            ) {
                items(combinedItems) { item ->
                    if (item.drawableRes != null && catMode) {
                        Image(
                            painter = painterResource(id = item.drawableRes),
                            contentDescription = item.text,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .size(56.dp)
                                .alpha(0.9f)
                                .blur(0.2.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            text = item.text,
                            fontSize = 46.sp,
                            color = DisplayTextColor,
                            fontWeight = FontWeight.Light,
                            fontFamily = sairaFF,
                            modifier = Modifier.shadow(
                                16.dp,
                                shape = CircleShape,
                                spotColor = DisplayShadowColor
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.linearGradient(
                            colorStops = colorStops,
                            start = Offset(0f, 225f),
                            end = Offset(950f, 1000f)
                        )
                    )
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceColorDark)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        content = ButtonContent("AC"),
                        darkColor = MediumGray,
                        mediumColor = LightGray,
                        lightColor = ExtraLightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f),
                        onClick = { onAction(CalculatorAction.Clear) }
                    )
                    CalculatorButton(
                        content = ButtonContent("Del"),
                        darkColor = MediumGray,
                        mediumColor = LightGray,
                        lightColor = ExtraLightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.Delete) }
                    )
                    CalculatorButton(
                        content = ButtonContent(CalculatorAction.CalculatorOperation.DIVIDE.symbol),
                        darkColor = DarkOrange,
                        mediumColor = MediumOrange,
                        lightColor = LightOrange,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.CalculatorOperation.DIVIDE) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        content = ButtonContent("7", R.drawable.cat7),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(7)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("8", R.drawable.cat8),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(8)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("9", R.drawable.cat9),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(9)) }
                    )
                    CalculatorButton(
                        content = ButtonContent(CalculatorAction.CalculatorOperation.MULTIPLY.symbol),
                        darkColor = DarkOrange,
                        mediumColor = MediumOrange,
                        lightColor = LightOrange,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.CalculatorOperation.MULTIPLY) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        content = ButtonContent("4", R.drawable.cat4),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(4)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("5", R.drawable.cat5),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(5)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("6", R.drawable.cat6),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(6)) }
                    )
                    CalculatorButton(
                        content = ButtonContent(CalculatorAction.CalculatorOperation.SUBTRACT.symbol),
                        darkColor = DarkOrange,
                        mediumColor = MediumOrange,
                        lightColor = LightOrange,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.CalculatorOperation.SUBTRACT) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        content = ButtonContent("1", R.drawable.cat1),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(1)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("2", R.drawable.cat2),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(2)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("3", R.drawable.cat3),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(3)) }
                    )
                    CalculatorButton(
                        content = ButtonContent(CalculatorAction.CalculatorOperation.ADD.symbol),
                        darkColor = DarkOrange,
                        mediumColor = MediumOrange,
                        lightColor = LightOrange,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.CalculatorOperation.ADD) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        content = ButtonContent("0", R.drawable.cat0),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f),
                        catMode = catMode,
                        onClick = { onAction(CalculatorAction.Number(0)) }
                    )
                    CalculatorButton(
                        content = ButtonContent("."),
                        darkColor = DarkGray,
                        mediumColor = MediumGray,
                        lightColor = LightGray,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.Decimal) }
                    )
                    CalculatorButton(
                        content = ButtonContent("="),
                        darkColor = DarkOrange,
                        mediumColor = MediumOrange,
                        lightColor = LightOrange,
                        textColor = TextColorLight,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = { onAction(CalculatorAction.Calculate) }
                    )
                }
            }
        }
    }
}