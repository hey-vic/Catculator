package com.myprojects.catculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myprojects.catculator.ui.Calculator
import com.myprojects.catculator.ui.theme.BackgroundColor
import com.myprojects.catculator.ui.theme.CatculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatculatorTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                Calculator(
                    state = state,
                    onAction = viewModel::onAction,
                    buttonSpacing = 4.dp,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)
                        .padding(20.dp)
                )
            }
        }
    }
}