package net.goldorion.enchanter;

import net.goldorion.enchanter.enchantments.Farmer;
import net.goldorion.enchanter.enchantments.MagmaWalker;
import net.goldorion.enchanter.enchantments.Miner;
import net.goldorion.enchanter.enchantments.ModProtection;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Enchanter.MODID);

    public static final EquipmentSlot[] ARMORS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
    public static final EquipmentSlot[] ALL = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST,
            EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};


    //Protections
    public static final RegistryObject<Enchantment> CACTUS_PROTECTION = register("cactus_protection", new ModProtection(Rarity.UNCOMMON,
            ModProtection.Type.CACTUS, EnchantmentCategory.ARMOR, ARMORS));
    public static final RegistryObject<Enchantment> FALLING_BLOCK_PROTECTION = register("falling_block_protection", new ModProtection(Rarity.UNCOMMON,
            ModProtection.Type.FALLING_BLOCK, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final RegistryObject<Enchantment> LIGHTNING_BOLT_PROTECTION = register("lightning_bolt_protection", new ModProtection(Rarity.UNCOMMON,
            ModProtection.Type.LIGHTNING_BOLT, EnchantmentCategory.ARMOR, ARMORS));


    // Tools
    public static final RegistryObject<Enchantment> FARMER = register("farmer", new Farmer());
    public static final RegistryObject<Enchantment> MINER = register("miner", new Miner());


    // Weapons
    public static final RegistryObject<Enchantment> MULTI_ARROWS = register("multi_arrows", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.BOW, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setCheckCompatibility(enchantment -> enchantment != Enchantments.INFINITY_ARROWS)));
    public static final RegistryObject<Enchantment> HYLIAN_SHIELD = register("hylian_shield", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, HANDS)
            .setMinCost(30).setMaxCost(55).setTreasure().setCheckCompatibility(e -> !(e == Enchantments.MENDING || e == Enchantments.UNBREAKING))
            .setCanEnchant(itemStack -> itemStack.getItem() instanceof ShieldItem)));


    // Curses
    public static final RegistryObject<Enchantment> SLEEPLESS_NIGHT = register("sleepless_night", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, ARMORS).setMinCost(25).setMaxCost(50).setTreasure().setCurse()));
    public static final RegistryObject<Enchantment> WRONG_BLOCK = register("wrong_block", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.ARMOR, ARMORS).setMaxLevel(5).setTreasure().setTreasure().setCurse()));
    public static final RegistryObject<Enchantment> SLIPPERY_ROPE = register("slippery_rope", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.BOW, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setTreasure().setCurse()));
    public static final RegistryObject<Enchantment> SLIPPERY_HOOK = register("slippery_hook", new GenericEnchantment(new EnchantmentBuilder(Rarity.RARE, EnchantmentCategory.FISHING_ROD, HANDS).setMaxLevel(4).setMinCost(20).setMaxCost(50).setTreasure().setCurse()));


    //Misc
    public static final RegistryObject<Enchantment> MAGMA_WALKER = register("magma_walker", new MagmaWalker(Rarity.RARE, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> UNVANISHABLE = register("unvanishable", new GenericEnchantment(new EnchantmentBuilder(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.VANISHABLE, ALL).setMaxLevel(3).setTreasure()));


    // Treasures
    public static RegistryObject<Enchantment> SECOND_CHANCE = register("second_chance", new GenericEnchantment(new EnchantmentBuilder(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST})
            .setMinCost(25).setMaxCost(50).setCheckCompatibility(e -> !(e instanceof MendingEnchantment))));

    private static RegistryObject<Enchantment> register(String registryName, Enchantment enchantment) {
        return REGISTRY.register(registryName, () -> enchantment);
    }

}
