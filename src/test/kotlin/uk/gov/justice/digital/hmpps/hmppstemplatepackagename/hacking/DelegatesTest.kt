package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.hacking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

interface Drone {
    fun makeTea() :String
}

class DisgruntledDrone : Drone {
    override fun makeTea(): String {
        print("Here's your p%&^(@g tea! Hehehe")
        return "Tea with unpleasantness in"
    }
}

class BigCheese(assistant: Drone) : Drone by assistant

class DelegatesTest {

    @Test
    fun `Delegate the crap jobs` () {
        val bigCheese = BigCheese(DisgruntledDrone())

        assertThat(bigCheese.makeTea()).isEqualTo("Tea with unpleasantness in")
    }

    @Test
    fun `I'll do it later 😫` () {
        val crapOnFloor = listOf("socks", "pizza box", "smarties", "empty beer can", "more socks", "don't ask", "running shoes", "frisbee", "once smart, now scuffed shoes", "pants")
        val smartShoes: String by lazy {
            println("Fine!")
            println("Searching for smart shoes...")
            crapOnFloor.filter { it.contains("shoes") }
                .filter {it.contains("smart")}
                .first()
        }

        assertThat(smartShoes).isEqualTo("once smart, now scuffed shoes")
    }

    @Test
    fun `I'm watching you boio!` () {
        val boioShenanigans = mutableListOf<String>()
        class Boio {
            var currentShenanigan: String by Delegates.observable("No shenanigans here") {
                prop, old, new ->
                boioShenanigans.add(new)
            }
        }

        val boio = Boio()
        boio.currentShenanigan = "Scrumping apples"
        boio.currentShenanigan = "Tipping cows"
        boio.currentShenanigan = "Shouting curse words at old dears"

        assertThat(boioShenanigans).containsExactly(
            "Scrumping apples",
            "Tipping cows",
            "Shouting curse words at old dears",
        )
    }
}
