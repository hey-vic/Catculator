package com.myprojects.catculator

sealed interface CalculatorAction {
    data class Number(val number: Int) : CalculatorAction
    object Clear : CalculatorAction
    object Delete : CalculatorAction
    object Decimal : CalculatorAction
    object Calculate : CalculatorAction

    enum class CalculatorOperation(val symbol: String) : CalculatorAction {
        ADD("+"), SUBTRACT("−"), MULTIPLY("×"), DIVIDE("÷")
    }
}
