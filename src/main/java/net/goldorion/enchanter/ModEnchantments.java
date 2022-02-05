package net.goldorion.enchanter;

import net.goldorion.enchanter.enchantments.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchantments {
    private static final List<Enchantment> enchantments = new ArrayList<>();
    public static final EquipmentSlot[] ARMORS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
    public static final EquipmentSlot[] ALL = new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST,
            EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    //Protections
    public static final Enchantment CACTUS_PROTECTION = register("cactus_protection", new ModProtectionEnchantment(Enchantment.Rarity.UNCOMMON,
            ModProtectionEnchantment.Type.CACTUS, EnchantmentCategory.ARMOR, ARMORS));
    public static final Enchantment FALLING_BLOCK_PROTECTION = register("falling_block_protection", new ModProtectionEnchantment(Enchantment.Rarity.UNCOMMON,
            ModProtectionEnchantment.Type.FALLING_BLOCK, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final Enchantment LIGHTNING_BOLT_PROTECTION = register("lightning_bolt_protection", new ModProtectionEnchantment(Enchantment.Rarity.UNCOMMON,
            ModProtectionEnchantment.Type.LIGHTNING_BOLT, EnchantmentCategory.ARMOR, ARMORS));


    // Tools
    public static final Enchantment FARMER = register("farmer", new Farmer());

    // Weapons
    public static final Enchantment  MULTI_ARROWS = register("multi_arrows", new MultiArrows());

    // Treasures
    public static Enchantment SECOND_CHANCE = register("second_chance", new SecondChance());

    // Curses
    public static final Enchantment SLEEPLESS_NIGHT = register("sleepless_night", new SleeplessNight());
    public static final Enchantment WRONG_BLOCK = register("wrong_block", new WrongBlock());

    //Misc
    public static final Enchantment MAGMA_WALKER = register("magma_walker", new MagmaWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
    public static final Enchantment UNVANISHABLE = register("unvanishable", new Unvanishable());

    private static Enchantment register(String registryName, Enchantment enchantment) {
        enchantments.add(enchantment.setRegistryName(registryName));
        return enchantment;
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(enchantments.toArray(new Enchantment[0]));
    }

}
