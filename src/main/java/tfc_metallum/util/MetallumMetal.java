package tfc_metallum.util;

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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
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

    ANDESITE_ALLOY(0xC9CABA, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ANTIMONY(0xf4f4f, Rarity.COMMON, Metal.Tier.TIER_I, true, false, false),
    ALNICO(0xD87F36, Rarity.UNCOMMON, Metal.Tier.TIER_III, true, false, false),
    ALUMINUM(0xe3f7f, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.ALUMINUM, MetallumArmorMaterials.ALUMINUM, true, true, true),
    BORON(0x5c545, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.BORON, MetallumArmorMaterials.BORON, true, true, true),
    BERYLLIUM(0xf6ffc, Rarity.UNCOMMON, Metal.Tier.TIER_III, true, false, false),
    BERYLLIUM_COPPER(0xffa67, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.BERYLLIUM_COPPER, MetallumArmorMaterials.BERYLLIUM_COPPER, true, true, true),
    BLUTONIUM(0x58a1c, Rarity.EPIC, Metal.Tier.TIER_VI, true, false, false),
    CONSTANTAN(0xfce7b, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    COBALT(0x59a6e, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.COBALT, MetallumArmorMaterials.COBALT, true, true, true),
    COMPRESSED_IRON(0xbdbdb, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.COMPRESSED_IRON, MetallumArmorMaterials.COMPRESSED_IRON, true, true, true),
    ELECTRUM(0xfbfbb, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    PLATINUM(0x9CB8BF, Rarity.RARE, Metal.Tier.TIER_III, true, false, false),
    ENDERIUM(0x76f0e, Rarity.RARE, Metal.Tier.TIER_V, MetallumTiers.ENDERIUM, MetallumArmorMaterials.ENDERIUM, true, true, true),
    FERROBORON(0x6f737, Rarity.EPIC, Metal.Tier.TIER_VI, MetallumTiers.FERROBORON, MetallumArmorMaterials.FERROBORON, true, true, true),
    FLORENTINE_BRONZE(0xAA924C, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.FLORENTINE_BRONZE, MetallumArmorMaterials.FLORENTINE_BRONZE, true, true, true),
    GRAPHITE(0x81808, Rarity.UNCOMMON, Metal.Tier.TIER_III, true, false, false),
    INVAR(0xdbdcc, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.INVAR, MetallumArmorMaterials.INVAR, true, true, true),
    IRIDIUM(0xe8f9f, Rarity.UNCOMMON, Metal.Tier.TIER_III, true, false, false),
    LEAD(0x72798, Rarity.COMMON, Metal.Tier.TIER_I, true, false, false),
    LUMIUM(0xfff1a, Rarity.RARE, Metal.Tier.TIER_IV, MetallumTiers.LUMIUM, MetallumArmorMaterials.LUMIUM, true, true, true),
    MITHRIL(0x89d9f, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.MITHRIL, MetallumArmorMaterials.MITHRIL, true, true, true),
    NICKEL_SILVER(0xa4a3a, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.NICKEL_SILVER, MetallumArmorMaterials.NICKEL_SILVER, true, true, true),
    OSMIUM(0xddeff, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.OSMIUM, MetallumArmorMaterials.OSMIUM, true, true, true),
    OSMIRIDIUM(0xb2c1c, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.OSMIRIDIUM, MetallumArmorMaterials.OSMIRIDIUM, true, true, true),
    PEWTER(0xb0aba, Rarity.UNCOMMON, Metal.Tier.TIER_II, MetallumTiers.PEWTER, MetallumArmorMaterials.PEWTER, true, true, true),
    PINK_SLIME(0xC279B6, Rarity.UNCOMMON, Metal.Tier.TIER_III, true, false, false),
    REFINED_GLOWSTONE(0xfffdc, Rarity.RARE, Metal.Tier.TIER_IV, MetallumTiers.REFINED_GLOWSTONE, MetallumArmorMaterials.REFINED_GLOWSTONE, true, true, true),
    REFINED_OBSIDIAN(0xbc92d, Rarity.RARE, Metal.Tier.TIER_IV, MetallumTiers.REFINED_OBSIDIAN, MetallumArmorMaterials.REFINED_OBSIDIAN, true, true, true),
    SIGNALUM(0xffc78, Rarity.RARE, Metal.Tier.TIER_IV, MetallumTiers.SIGNALUM, MetallumArmorMaterials.SIGNALUM, true, true, true),
    SOLDER(0x888888, Rarity.UNCOMMON, Metal.Tier.TIER_II, true, false, false),
    THORIUM(0x787b7, Rarity.EPIC, Metal.Tier.TIER_VI, MetallumTiers.THORIUM, MetallumArmorMaterials.THORIUM, true, true, true),
    TITANIUM(0xd8dae, Rarity.EPIC, Metal.Tier.TIER_III, MetallumTiers.TITANIUM, MetallumArmorMaterials.TITANIUM, true, true, true),
    TUNGSTEN(0x97a3b, Rarity.EPIC, Metal.Tier.TIER_III, MetallumTiers.TUNGSTEN, MetallumArmorMaterials.TUNGSTEN, true, true, true),
    TUNGSTEN_STEEL(0x555e6, Rarity.EPIC, Metal.Tier.TIER_IV, MetallumTiers.TUNGSTEN_STEEL, MetallumArmorMaterials.TUNGSTEN_STEEL, true, true, true),
    URANIUM(0xf0f39, Rarity.UNCOMMON, Metal.Tier.TIER_III, MetallumTiers.URANIUM, MetallumArmorMaterials.URANIUM, true, true, true),
    HIGH_CARBON_TUNGSTEN_STEEL(0xf0f39, Rarity.UNCOMMON, Metal.Tier.TIER_IV, false, false, false);

    private final String serializedName;
    private final boolean parts, armor, utility;
    private final Metal.Tier metalTier;
    @Nullable
    private final net.minecraft.world.item.Tier toolTier;
    @Nullable
    private final ArmorMaterial armorTier;
    private final Rarity rarity;
    private final int color;

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

    @Override
    public Supplier<Block> getFullBlock() {
        return null;
    }

    @Override
    public MapColor mapColor() {
        return null;
    }

    private enum Type {
        DEFAULT((metal) -> true),
        PART(MetallumMetal::hasParts),
        TOOL(MetallumMetal::hasTools),
        ARMOR(MetallumMetal::hasArmor),
        UTILITY(MetallumMetal::hasUtilities);

        private final Predicate<MetallumMetal> predicate;

        Type(Predicate<MetallumMetal> predicate) {
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
            return new JavelinItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -2.2F, 2f, properties(), metal.getSerializedName()); //todo attackspeed is new
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
            return new ArmorItem(metal.armorTier(), ArmorItem.Type.HELMET, properties());
        }),
        UNFINISHED_CHESTPLATE(Type.ARMOR, false),
        CHESTPLATE(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), ArmorItem.Type.CHESTPLATE, properties());
        }),
        UNFINISHED_GREAVES(Type.ARMOR, false),
        GREAVES(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), ArmorItem.Type.LEGGINGS, properties());
        }),
        UNFINISHED_BOOTS(Type.ARMOR, false),
        BOOTS(Type.ARMOR, (metal) -> {
            return new ArmorItem(metal.armorTier(), ArmorItem.Type.BOOTS, properties());
        }),
        SHIELD(Type.TOOL, (metal) -> {
            return new TFCShieldItem(metal.toolTier(), properties());
        });

        private final Function<RegistryMetal, Item> itemFactory;
        private final Type type;
        private final boolean mold;

        public static Item.Properties properties() {
            return (new Item.Properties());//todo .tab(MetallumItemGroup.METAL);
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
            return this.itemFactory.apply(metal);
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
            return new AnvilBlock(ExtendedProperties.of().mapColor(MapColor.METAL).noOcclusion().sound(SoundType.METAL).strength(10.0F, 10.0F).requiresCorrectToolForDrops().blockEntity(TFCBlockEntities.ANVIL), metal.metalTier());
        }),
        CHAIN(Type.UTILITY, (metal) -> {
            return new TFCChainBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN));
        }),
        LAMP(Type.UTILITY, (metal) -> {
            return new LampBlock(ExtendedProperties.of(MapColor.METAL).noOcclusion().sound(SoundType.LANTERN).strength(4.0F, 10.0F).randomTicks().lightLevel((state) -> {
                return (Boolean)state.getValue(LampBlock.LIT) ? 15 : 0;
            }).blockEntity(TFCBlockEntities.LAMP));
        }, (block, properties) -> {
            return new LampBlockItem(block, properties);
        }),
        TRAPDOOR(Type.UTILITY, (metal) -> {
            return new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(TFCBlocks::never), BlockSetType.IRON);
        });

        private final Function<RegistryMetal, Block> blockFactory;
        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final Type type;

        BlockType(Type type, Function<RegistryMetal, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.type = type;
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
        }

        BlockType(Type type, Function<RegistryMetal, Block> blockFactory) {
            this(type, blockFactory, BlockItem::new);
        }

        public Supplier<Block> create(RegistryMetal metal) {
            return () -> (Block)this.blockFactory.apply(metal);
        }

        public Function<Block, BlockItem> createBlockItem(Item.Properties properties) {
            return (block) -> (BlockItem)this.blockItemFactory.apply(block, properties);
        }

        public boolean has(MetallumMetal metal) {
            return this.type.hasType(metal);
        }
    }
}
