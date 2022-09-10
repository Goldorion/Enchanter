package net.goldorion.enchanter;

import net.fabricmc.api.ModInitializer;
import net.goldorion.enchanter.events.PlayerSleepEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enchanter implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Enchanter");

    public static final String MODID = "enchanter";

    @Override
    public void onInitialize() {
        LOGGER.debug("Registering enchantments...");
        ModEnchantments.register();
        LOGGER.debug("Successfully registered enchantments!");

        new PlayerSleepEvent();
    }
}
