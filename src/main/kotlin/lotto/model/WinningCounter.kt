package lotto.model

import lotto.model.LottoPlace.FIRST
import lotto.model.LottoPlace.SECOND
import lotto.model.LottoPlace.THIRD
import lotto.model.LottoPlace.FOURTH
import lotto.model.LottoPlace.FIFTH
import lotto.model.LottoPlace.MISS

data class WinningCounter(
    private val counter: MutableMap<LottoPlace, Int> = mutableMapOf(
        FIRST to 0,
        SECOND to 0,
        THIRD to 0,
        FOURTH to 0,
        FIFTH to 0
    )
) : Map<LottoPlace, Int> by counter {
    val winningsSum: Money
        get() {
            return counter.entries.fold(Money.zero, sumWinningsTimesCount())
        }

    constructor(tickets: LottoTickets, winningNumbers: WinningNumbers, bonusNumbers: BonusNumbers) : this() {
        tickets.forEach {
            val winningCount = it.countMatch(winningNumbers)
            val bonusCount = it.countMatch(bonusNumbers)

            record(winningCount, bonusCount)
        }
    }

    private fun sumWinningsTimesCount() =
        { accu: Money, (lottoPlace, winningCount): MutableMap.MutableEntry<LottoPlace, Int> ->
            accu + lottoPlace.winnings * winningCount
        }

    private fun record(matchCount: Int, bonusCount: Int) {
        val place = LottoPlace.match(matchCount, bonusCount)

        increment(place)
    }

    private fun increment(place: LottoPlace) {
        if (place == MISS) {
            return
        }

        counter[place] = counter.getValue(place) + 1
    }
}