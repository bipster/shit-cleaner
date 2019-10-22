package net.ntworld.codeClimate

import net.ntworld.codeCleaner.Serializer
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializerTest {

    @Test
    fun `test parse`() {
        val input = SerializerTest::class.java.getResource("/analyzed-results/foundation-v0.5.1.json").readText()
        val result = Serializer.parse(input)

        assertEquals(315, result.size)
    }

    @Test
    fun `test parseIssues`() {
        val input = SerializerTest::class.java.getResource("/analyzed-results/foundation-v0.5.1.json").readText()
        val result = Serializer.parseIssues(input)

        println(result)
        assertEquals(311, result.size)
    }
}