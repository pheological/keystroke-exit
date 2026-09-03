package dev.pheological.keystrokeexit.platform.fabric;

//? fabric {

import dev.pheological.keystrokeexit.KeystrokeExit;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		KeystrokeExit.onInitialize();
		KeystrokeExit.onInitializeClient();
	}

}
//?}
