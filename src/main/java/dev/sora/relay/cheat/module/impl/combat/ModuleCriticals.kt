package dev.sora.relay.cheat.module.impl.combat

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.NamedChoice
import dev.sora.relay.game.event.EventPacketOutbound
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket

class ModuleCriticals:CheatModule("Criticals",CheatCategory.COMBAT) {
	private var modeValue by listValue("Mode", Mode.values(), Mode.VANILLA)

	private var height = 1.2f

	private enum class Mode(override val choiceName: String) : NamedChoice {
		MOVEPACKET("MovePacket"),
		VANILLA("Vanilla")
	}

	private var onPacketOutbound = handle<EventPacketOutbound> {
		val packet = this.packet
		when(modeValue) {
			Mode.MOVEPACKET -> {
				if (packet is MovePlayerPacket) {
					packet.position = Vector3f.from(packet.position.x, packet.position.y + height, packet.position.z)
					height -= 0.1f
				}
				if (height <= 0.3f) height = 1.2f
			}
			Mode.VANILLA -> {
				if(packet is MovePlayerPacket){
					packet.isOnGround = false
				}
			}
		}
	}
}
