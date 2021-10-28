package lotto.model

class Lotto(
    val price: Int,
    private val numbers: LottoNumbers
) {

    init {
        require(price >= 0)
    }

    fun match(numbers: LottoNumbers): Int = this.numbers.match(numbers)
}
