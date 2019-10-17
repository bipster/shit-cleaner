package net.ntworld.codeClimate.command

import net.ntworld.foundation.cqrs.Command

interface AnalyzeCommand: Command {
    val cwd: String

    val watchId: String?

    val timeout: Long

    companion object
}
