package net.ntworld.codeClimate

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import kotlinx.serialization.modules.SerializersModule
import net.ntworld.codeClimate.structure.AnalyzedIssue
import net.ntworld.codeClimate.structure.AnalyzedIssueImpl
import net.ntworld.codeClimate.structure.AnalyzedMeasurementImpl
import net.ntworld.codeClimate.structure.AnalyzedResult

object Serializer {
    private val analyzedResultModule = SerializersModule {
        polymorphic(AnalyzedResult::class) {
            AnalyzedIssueImpl::class with AnalyzedIssueImpl.serializer()

            AnalyzedMeasurementImpl::class with AnalyzedMeasurementImpl.serializer()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun parse(input: String): List<AnalyzedResult> {
        val json = Json(
            configuration = JsonConfiguration.Stable.copy(strictMode = false),
            context = analyzedResultModule
        )

        val serializer = PolymorphicSerializer(AnalyzedResult::class)
        return json.parse(serializer.list, input) as List<AnalyzedResult>
    }

    @Suppress("UNCHECKED_CAST")
    fun filterIssues(items: List<AnalyzedResult>): List<AnalyzedIssue> {
        return items.filter { it is AnalyzedIssue } as List<AnalyzedIssue>
    }

    fun parseIssues(input: String): List<AnalyzedIssue> = filterIssues(parse(input))
}