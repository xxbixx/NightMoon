package dev.sora.relay.cheat.module.impl.movement

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.Choice
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventTick
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData
import org.cloudburstmc.protocol.bedrock.packet.SetEntityMotionPacket

class ModuleGlide:CheatModule("Glide" , CheatCategory.MOVEMENT) {
	private var modeValue by choiceValue("Mode", arrayOf(Teleport()), "Teleport")
	private var glideValue by floatValue("Glide" , 0.01f , -1.0f..1.0f)
	private inner class Teleport : Choice("Teleport") {
		private val onTick = handle<EventTick> {
			session.player.teleport(
				session.player.posX ,
				session.player.posY + glideValue ,
				session.player.posZ
			)
			if (session.player.inputData.contains(PlayerAuthInputData.WANT_UP)) {
			session.player.teleport(
				session.player.posX,
				session.player.posY + 0.2f,
				session.player.posZ
			)
			} else if (session.player.inputData.contains(PlayerAuthInputData.WANT_DOWN)) {
				session.player.teleport(
					session.player.posX,
					session.player.posY - 0.2f,
					session.player.posZ
				)
			}
		}
	}
}
