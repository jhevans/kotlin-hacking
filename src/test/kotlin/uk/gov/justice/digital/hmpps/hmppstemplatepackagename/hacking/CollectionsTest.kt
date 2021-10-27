package uk.gov.justice.digital.hmpps.hmppstemplatepackagename.hacking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

open class Shape(val sides: Int)

data class Rectangle (
    val x : Int,
    val y : Int)
    : Shape(4)

data class Triangle(val equilateral: Boolean) : Shape(3)

fun printSides(shapes: List<Shape>): List<Int> {
    return shapes.map { it.sides }
}

fun printAreas(shapes: List<Rectangle>): List<Int> {
    return shapes.map { it.x * it.y }
}

fun mutablePrintSides(shapes: MutableList<Shape>): List<Int> {
    return shapes.map { it.sides }
}

fun mutateAList(shapes: MutableList<Shape>) {
    shapes.add(Triangle(true))
}

class `Collecting things for fun and profit` {
    @Test
    fun `Everyone has to start somewhere - empty collections`() {
        assertThat(emptyList<Any>().size).isEqualTo(0)
        assertThat(emptySet<Any>().size).isEqualTo(0)
        assertThat(emptyMap<Any, Any>().size).isEqualTo(0)
    }

    @Test
    fun `I have this stuff I want in a list`() {
        val listOfStuff = listOf("one", 19, RuntimeException()) as MutableList
        assertThat(listOfStuff )
        assertThrows<UnsupportedOperationException> {
            listOfStuff.add("foo")
        }

        val mutableListOfStuff = mutableListOf("one", 19, RuntimeException())
        assertThat(mutableListOfStuff.size).isEqualTo(3)
        mutableListOfStuff.add("foo")
        assertThat(mutableListOfStuff.size).isEqualTo(4)
    }

    @Test
    fun `I'm lazy, do it for me - generator functions`() {
        assertThat(List(4) { it * 2 }).isEqualTo(listOf(0, 2, 4, 6))
    }

    @Test
    fun `Mapping and stuff (not streams required)` () {
        val words = "How much wood would a wood chuck chuck if a wood chuck could chuck wood?"
            .replace("?", "")
            .split(" ")
            .toSet()

        assertThat(words).isEqualTo(setOf("How", "wood", "much", "would", "chuck", "if", "a", "could"))
    }

    @Test
    fun `What's covariance and why should I care?`() {

        // Read-only lists are Covariant.. what does that mean?
        val readOnlyList = listOf(Rectangle(1,2), Rectangle(3,4), Triangle(false))
        val rectangleReadOnlyList = listOf(Rectangle(1,2), Rectangle(3,4))

        // The printSides method is happy to accept a List<Shape>
        assertThat(printSides(readOnlyList)).isEqualTo(listOf(4, 4, 3))
        // It's also happy to accept a List<Rectangle> as Rectangle is a sub-class of Shape
        assertThat(printSides(rectangleReadOnlyList)).isEqualTo(listOf(4, 4))


        // Mutable lists are not covariant
        val mutableList = mutableListOf(Rectangle(1,2), Rectangle(3,4), Triangle(false))
        val rectangleMutableList = mutableListOf(Rectangle(1,2), Rectangle(3,4))

        // The printSide method is happy to accept a List<Shape> as before
        assertThat(mutablePrintSides(mutableList)).isEqualTo(listOf(4,4,3))
        // But we now have to cast to make our MutableList<Rectangle> work even though Rectangle is a Shape 🤔
        val castList = rectangleMutableList as MutableList<Shape>
        assertThat(mutablePrintSides(castList)).isEqualTo(listOf(4,4))
        // Oh well, lets mutate our MutableList<Shape> because it's fun to do and surely won't cause any problems
        mutateAList(castList)

        // oh... ☹️
        assertThrows<ClassCastException> {  printAreas(rectangleMutableList) }

        // but again - why should I care?
        // For the most part you don't need to, the compiler won't let you do anything stupid without casting.
        // Casting can mess you up though so be careful with it
    }
}
