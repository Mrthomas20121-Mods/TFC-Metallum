package tfc_metallum_modern.util;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.TFCChainBlock;
import net.dries007.tfc.common.blocks.devices.AnvilBlock;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tfc_metallum_modern.common.TFCMetallumModernArmorMaterials;
import tfc_metallum_modern.common.TFCMetallumModernTiers;
import tfc_metallum_modern.common.block.TFCMetallumModernBlocks;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum TFCMetallumModernMetal implements StringRepresentable, RegistryMetal {

    ALUMINUM(0xFFD5DCE0, MapColor.QUARTZ, Rarity.COMMON, Metal.Tier.TIER_I, TFCMetallumModernTiers.ALUMINUM, TFCMetallumModernArmorMaterials.ALUMINUM, true, true, true),
    ANTIMONY(0xFFDEEAE7, MapColor.QUARTZ, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    BORON(0xFF84888D, MapColor.COLOR_GRAY, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    BRITANNIUM(0xFF94C4DC, MapColor.TERRACOTTA_LIGHT_BLUE, Rarity.COMMON, Metal.Tier.TIER_II, TFCMetallumModernTiers.BRITANNIUM, TFCMetallumModernArmorMaterials.BRITANNIUM, true, true, true),
    COBALT(0xFF29509E, MapColor.COLOR_BLUE, Rarity.COMMON, Metal.Tier.TIER_III, TFCMetallumModernTiers.COBALT, TFCMetallumModernArmorMaterials.COBALT, true, true, true),
    CONSTANTAN(0xFFCC8B39, MapColor.COLOR_ORANGE, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ELECTRUM(0xFFDED2A6, MapColor.COLOR_YELLOW, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    FERROBORON(0xFF393942, MapColor.TERRACOTTA_BLACK, Rarity.COMMON, Metal.Tier.TIER_III, TFCMetallumModernTiers.FERROBORON, TFCMetallumModernArmorMaterials.FERROBORON, true, true, true),
    FLORENTINE_BRONZE(0xFFEEBD78, MapColor.TERRACOTTA_ORANGE, Rarity.COMMON, Metal.Tier.TIER_II, TFCMetallumModernTiers.FLORENTINE_BRONZE, TFCMetallumModernArmorMaterials.FLORENTINE_BRONZE, true, true, true),
    INVAR(0xFF878E8E, MapColor.TERRACOTTA_LIGHT_GRAY, Rarity.COMMON, Metal.Tier.TIER_III, TFCMetallumModernTiers.INVAR, TFCMetallumModernArmorMaterials.INVAR, true, true, true),
    IRIDIUM(0xFFC4EFEA, MapColor.COLOR_LIGHT_BLUE, Rarity.COMMON, Metal.Tier.TIER_III, true, false, false),
    LEAD(0xFF466CC3, MapColor.TERRACOTTA_BLUE, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    OSMIRIDIUM(0xFF66768F, MapColor.TERRACOTTA_GRAY, Rarity.COMMON, Metal.Tier.TIER_III, TFCMetallumModernTiers.OSMIRIDIUM, TFCMetallumModernArmorMaterials.OSMIRIDIUM, true, true, true),
    OSMIUM(0xFFB6C9D5, MapColor.COLOR_LIGHT_GRAY, Rarity.COMMON, Metal.Tier.TIER_III, TFCMetallumModernTiers.OSMIUM, TFCMetallumModernArmorMaterials.OSMIUM, true, true, true),
    PEWTER(0xFFAFA0B2, MapColor.TERRACOTTA_BROWN, Rarity.COMMON, Metal.Tier.TIER_II, TFCMetallumModernTiers.PEWTER, TFCMetallumModernArmorMaterials.PEWTER, true, true, true),
    UNREFINED_PLATINUM(0xFFD0CDE7, MapColor.COLOR_PURPLE, Rarity.UNCOMMON, Metal.Tier.TIER_V, false, false, false),
    PLATINUM(0xFFD0CDE7, MapColor.COLOR_PURPLE, Rarity.UNCOMMON, Metal.Tier.TIER_V, true, false, false),
    PLATINE_STEEL(0xFF48563F, MapColor.COLOR_GREEN, Rarity.EPIC, Metal.Tier.TIER_VI, TFCMetallumModernTiers.PLATINE_STEEL, TFCMetallumModernArmorMaterials.PLATINE_STEEL, true, true, true),
    PURPLE_GOLD(0xFF968BC7, MapColor.COLOR_PURPLE, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    TITAN_STEEL(0xFFD0D691, MapColor.COLOR_LIGHT_GREEN, Rarity.EPIC, Metal.Tier.TIER_VI, TFCMetallumModernTiers.TITAN_STEEL, TFCMetallumModernArmorMaterials.TITAN_STEEL, true, true, true),
    UNREFINED_TITANIUM(0xFF8E5045, MapColor.COLOR_BROWN, Rarity.UNCOMMON, Metal.Tier.TIER_V, false, false, false),
    TITANIUM(0xFF8E5045, MapColor.COLOR_BROWN, Rarity.UNCOMMON, Metal.Tier.TIER_V, TFCMetallumModernTiers.TITANIUM, TFCMetallumModernArmorMaterials.TITANIUM, true, true, true),
    UNREFINED_TUNGSTEN(0xFF7E727F, MapColor.TERRACOTTA_PURPLE, Rarity.UNCOMMON, Metal.Tier.TIER_V, false, false, false),
    TUNGSTEN(0xFF7E727F, MapColor.TERRACOTTA_PURPLE, Rarity.UNCOMMON, Metal.Tier.TIER_V, true, false, false),
    TUNGSTEN_STEEL(0xFF7B586A, MapColor.TERRACOTTA_BLACK, Rarity.EPIC, Metal.Tier.TIER_VI, TFCMetallumModernTiers.TUNGSTEN_STEEL, TFCMetallumModernArmorMaterials.TUNGSTEN_STEEL, true, true, true),
    URANIUM(0xFFCDE0AA, MapColor.COLOR_LIGHT_GREEN, Rarity.COMMON, Metal.Tier.TIER_III, true, false, false),
    WEAK_PLATINE_STEEL(0xFF48563F, MapColor.COLOR_GREEN, Rarity.COMMON),
    WEAK_TITAN_STEEL(0xFFD0D691, MapColor.COLOR_LIGHT_GREEN, Rarity.COMMON),
    WEAK_TUNGSTEN_STEEL(0xFF7B586A, MapColor.TERRACOTTA_BLACK, Rarity.COMMON),
    HIGH_CARBON_PLATINE_STEEL(0xFF567642, MapColor.COLOR_GREEN, Rarity.COMMON),
    HIGH_CARBON_TITAN_STEEL(0xFFFBFF6F, MapColor.COLOR_LIGHT_GREEN, Rarity.COMMON),
    HIGH_CARBON_TUNGSTEN_STEEL(0xFF9E527A, MapColor.TERRACOTTA_BLACK, Rarity.COMMON);

    public static final List<TFCMetallumModernMetal> BLOOM_METALS = List.of(COBALT, IRIDIUM, OSMIUM, PLATINUM, TITANIUM, TUNGSTEN, URANIUM);

    private final String serializedName;
    private final boolean parts;
    private final boolean armor;
    private final boolean utility;
    private final Metal.Tier metalTier;
    private final net.minecraft.world.item.@Nullable Tier toolTier;
    private final @Nullable ArmorMaterial armorTier;
    private final MapColor mapColor;
    private final Rarity rarity;
    private final int color;

    TFCMetallumModernMetal(int color, MapColor mapColor, Rarity rarity) {
        this(color, mapColor, rarity, Metal.Tier.TIER_0, null, null, false, false, false);
    }

    TFCMetallumModernMetal(int color, MapColor mapColor, Rarity rarity, @Nullable Metal.Tier metalTier, boolean parts, boolean armor, boolean utility) {
        this(color, mapColor, rarity, metalTier, null, null, parts, armor, utility);
    }

    TFCMetallumModernMetal(int color, MapColor mapColor, @Nullable Rarity rarity, @Nullable Metal.Tier metalTier, net.minecraft.world.item.Tier toolTier, ArmorMaterial armorTier, boolean parts, boolean armor, boolean utility) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.metalTier = metalTier;
        this.toolTier = toolTier;
        this.armorTier = armorTier;
        this.rarity = rarity;
        this.mapColor = mapColor;
        this.color = color;
        this.parts = parts;
        this.armor = armor;
        this.utility = utility;
    }

    public @NotNull String getSerializedName() {
        return this.serializedName;
    }

    public int getColor() {
        return this.color;
    }

    public @NotNull Rarity getRarity() {
        return this.rarity;
    }

    public boolean hasParts() {
        return this.parts;
    }

    public boolean hasArmor() {
        return this.armor;
    }

    public boolean hasTools() {
        return this.toolTier != null;
    }

    public boolean hasUtilities() {
        return this.utility;
    }

    public boolean hasBloom() {
        return BLOOM_METALS.contains(this);
    }

    public @NotNull net.minecraft.world.item.Tier toolTier() {
        return Objects.requireNonNull(this.toolTier, "Tried to get non-existent tier from " + this.name());
    }

    public @NotNull ArmorMaterial armorTier() {
        return Objects.requireNonNull(this.armorTier, "Tried to get non-existent armor tier from " + this.name());
    }

    public @NotNull Metal.Tier metalTier() {
        return this.metalTier;
    }

    public @NotNull MapColor mapColor() {
        return this.mapColor;
    }

    public @NotNull Supplier<Block> getFullBlock() {
        return TFCMetallumModernBlocks.METALS.get(this).get(BlockType.BLOCK);
    }

    public enum ItemType {
        INGOT(TFCMetallumModernMetal.Type.DEFAULT, true, (metal) -> new IngotItem(properties(metal))),
        DOUBLE_INGOT(TFCMetallumModernMetal.Type.PART, false),
        SHEET(TFCMetallumModernMetal.Type.PART, false),
        DOUBLE_SHEET(TFCMetallumModernMetal.Type.PART, false),
        ROD(TFCMetallumModernMetal.Type.PART, false),
        TUYERE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new TieredItem(metal.toolTier(), properties(metal))),
        FISH_HOOK(TFCMetallumModernMetal.Type.TOOL, false),
        FISHING_ROD(TFCMetallumModernMetal.Type.TOOL, (metal) -> new TFCFishingRodItem(properties(metal).defaultDurability(metal.toolTier().getUses()), metal.toolTier())),
        UNFINISHED_LAMP(TFCMetallumModernMetal.Type.UTILITY, (metal) -> new Item(properties(metal))),
        PICKAXE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new PickaxeItem(metal.toolTier(), (int) ToolItem.calculateVanillaAttackDamage(0.75F, metal.toolTier()), -2.8F, properties(metal))),
        PICKAXE_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        PROPICK(TFCMetallumModernMetal.Type.TOOL, (metal) -> new PropickItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.5F, metal.toolTier()), -2.8F, properties(metal))),
        PROPICK_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        AXE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new AxeItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.5F, metal.toolTier()), -3.1F, properties(metal))),
        AXE_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        SHOVEL(TFCMetallumModernMetal.Type.TOOL, (metal) -> new ShovelItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.875F, metal.toolTier()), -3.0F, properties(metal))),
        SHOVEL_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        HOE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new TFCHoeItem(metal.toolTier(), -1, -2.0F, properties(metal))),
        HOE_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        CHISEL(TFCMetallumModernMetal.Type.TOOL, (metal) -> new ChiselItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.27F, metal.toolTier()), -1.5F, properties(metal))),
        CHISEL_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        HAMMER(TFCMetallumModernMetal.Type.TOOL, (metal) -> new HammerItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -3.0F, properties(metal), metal.getSerializedName())),
        HAMMER_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        SAW(TFCMetallumModernMetal.Type.TOOL, (metal) -> new AxeItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.5F, metal.toolTier()), -3.0F, properties(metal))),
        SAW_BLADE(TFCMetallumModernMetal.Type.TOOL, true),
        JAVELIN(TFCMetallumModernMetal.Type.TOOL, (metal) -> new JavelinItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.7F, metal.toolTier()), 1.5F * metal.toolTier().getAttackDamageBonus(), -2.6F, properties(metal), metal.getSerializedName())),
        JAVELIN_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        SWORD(TFCMetallumModernMetal.Type.TOOL, (metal) -> new SwordItem(metal.toolTier(), (int)ToolItem.calculateVanillaAttackDamage(1.0F, metal.toolTier()), -2.4F, properties(metal))),
        SWORD_BLADE(TFCMetallumModernMetal.Type.TOOL, true),
        MACE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new MaceItem(metal.toolTier(), (int)ToolItem.calculateVanillaAttackDamage(1.3F, metal.toolTier()), -3.0F, properties(metal))),
        MACE_HEAD(TFCMetallumModernMetal.Type.TOOL, true),
        KNIFE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new ToolItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.6F, metal.toolTier()), -2.0F, TFCTags.Blocks.MINEABLE_WITH_KNIFE, properties(metal))),
        KNIFE_BLADE(TFCMetallumModernMetal.Type.TOOL, true),
        SCYTHE(TFCMetallumModernMetal.Type.TOOL, (metal) -> new ScytheItem(metal.toolTier(), ToolItem.calculateVanillaAttackDamage(0.7F, metal.toolTier()), -3.2F, TFCTags.Blocks.MINEABLE_WITH_SCYTHE, properties(metal))),
        SCYTHE_BLADE(TFCMetallumModernMetal.Type.TOOL, true),
        SHEARS(TFCMetallumModernMetal.Type.TOOL, (metal) -> new ShearsItem(properties(metal).defaultDurability(metal.toolTier().getUses()))),
        UNFINISHED_HELMET(TFCMetallumModernMetal.Type.ARMOR, false),
        HELMET(TFCMetallumModernMetal.Type.ARMOR, (metal) -> new ArmorItem(metal.armorTier(), net.minecraft.world.item.ArmorItem.Type.HELMET, properties(metal))),
        UNFINISHED_CHESTPLATE(TFCMetallumModernMetal.Type.ARMOR, false),
        CHESTPLATE(TFCMetallumModernMetal.Type.ARMOR, (metal) -> new ArmorItem(metal.armorTier(), net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, properties(metal))),
        UNFINISHED_GREAVES(TFCMetallumModernMetal.Type.ARMOR, false),
        GREAVES(TFCMetallumModernMetal.Type.ARMOR, (metal) -> new ArmorItem(metal.armorTier(), net.minecraft.world.item.ArmorItem.Type.LEGGINGS, properties(metal))),
        UNFINISHED_BOOTS(TFCMetallumModernMetal.Type.ARMOR, false),
        BOOTS(TFCMetallumModernMetal.Type.ARMOR, (metal) -> new ArmorItem(metal.armorTier(), net.minecraft.world.item.ArmorItem.Type.BOOTS, properties(metal))),
        HORSE_ARMOR(TFCMetallumModernMetal.Type.ARMOR, (metal) -> new HorseArmorItem(Mth.floor((double)metal.armorTier().getDefenseForType(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE) * (double)1.5F), Helpers.identifier("textures/entity/animal/horse_armor/" + metal.getSerializedName() + ".png"), properties(metal))),
        SHIELD(TFCMetallumModernMetal.Type.TOOL, (metal) -> new TFCShieldItem(metal.toolTier(), properties(metal)));

        private final Function<RegistryMetal, Item> itemFactory;
        private final Type type;
        private final boolean mold;

        public static Item.Properties properties(RegistryMetal metal) {
            return (new Item.Properties()).rarity(metal.getRarity());
        }

        ItemType(Type type, boolean mold) {
            this(type, mold, (metal) -> new Item(properties(metal)));
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

        public boolean has(TFCMetallumModernMetal metal) {
            return this.type.hasType(metal);
        }

        public boolean hasMold() {
            return this.mold;
        }
    }

    public enum BlockType {
        ANVIL(Type.UTILITY, (metal) -> new AnvilBlock(ExtendedProperties.of().mapColor(metal.mapColor()).noOcclusion().sound(SoundType.ANVIL).strength(10.0F, 10.0F).requiresCorrectToolForDrops().blockEntity(TFCBlockEntities.ANVIL), metal.metalTier())),
        BLOCK(Type.PART, (metal) -> new Block(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL))),
        BLOCK_SLAB(Type.PART, (metal) -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL))),
        BLOCK_STAIRS(Type.PART, (metal) -> new StairBlock(() -> ((Block)metal.getFullBlock().get()).defaultBlockState(), BlockBehaviour.Properties.of().mapColor(metal.mapColor()).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL))),
        BARS(Type.UTILITY, (metal) -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(6.0F, 7.0F).sound(SoundType.METAL).noOcclusion())),
        CHAIN(Type.UTILITY, (metal) -> new TFCChainBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).lightLevel(TFCBlocks.lavaLoggedBlockEmission()))),
        LAMP(Type.UTILITY, (metal) -> new LampBlock(ExtendedProperties.of().mapColor(metal.mapColor()).noOcclusion().sound(SoundType.LANTERN).strength(4.0F, 10.0F).randomTicks().pushReaction(PushReaction.DESTROY).lightLevel((state) -> (Boolean)state.getValue(LampBlock.LIT) ? 15 : 0).blockEntity(TFCBlockEntities.LAMP)), (block, properties) -> new LampBlockItem(block, properties.stacksTo(1))),
        TRAPDOOR(Type.UTILITY, (metal) -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(metal.mapColor()).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(TFCBlocks::never), BlockSetType.IRON));

        private final Function<RegistryMetal, Block> blockFactory;
        private final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;
        private final Type type;
        private final String serializedName;

        BlockType(Type type, Function<RegistryMetal, Block> blockFactory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory) {
            this.type = type;
            this.blockFactory = blockFactory;
            this.blockItemFactory = blockItemFactory;
            this.serializedName = this.name().toLowerCase(Locale.ROOT);
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

        public boolean has(TFCMetallumModernMetal metal) {
            return this.type.hasType(metal);
        }

        public String createName(RegistryMetal metal) {
            if (this != BLOCK_SLAB && this != BLOCK_STAIRS) {
                String var2 = this.serializedName;
                return "metal/" + var2 + "/" + metal.getSerializedName();
            } else {
                String var10000 = BLOCK.createName(metal);
                return var10000 + (this == BLOCK_SLAB ? "_slab" : "_stairs");
            }
        }
    }

    private enum Type {
        DEFAULT((metal) -> true),
        PART(TFCMetallumModernMetal::hasParts),
        TOOL(TFCMetallumModernMetal::hasTools),
        ARMOR(TFCMetallumModernMetal::hasArmor),
        UTILITY(TFCMetallumModernMetal::hasUtilities);

        private final Predicate<TFCMetallumModernMetal> predicate;

        private Type(Predicate<TFCMetallumModernMetal> predicate) {
            this.predicate = predicate;
        }

        boolean hasType(TFCMetallumModernMetal metal) {
            return this.predicate.test(metal);
        }
    }


}
