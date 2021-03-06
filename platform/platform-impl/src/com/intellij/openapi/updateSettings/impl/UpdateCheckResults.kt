// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.updateSettings.impl

import com.intellij.ide.externalComponents.ExternalComponentSource
import com.intellij.ide.externalComponents.UpdatableExternalComponent
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginNode
import com.intellij.openapi.util.BuildNumber
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class UpdateChain internal constructor(
  val chain: List<BuildNumber>,
  val size: String?,
)

sealed class CheckForUpdateResult(open val newBuild: BuildInfo? = null) {

  internal object Empty : CheckForUpdateResult()

  @ApiStatus.Internal
  data class Loaded @JvmOverloads internal constructor(
    override val newBuild: BuildInfo,
    val updatedChannel: UpdateChannel,
    val patches: UpdateChain? = null,
  ) : CheckForUpdateResult(newBuild)

  internal data class ConnectionError(val error: Exception) : CheckForUpdateResult()
}

/**
 * [allEnabled] - new versions of enabled plugins compatible with the specified build
 *
 * [allDisabled] - new versions of disabled plugins compatible with the specified build
 *
 * [incompatible] - plugins that would become incompatible and don't have updates compatible with the specified build
 */
@ApiStatus.Internal
data class PluginUpdates @JvmOverloads internal constructor(
  val allEnabled: Collection<PluginDownloader> = emptyList(),
  val allDisabled: Collection<PluginDownloader> = emptyList(),
  val incompatible: Collection<IdeaPluginDescriptor> = emptyList(),
) {

  val all: List<PluginDownloader> by lazy {
    allEnabled + allDisabled
  }

  internal val enabled: Sequence<PluginDownloader> by lazy {
    nonIgnored(allEnabled)
  }

  internal val disabled: Sequence<PluginDownloader> by lazy {
    nonIgnored(allDisabled)
  }

  internal val updated: List<PluginDownloader> by lazy {
    (enabled + disabled).toList()
  }

  private fun nonIgnored(downloaders: Collection<PluginDownloader>): Sequence<PluginDownloader> {
    return downloaders.asSequence()
      .filterNot { UpdateChecker.isIgnored(it.descriptor) }
  }
}

@ApiStatus.Internal
data class InternalPluginResults @JvmOverloads internal constructor(
  val pluginUpdates: PluginUpdates,
  val pluginNods: Collection<PluginNode> = emptyList(),
  val errors: Map<String?, Exception> = emptyMap(),
)

@ApiStatus.Internal
data class ExternalUpdate @JvmOverloads internal constructor(
  val source: ExternalComponentSource,
  val components: Collection<UpdatableExternalComponent> = emptyList(),
)

@ApiStatus.Internal
data class ExternalPluginResults @JvmOverloads internal constructor(
  val externalUpdates: Collection<ExternalUpdate> = emptyList(),
  val errors: Map<ExternalComponentSource, Exception> = emptyMap(),
)