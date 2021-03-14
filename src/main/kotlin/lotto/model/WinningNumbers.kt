package lotto.model

data class WinningNumbers(val winningNumbers: Set<LottoNumber>) : Set<LottoNumber> by winningNumbers {
    constructor(numbers: List<Int>) : this(numbers.map { LottoNumber(it) }.toSet())
}