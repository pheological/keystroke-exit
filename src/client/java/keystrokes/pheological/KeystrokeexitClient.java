package keystrokes.pheological;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.util.InputUtil;

import net.fabricmc.api.ClientModInitializer;

public class KeystrokeexitClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.currentScreen instanceof HandledScreen) {
				HandledScreen<?> screen = (HandledScreen<?>) client.currentScreen;

				if (isKeyPressed(GLFW.GLFW_KEY_W) ||
						isKeyPressed(GLFW.GLFW_KEY_A) ||
						isKeyPressed(GLFW.GLFW_KEY_S) ||
						isKeyPressed(GLFW.GLFW_KEY_D)) {
					client.setScreen(null);
				}
			}
		});
	}

	private boolean isKeyPressed(int keyCode) {
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode);
	}
	}
