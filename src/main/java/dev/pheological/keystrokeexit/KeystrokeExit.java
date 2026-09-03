package dev.pheological.keystrokeexit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("LoggingSimilarMessage")
public class KeystrokeExit {

	public static final String MOD_ID = /*$ mod_id*/ "keystrokeexit";
	public static final String MOD_VERSION = /*$ mod_version*/ "3.0.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Keystroke Exit";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void onInitialize() {
		LOGGER.info("Initializing {} on Fabric", MOD_ID);
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} client", MOD_ID);
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}
}
