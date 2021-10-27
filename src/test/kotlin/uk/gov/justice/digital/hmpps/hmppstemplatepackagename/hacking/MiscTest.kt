package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.hacking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Version(val major: Int, val minor: Int): Comparable<Version> {
    override fun compareTo(other: Version): Int {
        if (this.major != other.major) {
            return this.major - other.major
        }
        return this.minor - other.minor
    }
}

class `Some nice tricks` {
    @Test
    fun `Custom ranges` () {
        // We can use ranges for any class implementing the Comparable interface
        val versionRange = Version(0, 4)..Version(2, 5)

        assertThat(Version(0,1) in versionRange).isFalse
        assertThat(Version(1,1) in versionRange).isTrue
        assertThat(Version(3,1) in versionRange).isFalse
    }

    @Test
    fun `Progressions` () {
        val list = mutableListOf<Int>()
        for (i in 50..100 step 10) list.add(i)

        assertThat(list).isEqualTo(listOf(50,60,70,80,90,100))
    }

    @Test
    fun `Progressions (descending)` () {
        val list = mutableListOf<Int>()
        for (i in 100 downTo 50 step 10) list.add(i)

        assertThat(list).isEqualTo(listOf(100,90,80,70,60,50))
    }

    @Test
    fun `Sequences` () {
        val sequence = generateSequence(10) { it * 10 }

        assertThat(sequence.take(5).toList()).isEqualTo(listOf(10, 100, 1000, 10000, 100000))
    }

    @Test
    fun `Associate` () {
        val list = listOf("Tom", "Dick", "Harry")
        assertThat(list.associateWith { it.length }).isEqualTo(mapOf("Tom" to 3, "Dick" to 4, "Harry" to 5))

        assertThat(list.associateBy( keySelector = { it.first().uppercase()}, valueTransform = {it.length })).isEqualTo(mapOf("T" to 3, "D" to 4, "H" to 5))
    }

    @Test
    fun `Set arithmetic` () {
        val odds = setOf(1,3,5)
        val evens = setOf(2,4,6)
        val factorsOf3 = setOf(3,6,9)

        assertThat(odds intersect factorsOf3).containsOnly(3)
        assertThat(evens subtract  factorsOf3).containsOnly(2,4)
        assertThat(evens union odds).containsOnly(1,2,3,4,5,6)

        // These operators are really just infix functions - so we can define our own
        // The difference operator is notably absent so lets define it
        infix fun <T> Iterable<T>.difference(other: Iterable<T>) : Set<T> {
            return other subtract this
        }

        // And so...
        assertThat(evens difference factorsOf3).containsOnly(3,9)
    }
}
