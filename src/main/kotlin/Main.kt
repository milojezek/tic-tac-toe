package tictactoe

fun main() {
    val field = arrayOf(charArrayOf(' ', ' ', ' '),
        charArrayOf(' ', ' ', ' '),
        charArrayOf(' ', ' ', ' ')
    )

    var turn = 1
    var currentField: Array<CharArray> = field.clone()
    printField(currentField)
    
    // Game loop
    while(gameContinues(currentField)) {
        val coordinates = selectAndCheckCoordinates(currentField)
        currentField = updateField(coordinates, currentField, turn)
        printField(currentField)
        turn++
    }
}


fun printField(field: Array<CharArray>) {
    println("---------")
    for (row in field) {
        println("| ${row.joinToString(" ")} |")
    }
    println("---------")
}

fun askForCoordinates(): String {
    print("Enter the coordinates: ")
    val coordinates = readln()
    return coordinates.replace(" ", "")
}

fun selectAndCheckCoordinates(f: Array<CharArray>): String {
    var correctCoordinates = false
    var coordinates = ""
    while (!correctCoordinates) {
        coordinates = askForCoordinates()
        if (!coordinates.all { char -> char.isDigit() }) {
            println("You should enter numbers!")
        } else if (coordinates[0].digitToInt() !in 1..3 || coordinates[1].digitToInt() !in 1..3) {
            println("Coordinates should be from 1 to 3!")
        } else if (f[coordinates[0].digitToInt() - 1][coordinates[1].digitToInt() - 1] in "XO") {
            println("This cell is occupied! Choose another one!")
        } else {
            correctCoordinates = true
        }
    }
    return coordinates
}

fun updateField(coordinates: String, f: Array<CharArray>, turn: Int): Array<CharArray> {
    val line = coordinates[0].digitToInt() - 1
    val column = coordinates[1].digitToInt() -1
    if (turn % 2 != 0) f[line][column] = 'X' else f[line][column] = 'O'
    return f
}

fun gameContinues(f: Array<CharArray>): Boolean {
    var result = 0

    val winningCombinations = arrayOf(
        charArrayOf(f[0][0], f[0][1], f[0][2]),
        charArrayOf(f[1][0], f[1][1], f[1][2]),
        charArrayOf(f[2][0], f[2][1], f[2][2]),
        charArrayOf(f[0][0], f[1][1], f[2][2]),
        charArrayOf(f[0][2], f[1][1], f[2][0]),
        charArrayOf(f[0][0], f[1][0], f[2][0]),
        charArrayOf(f[0][1], f[1][1], f[2][1]),
        charArrayOf(f[0][2], f[1][2], f[2][2])
    )

    // Check if someone wins
    for (combination in winningCombinations) {
        if (combination.all { it == 'X' }) {
            result = 1
        } else if (combination.all { it == 'O' }) {
            result = 2
        }
    }

    // Check if there are any empty squares left
    if (' ' !in f[0] && ' ' !in f[1] && ' ' !in f[2]) {
        result = 3
    }

    return when (result) {
        1 -> {
            println("X wins")
            false
        }
        2 -> {
            println("O wins")
            false
        }
        3 -> {
            println("Draw")
            false
        }
        else -> true
    }
}
