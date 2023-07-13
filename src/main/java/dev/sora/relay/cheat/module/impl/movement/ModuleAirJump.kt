package dev.sora.relay.cheat.module.impl.movement

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.Choice
import dev.sora.relay.game.event.EventTick
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData
import org.cloudburstmc.protocol.bedrock.packet.SetEntityMotionPacket

class ModuleAirJump : CheatModule("AirJump", CheatCategory.MOVEMENT) {

	private var modeValue by choiceValue("Mode", arrayOf(Motion(), Teleport()), "Motion")
	private var jumpValue by floatValue("Jump", 0.42f, 0.1f..3f)


	private open inner class Motion : Choice("Motion") {
		private var speedMultiplierValue by floatValue("SpeedMultiplier", 1f, 0.5f..3f)
		private var speedBoostValue by boolValue("Speed Boost", false)
		private val onTick = handleOneTime<EventTick>({ it.session.player.inputData.contains(PlayerAuthInputData.JUMP_DOWN) }) {
			val player = session.player

			if (!player.onGround && !player.prevOnGround) {
				session.netSession.inboundPacket(SetEntityMotionPacket().apply {
					runtimeEntityId = player.runtimeEntityId
					if(speedBoostValue) {
						motion = Vector3f.from(
							player.motionX * speedMultiplierValue,
							jumpValue,
							player.motionZ * speedMultiplierValue
						)
					} else {
						motion = Vector3f.from(
							player.motionX,
							jumpValue,
							player.motionZ
						)
					}
				})
			}
		}
	}

	private open inner class Teleport : Choice("Teleport") {

		private val onTick = handleOneTime<EventTick>({ it.session.player.inputData.contains(PlayerAuthInputData.JUMP_DOWN) }) {
			val player = session.player

			if (!player.onGround && !player.prevOnGround) {
				player.teleport(
					player.posX ,
					player.posY  + jumpValue ,
					player.posZ
				)
			}
		}
	}
}
