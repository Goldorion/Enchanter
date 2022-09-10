package net.goldorion.enchanter;

import net.goldorion.enchanter.enchantments.Farmer;
import net.goldorion.enchanter.enchantments.MagmaWalker;
import net.goldorion.enchanter.enchantments.Miner;
import net.goldorion.enchanter.enchantments.ModProtection;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.MendingEnchantment;

public class ModEnchantments {

    public static final EquipmentSlot[] ARMORS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
    public static final EquipmentSlot[] ALL = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST,
            EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    //Protections
    public static Enchantment CACTUS_PROTECTION;
    public static Enchantment FALLING_BLOCK_PROTECTION;
    public static Enchantment LIGHTNING_BOLT_PROTECTION;


    // Tools
    public static Enchantment FARMER;
    public static Enchantment MINER;


    // Weapons
    public static Enchantment MULTI_ARROWS;
    public static Enchantment HYLIAN_SHIELD;


    // Curses
    public static Enchantment SLEEPLESS_NIGHT;
    public static Enchantment WRONG_BLOCK;
    public static Enchantment SLIPPERY_ROPE;
    public static Enchantment SLIPPERY_HOOK;


    //Misc
    public static Enchantment MAGMA_WALKER;


    // Treasures
    public static Enchantment SECOND_CHANCE;
    public static Enchantment UNVANISHABLE;
    public static Enchantment ENDER_FRIEND;

    public static void register() {
        //Protections
        CACTUS_PROTECTION = register("cactus_protection", new ModProtection(Rarity.UNCOMMON,
                ModProtection.Type.CACTUS, EnchantmentCategory.ARMOR, ARMORS));
        FALLING_BLOCK_PROTECTION = register("falling_block_protection", new ModProtection(Rarity.UNCOMMON,
                ModProtection.Type.FALLING_BLOCK, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
        LIGHTNING_BOLT_PROTECTION = register("lightning_bolt_protection", new ModProtection(Rarity.UNCOMMON,
                ModProtection.Type.LIGHTNING_BOLT, EnchantmentCategory.ARMOR, ARMORS));

        //Tools
        FARMER = register("farmer", new Farmer());
        MINER = register("miner", new Miner());
        MULTI_ARROWS = register("multi_arrows", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.BOW, HANDS)
                .setMaxLevel(4).setMinCost(20).setMaxCost(50).setCheckCompatibility(enchantment -> enchantment != Enchantments.INFINITY_ARROWS)));

        //Curses
        SLEEPLESS_NIGHT = register("sleepless_night", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, ARMORS).setMinCost(25).setMaxCost(50).setTreasure().setCurse()));
        WRONG_BLOCK = register("wrong_block", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.ARMOR, ARMORS).setMaxLevel(5).setTreasure().setCurse()));
        SLIPPERY_ROPE = register("slippery_rope", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.BOW, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setTreasure().setCurse()));
        SLIPPERY_HOOK = register("slippery_hook", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.FISHING_ROD, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setTreasure().setCurse()));

        //Misc
        MAGMA_WALKER = register("magma_walker", new MagmaWalker(Rarity.RARE, EquipmentSlot.FEET));

        // Treasures
        HYLIAN_SHIELD = register("hylian_shield", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, HANDS)
                .setMinCost(30).setMaxCost(55).setTreasure().setCheckCompatibility(e -> !(e == Enchantments.MENDING || e == Enchantments.UNBREAKING))
                .setCanEnchant(itemStack -> itemStack.getItem() instanceof ShieldItem)));
        UNVANISHABLE = register("unvanishable", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.VANISHABLE, ALL)
                .setMaxLevel(3).setTreasure()));
        SECOND_CHANCE = register("second_chance", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST})
                .setTreasure().setMinCost(25).setMaxCost(50).setCheckCompatibility(e -> !(e instanceof MendingEnchantment))));
        ENDER_FRIEND = register("ender_friend", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD})
                .setTreasure().setMinCost(25).setMaxCost(50)));
    }

    private static Enchantment register(String registryName, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new ResourceLocation(Enchanter.MODID, registryName), enchantment);
    }

}
