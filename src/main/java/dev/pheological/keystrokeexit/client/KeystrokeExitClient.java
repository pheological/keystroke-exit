package dev.pheological.keystrokeexit.client;

import com.mojang.blaze3d.platform.InputConstants;
//? if >= 1.21.10
//import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Closes container screens when a movement key is newly pressed.
 *
 * <p>The key state is updated even while no screen is open. This prevents a
 * held movement key from immediately closing a screen that was opened while
 * the key was already down.</p>
 */
public final class KeystrokeExitClient {
	private static final boolean[] PREVIOUS_KEY_STATES = new boolean[4];

	private KeystrokeExitClient() {
	}

	public static void clientTick(Minecraft minecraft) {
		//? if >= 1.21.10 {
		/*Window window = minecraft.getWindow();
		 *///?} else {
		long window = minecraft.getWindow().getWindow();
		//?}
		KeyMapping[] movementKeys = movementKeys(minecraft);
		boolean newlyPressed = false;

		for (int index = 0; index < movementKeys.length; index++) {
			KeyMapping movementKey = movementKeys[index];
			InputConstants.Key boundKey = movementKey.key;
			boolean pressed;
			if (boundKey.getType() == InputConstants.Type.KEYSYM) {
				//? if >= 1.21.10 {
				/*pressed = InputConstants.isKeyDown(window, boundKey.getValue());
				 *///?} else {
				pressed = InputConstants.isKeyDown(window, boundKey.getValue());
				//?}
			} else {
				// Preserve Minecraft's own handling for non-keyboard bindings.
				pressed = movementKey.isDown();
			}
			newlyPressed |= pressed && !PREVIOUS_KEY_STATES[index];
			PREVIOUS_KEY_STATES[index] = pressed;
		}

		//? if >= 26.2 {
		/*Screen screen = minecraft.gui.screen();
		 *///?} else {
		Screen screen = minecraft.screen;
		//?}
		if (newlyPressed
				&& minecraft.player != null
				&& screen instanceof AbstractContainerScreen<?>
				&& !hasFocusedTextInput(screen)) {
			//? if >= 26.2 {
			/*minecraft.gui.setScreen(null);
			 *///?} else {
			minecraft.setScreen(null);
			//?}
			forwardHeldKeys(movementKeys);
		}
	}

	private static KeyMapping[] movementKeys(Minecraft minecraft) {
		return new KeyMapping[] {
				minecraft.options.keyUp,
				minecraft.options.keyLeft,
				minecraft.options.keyDown,
				minecraft.options.keyRight
		};
	}

	/**
	 * A screen consumes the original key event, so Minecraft's key mapping can
	 * remain released until the operating system emits a repeat event. Forward
	 * the physical state immediately to avoid the macOS key-repeat delay.
	 */
	private static void forwardHeldKeys(KeyMapping[] movementKeys) {
		for (int index = 0; index < movementKeys.length; index++) {
			if (PREVIOUS_KEY_STATES[index]) {
				KeyMapping.set(movementKeys[index].key, true);
			}
		}
	}

	/**
	 * Walks the active focus chain rather than excluding whole screen types.
	 * Creative search, anvil names, recipe/knowledge-book search, and modded
	 * container text boxes therefore keep receiving movement keys normally.
	 */
	private static boolean hasFocusedTextInput(Screen screen) {
		Set<GuiEventListener> visited =
				Collections.newSetFromMap(new IdentityHashMap<>());
		GuiEventListener focused = screen.getFocused();

		while (focused != null && visited.add(focused)) {
			if (focused instanceof EditBox) {
				return true;
			}
			if (focused instanceof ContainerEventHandler container) {
				focused = container.getFocused();
			} else {
				focused = null;
			}
		}

		/*
		 * Recipe-book search is nested inside RecipeBookComponent and is not
		 * part of Screen#getFocused(). Inspect nested GUI listeners so that
		 * search box—and equivalent modded components—are covered as well.
		 */
		return containsFocusedTextInput(screen, 0,
				Collections.newSetFromMap(new IdentityHashMap<>()));
	}

	private static boolean containsFocusedTextInput(
			Object component,
			int depth,
			Set<Object> visited) {
		if (component == null || depth > 4 || !visited.add(component)) {
			return false;
		}
		if (component instanceof EditBox editBox) {
			return editBox.isFocused();
		}

		for (Class<?> type = component.getClass();
				type != null && type != Object.class;
				type = type.getSuperclass()) {
			for (Field field : type.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				try {
					field.setAccessible(true);
					Object value = field.get(component);
					if (value instanceof GuiEventListener
							&& containsFocusedTextInput(value, depth + 1, visited)) {
						return true;
					}
				} catch (RuntimeException | IllegalAccessException ignored) {
					// An inaccessible optional field should not block normal exit behavior.
				}
			}
		}

		return false;
	}
}
