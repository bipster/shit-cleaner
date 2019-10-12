package net.ntworld.codeClimate

import net.ntworld.codeClimate.AutoGeneratedInfrastructureProvider
import net.ntworld.foundation.InfrastructureProvider

class CoreInfrastructure : InfrastructureProvider() {
    private val included = listOf(
        AutoGeneratedInfrastructureProvider(this)
    )

    init {
        wire(this.root, this.included)
    }
}