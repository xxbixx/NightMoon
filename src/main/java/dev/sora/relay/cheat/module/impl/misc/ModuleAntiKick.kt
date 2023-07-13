package dev.sora.relay.cheat.module.impl.misc

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventPacketInbound
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket

class ModuleAntiKick:CheatModule("AntiKick",CheatCategory.MISC) {

	private var onPacketInbound = handle<EventPacketInbound> {
		val packet = this.packet

		if(packet is DisconnectPacket){
			cancel()
			session.chat("disconnect: ${packet.kickMessage}")
		}

		if(packet is TransferPacket){
			cancel()
			session.chat("transfer cancel!")
		}
	}
}
