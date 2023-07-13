package dev.sora.relay.cheat.module.impl.visual

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.entity.data.Effect
import dev.sora.relay.game.event.EventPacketInbound
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.cloudburstmc.protocol.bedrock.packet.AddEntityPacket
import org.cloudburstmc.protocol.bedrock.packet.AddPlayerPacket
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket
import org.cloudburstmc.protocol.bedrock.packet.SetEntityDataPacket

class ModuleAntiInvisible:CheatModule("AntiInvisible", CheatCategory.VISUAL) {
	private val handlePacketInbound = handle<EventPacketInbound> {
		if (packet is SetEntityDataPacket) {
			if (packet.runtimeEntityId == session.player.runtimeEntityId) {
					processInvisibleEntityData(packet.metadata)
				}
		} else if (packet is AddEntityPacket) {
			processInvisibleEntityData(packet.metadata)
		} else if (packet is AddPlayerPacket) {
			processInvisibleEntityData(packet.metadata)
		}
	}
	private fun processInvisibleEntityData(metadata: EntityDataMap?) {
		metadata?.flags ?: return

		if (metadata.flags.contains(EntityFlag.INVISIBLE)) {
			metadata.setFlag(EntityFlag.INVISIBLE, false)
		}
	}
}
