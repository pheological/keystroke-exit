package keystrokes.pheological;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.util.InputUtil;

public class KeystrokeexitClient implements ClientModInitializer {
	private boolean wasWPressed = false;
	private boolean wasAPressed = false;
	private boolean wasSPressed = false;
	private boolean wasDPressed = false;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.currentScreen instanceof HandledScreen && !client.player.getAbilities().creativeMode) { //Creative Mode
				if (isKeyPressedOnce(GLFW.GLFW_KEY_W, wasWPressed, keyState -> wasWPressed = keyState) ||
						isKeyPressedOnce(GLFW.GLFW_KEY_A, wasAPressed, keyState -> wasAPressed = keyState) ||
						isKeyPressedOnce(GLFW.GLFW_KEY_S, wasSPressed, keyState -> wasSPressed = keyState) ||
						isKeyPressedOnce(GLFW.GLFW_KEY_D, wasDPressed, keyState -> wasDPressed = keyState)) {

					// Close inventory
					client.setScreen(null);

				}
			}
		});
	}

	private boolean isKeyPressedOnce(int keyCode, boolean wasKeyPreviouslyPressed, java.util.function.Consumer<Boolean> updateKeyState) {
		boolean isCurrentlyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode);

		if (isCurrentlyPressed && !wasKeyPreviouslyPressed) {
			updateKeyState.accept(true);
			return true;
		} else if (!isCurrentlyPressed) {
			updateKeyState.accept(false);
		}

		return false;
	}

}
