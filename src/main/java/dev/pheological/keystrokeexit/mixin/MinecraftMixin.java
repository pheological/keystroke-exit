package dev.pheological.keystrokeexit.mixin;

import dev.pheological.keystrokeexit.client.KeystrokeExitClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Inject(method = "tick", at = @At("TAIL"))
	private void keystrokeexit$afterClientTick(CallbackInfo ci) {
		KeystrokeExitClient.clientTick((Minecraft) (Object) this);
	}
}
