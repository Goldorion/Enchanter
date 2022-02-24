package net.goldorion.enchanter;

import net.goldorion.enchanter.enchantments.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.MendingEnchantment;
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
    public static final Enchantment CACTUS_PROTECTION = register("cactus_protection", new ModProtection(Enchantment.Rarity.UNCOMMON,
            ModProtection.Type.CACTUS, EnchantmentCategory.ARMOR, ARMORS));
    public static final Enchantment FALLING_BLOCK_PROTECTION = register("falling_block_protection", new ModProtection(Enchantment.Rarity.UNCOMMON,
            ModProtection.Type.FALLING_BLOCK, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final Enchantment LIGHTNING_BOLT_PROTECTION = register("lightning_bolt_protection", new ModProtection(Enchantment.Rarity.UNCOMMON,
            ModProtection.Type.LIGHTNING_BOLT, EnchantmentCategory.ARMOR, ARMORS));


    // Tools
    public static final Enchantment FARMER = register("farmer", new Farmer());

    // Weapons
    public static final Enchantment  MULTI_ARROWS = register("multi_arrows", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setCheckCompatibility(enchantment -> enchantment != Enchantments.INFINITY_ARROWS)));

    // Treasures
    public static Enchantment SECOND_CHANCE = register("second_chance", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST}).setMinCost(25).setMaxCost(50).setCheckCompatibility(e -> !(e instanceof MendingEnchantment))));

    // Curses
    public static final Enchantment SLEEPLESS_NIGHT = register("sleepless_night", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR, ARMORS).setMinCost(25).setMaxCost(50).setTreasure().setCurse()));
    public static final Enchantment WRONG_BLOCK = register("wrong_block", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, ARMORS).setMaxLevel(5).setTreasure().setCurse()));
    public static final Enchantment SLIPPERY_ROPE = register("slippery_rope", new SlipperyRope());

    //Misc
    public static final Enchantment MAGMA_WALKER = register("magma_walker", new MagmaWalker(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
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
