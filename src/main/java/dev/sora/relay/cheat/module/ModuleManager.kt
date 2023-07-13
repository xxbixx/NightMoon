package dev.sora.relay.cheat.module

import dev.sora.relay.cheat.module.impl.combat.*
import dev.sora.relay.cheat.module.impl.misc.*
import dev.sora.relay.cheat.module.impl.movement.*
import dev.sora.relay.cheat.module.impl.visual.*
import dev.sora.relay.game.GameSession

class ModuleManager(private val session: GameSession) {

    val modules = mutableListOf<CheatModule>()

    fun registerModule(module: CheatModule) {
		module.session = session
		module.moduleManager = this
        modules.add(module)
        module.register(session.eventManager)
    }

    fun init() {
		//combat
		registerModule(ModuleOpFightBot())
		registerModule(ModuleVelocity())
        registerModule(ModuleKillAura())
		registerModule(ModuleTargets())
		registerModule(ModuleSurround())
		registerModule(ModuleCrystalAura())
		registerModule(ModuleCriticals())

		//misc
        registerModule(ModuleSpammer())
        registerModule(ModuleBGM())
        registerModule(ModuleDisabler())
        registerModule(ModuleNoSkin())
        registerModule(ModuleDeviceSpoof())
        registerModule(ModuleResourcePackInject())
		registerModule(ModuleMiner())
		registerModule(ModuleInventoryHelper())

		//movement
		registerModule(ModuleFly())
        registerModule(ModuleNoFall())
        registerModule(ModuleFastBreak())
        registerModule(ModuleBlink())
        registerModule(ModuleBlockFly())

		registerModule(ModuleAirJump())
		registerModule(ModuleClip())
		registerModule(ModuleSpeed())

		//visual
		registerModule(ModuleNoHurtCam())
		registerModule(ModuleAntiBlind())
		registerModule(ModuleHitEffect())
		registerModule(ModuleAntiInvisible())
		registerModule(ModuleTextSpoof())
		registerModule(ModuleNoFireCam())
    }

	inline fun <reified T : CheatModule> getModule(t: Class<T>): T {
		return modules.filterIsInstance<T>().first()
	}
}
