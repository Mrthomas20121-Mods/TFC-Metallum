package tfc_metallum.util;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCChainBlock;
import net.dries007.tfc.common.blocks.devices.AnvilBlock;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import tfc_metallum.common.MetallumItemGroup;
import tfc_metallum.common.MetallumTiers;
import tfc_metallum.common.MetallumArmorMaterials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum MetallumMetal implements RegistryMetal {

    ALUMINUM(0xCAE3E4, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.ALUMINUM, MetallumArmorMaterials.ALUMINUM, true, true, true),
    ANDESITE_ALLOY(0xC9CABA, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ANTIMONY(0xD6D6DA, Rarity.COMMON, Metal.Tier.TIER_I, true, false, false),
    COBALT(0x7AB7F1, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.COBALT, MetallumArmorMaterials.COBALT,true, true, true),
    CONSTANTAN(0xF19886, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ELECTRUM(0xF3D157, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    INVAR(0xDBDCCF, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.INVAR, MetallumArmorMaterials.INVAR, true, true, true),
    MITHRIL(0x89D9F5, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.MITHRIL, MetallumArmorMaterials.MITHRIL, true, true, true),
    NICKEL_SILVER(0xA4A3A4, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    OSMIUM(0xD0F5FB, Rarity.COMMON, Metal.Tier.TIER_III, MetallumTiers.OSMIUM, MetallumArmorMaterials.OSMIUM, true, true, true),
    PEWTER(0xB0ABA6, Rarity.COMMON, Metal.Tier.TIER_III, MetallumTiers.PEWTER, MetallumArmorMaterials.PEWTER, true, true, true),
    URANIUM(0x62A94A, Rarity.COMMON, Metal.Tier.TIER_III, true, false, false),
    RED_ALLOY(0xE97D7F, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    SIGNALUM(0xD53C27, Rarity.COMMON, Metal.Tier.TIER_IV, MetallumTiers.SIGNALUM, MetallumArmorMaterials.SIGNALUM, true, true, true),
    LUMIUM(0xFFFCA4, Rarity.COMMON, Metal.Tier.TIER_IV, MetallumTiers.LUMIUM, MetallumArmorMaterials.LUMIUM, true, true, true),
    ENDERIUM(0x4AA49B, Rarity.COMMON, Metal.Tier.TIER_V, MetallumTiers.ENDERIUM, MetallumArmorMaterials.ENDERIUM, true, true, true),
    TITANIUM(0xD8DAE1, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.TITANIUM, MetallumArmorMaterials.TITANIUM, true, true, true),
    TUNGSTEN(0x97A3B6, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.TUNGSTEN, MetallumArmorMaterials.TUNGSTEN, true, true, true),
    TUNGSTEN_STEEL(0x555E6D, Rarity.COMMON, Metal.Tier.TIER_VI, MetallumTiers.TUNGSTEN_STEEL, MetallumArmorMaterials.TUNGSTEN_STEEL, true, true, true),
    WEAK_TUNGSTEN_STEEL(0x555E6D, Rarity.COMMON, false, false, false);

    private final String serializedName;
    private final boolean parts, armor, utility;
    private final Metal.Tier metalTier;
    @Nullable
    private final net.minecraft.world.item.Tier toolTier;
    @Nullable
    private final ArmorMaterial armorTier;
    private final Rarity rarity;
    private final int color;

    MetallumMetal(int color, Rarity rarity, boolean parts, boolean armor, boolean utility)
    {
        this(color, rarity, Metal.Tier.TIER_0, null, null, parts, armor, utility);
    }

    MetallumMetal(int color, Rarity rarity, Metal.Tier tier, boolean parts, boolean armor, boolean utility)
    {
        this(color, rarity, tier, null, null, parts, armor, utility);
    }

    MetallumMetal(int color, Rarity rarity, Metal.Tier metalTier, @Nullable net.minecraft.world.item.Tier toolTier, @Nullable ArmorMaterial armorTier, boolean parts, boolean armor, boolean utility)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.metalTier = metalTier;
        this.toolTier = toolTier;
        this.armorTier = armorTier;
        this.rarity = rarity;
        this.color = color;

        this.parts = parts;
        this.armor = armor;
        this.utility = utility;
    }

    @Nonnull
    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public int getColor()
    {
        return color;
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public boolean hasParts()
    {
        return parts;
    }

    public boolean hasArmor()
    {
        return armor;
    }

    public boolean hasTools()
    {
        return toolTier != null;
    }

    public boolean hasUtilities()
    {
        return utility;
    }

    @Nonnull
    @Override
    public net.minecraft.world.item.Tier toolTier()
    {
        return Objects.requireNonNull(toolTier, "Tried to get non-existent tier from " + name());
    }

    @Nonnull
    @Override
    public ArmorMaterial armorTier()
    {
        return Objects.requireNonNull(armorTier, "Tried to get non-existent armor tier from " + name());
    }

    @Nonnull
    @Override
    public Metal.Tier metalTier()
    {
        return metalTier;
    }

    private enum Type {
        DEFAULT((metal) -> {
            return true;
        }),
        PART(MetallumMetal::hasParts),
        TOOL(MetallumMetal::hasTools),
        ARMOR(MetallumMetal::hasArmor),
        UTILITY(MetallumMetal::hasUtilities);

        private final Predicate<MetallumMetal> predicate;

        private Type(Predicate<MetallumMetal> predicate) {
            this.predicate = predicate;
        }

        boolean hasType(MetallumMetal metal) {
            return this.predicate.test(metal);
        }
    }

    public enum ItemType {
        INGOT(Type.DEFAULT, true),
        DOUBLE_INGOT(Type.PART, false),
        SHEET(Type.PART, false),
        DOUBLE_SHEET(Type.PART, false),
        ROD(Type.PART, false),
        TUYERE(Type.TOOL, (metal) -> {
            return new TieredItem(metal.toolTier(), properties());
        }),
        FISH_HOOK(Type.TOOL, false),
        FISHING_ROD(Type.TOOL, (metal) -> {
            return new TFCFishingRodItem(properties().defaultDurability(metal.toolTier().getUses()), metal.toolTier());
        }),
        PICKAXE(Type.TOOL, (metal) -> {
            return new PickaxeItem(metal.toolTier(), (int) ToolItem.calculateVanillaAttackDamage(0.75F, metal.toolTier()), -2.8F, properties());
        }),
        PICKAXE_HEAD(Type.TOOL, true),
        PROPICK(Type.TOOL, (metal) -> {
            return new PropickItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.5F, metal.toolTier()), -2.8F, properties());
        }),
        PROPICK_HEAD(Type.TOOL, true),
        AXE(Type.TOOL, (metal) -> {
            return new AxeItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.5F, metal.toolTier()), -3.1F, properties());
        }),
        AXE_HEAD(Type.TOOL, true),
        SHOVEL(Type.TOOL, (metal) -> {
            return new ShovelItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.875F, metal.toolTier()), -3.0F, properties());
        }),
        SHOVEL_HEAD(Type.TOOL, true),
        HOE(Type.TOOL, (metal) -> {
            return new TFCHoeItem(metal.toolTier(), -1, -2.0F, properties());
        }),
        HOE_HEAD(Type.TOOL, true),
        CHISEL(Type.TOOL, (metal) -> {
            return new ChiselItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.27F, metal.toolTier()), -1.5F, properties());
        }),
        CHISEL_HEAD(Type.TOOL, true),
        HAMMER(Type.TOOL, (metal) -> {
            return new ToolItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -3.0F, TFCTags.Blocks.MINEABLE_WITH_HAMMER, properties());
        }),
        HAMMER_HEAD(Type.TOOL, true),
        SAW(Type.TOOL, (metal) -> {
            return new AxeItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.5F, metal.toolTier()), -3.0F, properties());
        }),
        SAW_BLADE(Type.TOOL, true),
        JAVELIN(Type.TOOL, (metal) -> {
            return new JavelinItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -2.2F, properties(), metal.getSerializedName());
        }),
        JAVELIN_HEAD(Type.TOOL, true),
        SWORD(Type.TOOL, (metal) -> {
            return new SwordItem(metal.toolTier(), (int)ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -2.4F, properties());
        }),
        SWORD_BLADE(Type.TOOL, true),
        MACE(Type.TOOL, (metal) -> {
            return new MaceItem(metal.toolTier(), (int)ToolItem.calculateVanillaAttackDamage(1.3F, metal.toolTier()), -3.0F, properties());
        }),
        MACE_HEAD(Type.TOOL, true),
        KNIFE(Type.TOOL, (metal) -> {
            return new ToolItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.6F, metal.toolTier()), -2.0F, TFCTags.Blocks.MINEABLE_WITH_KNIFE, properties());
        }),
        KNIFE_BLADE(Type.TOOL, true),
        SCYTHE(Type.TOOL, (metal) -> {
            return new ScytheItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.7F, metal.toolTier()), -3.2F, TFCTags.Blocks.MINEABLE_WITH_SCYTHE, properties());
        }),
        SCYTHE_BLADE(Type.TOOL, true),
        SHEARS(Type.TOOL, (metal) -> {
            return new ShearsItem(properties().defaultDurability(metal.toolTier().getUses()));
        }),
        UNFINISHED_HELMET(Type.ARMOR, false),
        HELMET(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), EquipmentSlot.HEAD, properties());
        }),
        UNFINISHED_CHESTPLATE(Type.ARMOR, false),
        CHESTPLATE(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), EquipmentSlot.CHEST, properties());
        }),
        UNFINISHED_GREAVES(Type.ARMOR, false),
        GREAVES(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), EquipmentSlot.LEGS, properties());
        }),
        UNFINISHED_BOOTS(Type.ARMOR, false),
        BOOTS(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), EquipmentSlot.FEET, properties());
        }),
        SHIELD(Type.TOOL, (metal) -> {
            return new TFCShieldItem(metal.toolTier(), properties());
        });

        private final Function<RegistryMetal, Item> itemFactory;
        private final Type type;
        private final boolean mold;

        public static Item.Properties properties() {
            return (new Item.Properties()).tab(MetallumItemGroup.METAL);
        }

        ItemType(Type type, boolean mold) {
            this(type, mold, (metal) -> {
                return new Item(properties());
            });
        }

        ItemType(Type type, Function<RegistryMetal, Item> itemFactory) {
            this(type, false, itemFactory);
        }

        ItemType(Type type, boolean mold, Function<RegistryMetal, Item> itemFactory) {
            this.type = type;
            this.mold = mold;
            this.itemFactory = itemFactory;
        }

        public Item create(RegistryMetal metal) {
            return (Item)this.itemFactory.apply(metal);
        }

        public boolean has(MetallumMetal metal) {
            return this.type.hasType(metal);
        }

        public boolean hasMold() {
            return this.mold;
        }
    }

    public enum BlockType {
        ANVIL(Type.UTILITY, (metal) -> {
            return new AnvilBlock(ExtendedProperties.of(Material.METAL).noOcclusion().sound(SoundType.METAL).strength(10.0F, 10.0F).requiresCorrectToolForDrops().blockEntity(TFCBlockEntities.ANVIL), metal.metalTier());
        }),
        CHAIN(Type.UTILITY, (metal) -> {
            return new TFCChainBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN));
        }),
        LAMP(Type.UTILITY, (metal) -> {
            return new LampBlock(ExtendedProperties.of(Material.METAL).noOcclusion().sound(SoundType.LANTERN).strength(4.0F, 10.0F).randomTicks().lightLevel((state) -> {
                return (Boolean)state.getValue(LampBlock.LIT) ? 15 : 0;
            }).blockEntity(TFCBlockEntities.LAMP));
        }, (block, properties) -> {
            return new LampBlockItem(block, properties);
        }),
        TRAPDOOR(Type.UTILITY, (metal) -> {
            return new TrapDoorBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(TFCBlocks::never));
        });

        private final Function<RegistryMetal, Block> blockFactory;
        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final Type type;

        private BlockType(Type type, Function<RegistryMetal, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.type = type;
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
        }

        private BlockType(Type type, Function<RegistryMetal, Block> blockFactory) {
            this(type, blockFactory, BlockItem::new);
        }

        public Supplier<Block> create(RegistryMetal metal) {
            return () -> {
                return (Block)this.blockFactory.apply(metal);
            };
        }

        public Function<Block, BlockItem> createBlockItem(Item.Properties properties) {
            return (block) -> {
                return (BlockItem)this.blockItemFactory.apply(block, properties);
            };
        }

        public boolean has(MetallumMetal metal) {
            return this.type.hasType(metal);
        }
    }
}
