package dev.sora.relay.cheat.module.impl.visual

import dev.sora.relay.cheat.module.CheatCategory
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventPacketInbound
import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket
import org.cloudburstmc.protocol.bedrock.packet.TextPacket

class ModuleTextSpoof:CheatModule("TextSpoof", CheatCategory.VISUAL) {
	private var oldTextValue by stringValue("OldText","None")
	private var newTextValue by stringValue("NewText","None")

	private var onPacketInbound = handle<EventPacketInbound> {
		val packet = this.packet
		if(packet is TextPacket){
			if(packet.message.contains(oldTextValue)){
				packet.message = packet.message.replace(oldTextValue, newTextValue)
			}
		} else if(packet is SetTitlePacket){
			if(packet.text.contains(oldTextValue)) {
				packet.text = packet.text.replace(oldTextValue, newTextValue)
			}
		}
	}
}
