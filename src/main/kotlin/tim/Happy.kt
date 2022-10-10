package tim

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main() {
    println("Нажми enter что бы остановить рулетку желаний")

    val wish1 = Wish(
        words = listOf(
            "лучшей",
            "первой на селе",
            "желанной",
            "красивой",
            "манящей"
        )
    )
    val wish2 = Wish(
        words = listOf(
            "красоткой",
            "чикулей",
            "мадамой",
            "малышкой",
            "принцессой",
            "совратительницей",
            "мамой",
            "кошечкой"
        )
    )
    val wish3 = Wish(
        words = listOf(
            "во всем мире",
            "на селе",
            "для любимого",
            "в галактике",
            "на конкурсе красоты",
            "на порохабе",
            "под небом и землей"
        )
    )
    val list = WishList(
        list = listOf(wish1, wish2, wish3)
    )

    thread {
        while (!list.allSelected()) {
            list.list.forEach {
                if (!it.selected()) {
                    it.nextWish()
                }
            }

            print("\r" + list.wishes(true))
            Thread.sleep(300)
        }
    }
    list.list.forEach {
        readln()
        it.selectedCurrent()
    }
    println()
    println("Поздравление: ")
    println("От всего сердца желаю тебе стать самой " + list.wishes(false))
    readln()
}

class Wish(
    val words: List<String>
) {
    private val counter = AtomicInteger(0)
    private val flag = AtomicBoolean(false)

    private val maxLength = words.maxBy { it.length }.length

    fun nextWish() {
        if (counter.get() >= words.size - 1) {
            counter.set(0)
        } else {
            counter.incrementAndGet()
        }
    }

    fun getCurrentWish(fixed: Boolean) = words[counter.get()].let {
        if (fixed) {
            it.padEnd(maxLength)
        } else {
            it
        }
    }


    fun selectedCurrent() {
        flag.set(true)
    }

    fun selected() = flag.get()
}

class WishList(
    val list: List<Wish>
) {
    fun allSelected() = !list.any { !it.selected() }

    fun wishes(fixed: Boolean) = list.joinToString(", ") { it.getCurrentWish(fixed) }

    fun next() = list.forEach {
        if (!it.selected()) {
            it.nextWish()
        }
    }
}