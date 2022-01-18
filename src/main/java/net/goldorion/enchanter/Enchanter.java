package net.goldorion.enchanter;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Enchanter.MODID)
public class Enchanter {

    public static final Logger LOGGER = LogManager.getLogger("Enchanter");

    public static final String MODID = "enchanter";

    public Enchanter() {
    }
}
