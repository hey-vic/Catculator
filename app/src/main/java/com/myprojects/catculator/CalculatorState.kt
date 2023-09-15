package com.myprojects.catculator

data class CalculatorState(
    val numbers: List<List<ButtonContent>> = emptyList(),
    val operations: List<ButtonContent> = emptyList()
)
