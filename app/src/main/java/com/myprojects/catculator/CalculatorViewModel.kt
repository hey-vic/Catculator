package com.myprojects.catculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.CalculatorOperation -> enterOperation(action)
        }
    }

    private fun enterOperation(action: CalculatorAction.CalculatorOperation) {
        if (state.operations.size < state.numbers.size) {
            state = state.copy(
                operations = state.operations + ButtonContent(
                    text = action.symbol
                )
            )
        } else if (state.operations.size == state.numbers.size
            && state.operations.isNotEmpty()
        ) {
            state = state.copy(
                operations = state.operations.dropLast(1) + ButtonContent(
                    text = action.symbol
                )
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operations.size < state.numbers.size) {
            val lastNumber = state.numbers.last().toMutableList()
            lastNumber.add(
                ButtonContent(
                    number.toString(),
                    getDrawableResByNumber(number)
                )
            )
            val newNumbers = state.numbers.dropLast(1).toMutableList()
            newNumbers.add(lastNumber)
            state = state.copy(
                numbers = newNumbers
            )
        } else if (state.operations.size == state.numbers.size) {
            val newNumbers = state.numbers.toMutableList()
            newNumbers.add(listOf(ButtonContent(number.toString(), getDrawableResByNumber(number))))
            state = state.copy(
                numbers = newNumbers
            )
        }
    }

    private fun performDeletion() {
        if (state.operations.size == state.numbers.size && state.operations.isNotEmpty()) {
            state = state.copy(operations = state.operations.dropLast(1))
        } else if (state.numbers.isNotEmpty()) {
            val lastNumber = state.numbers.last()
            state = if (lastNumber.size == 1) {
                state.copy(
                    numbers = state.numbers.dropLast(1)
                )
            } else {
                val newNumbers = state.numbers.dropLast(1).toMutableList()
                newNumbers.add(lastNumber.dropLast(1))
                state.copy(
                    numbers = newNumbers
                )
            }
        }
    }

    private fun enterDecimal() {
        if (state.operations.size < state.numbers.size && state.numbers.last().isNotEmpty()
            && !state.numbers.last().joinToString { it.text }.contains(".")
        ) {
            val lastNumber = state.numbers.last().toMutableList()
            lastNumber.add(ButtonContent("."))
            val newNumbers = state.numbers.dropLast(1).toMutableList()
            newNumbers.add(lastNumber)
            state = state.copy(
                numbers = newNumbers
            )
        }
    }

    private fun performCalculation() {
        val tmpNumbers = state.numbers
            .map { it.joinToString("") { el -> el.text } }
            .map { it.toDouble() }
            .toMutableList()
        var tmpOperations = state.operations.map { el -> el.text }.toMutableList()

        if (tmpNumbers.size == tmpOperations.size) {
            tmpOperations = tmpOperations.dropLast(1).toMutableList()
        }
        while (CalculatorAction.CalculatorOperation.MULTIPLY.symbol in tmpOperations) {
            val index = tmpOperations.indexOf(CalculatorAction.CalculatorOperation.MULTIPLY.symbol)
            tmpNumbers[index] *= tmpNumbers[index + 1]
            tmpNumbers.removeAt(index + 1)
            tmpOperations.removeAt(index)
        }
        while (CalculatorAction.CalculatorOperation.DIVIDE.symbol in tmpOperations) {
            val index = tmpOperations.indexOf(CalculatorAction.CalculatorOperation.DIVIDE.symbol)
            tmpNumbers[index] /= tmpNumbers[index + 1]
            tmpNumbers.removeAt(index + 1)
            tmpOperations.removeAt(index)
        }
        while (CalculatorAction.CalculatorOperation.ADD.symbol in tmpOperations) {
            val index = tmpOperations.indexOf(CalculatorAction.CalculatorOperation.ADD.symbol)
            tmpNumbers[index] += tmpNumbers[index + 1]
            tmpNumbers.removeAt(index + 1)
            tmpOperations.removeAt(index)
        }
        while (CalculatorAction.CalculatorOperation.SUBTRACT.symbol in tmpOperations) {
            val index = tmpOperations.indexOf(CalculatorAction.CalculatorOperation.SUBTRACT.symbol)
            tmpNumbers[index] -= tmpNumbers[index + 1]
            tmpNumbers.removeAt(index + 1)
            tmpOperations.removeAt(index)
        }
        val res = tmpNumbers.first()
        val roundedRes = (res * 100000.0).roundToInt() / 100000.0
        val resChars = roundedRes.toString()
            .replace("\\.0+".toRegex(), "")
            .split("")
            .filter { it.isNotBlank() }
        state = CalculatorState(
            numbers = listOf(resChars.map {
                ButtonContent(
                    text = it,
                    drawableRes = getDrawableResByNumber(it)
                )
            }),
            operations = emptyList()
        )
    }

    private fun getDrawableResByNumber(number: Int): Int? {
        return if (number in 0..9) {
            listOf(
                R.drawable.cat0, R.drawable.cat1, R.drawable.cat2,
                R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
                R.drawable.cat6, R.drawable.cat7, R.drawable.cat8,
                R.drawable.cat9
            )[number]
        } else null
    }

    private fun getDrawableResByNumber(number: String): Int? {
        return if (number.isNotBlank() && number in "0123456789") {
            listOf(
                R.drawable.cat0, R.drawable.cat1, R.drawable.cat2,
                R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
                R.drawable.cat6, R.drawable.cat7, R.drawable.cat8,
                R.drawable.cat9
            )[number.toInt()]
        } else null
    }
}