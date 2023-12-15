package tfc_metallum.common.blocks;

import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.TFCBellBlock;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.MetallumItemGroup;
import tfc_metallum.common.block_entities.MetallumBlockEntities;
import tfc_metallum.common.blocks.rock.MetallumOre;
import tfc_metallum.common.fluids.MetallumFluids;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.MetallumMetal;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MetallumBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TFCMetallum.mod_id);

    public static final Map<Rock, Map<MetallumOre, RegistryObject<Block>>> ORES = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(MetallumOre.class, ore -> !ore.isGraded(),  ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );

    public static final Map<Rock, Map<MetallumOre, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(MetallumOre.class, MetallumOre::isGraded, ore ->
                    Helpers.mapOfKeys(Ore.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    public static final Map<MetallumOre, RegistryObject<Block>> SMALL_ORES = Helpers.mapOfKeys(MetallumOre.class, MetallumOre::isGraded, type ->
            register(("ore/small_" + type.name()), () -> GroundcoverBlock.looseOre(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.05F, 0.0F).sound(SoundType.NETHER_ORE).noCollission()))
    );

    public static final Map<MetallumMetal, Map<MetallumMetal.BlockType, RegistryObject<Block>>> METALS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            Helpers.mapOfKeys(MetallumMetal.BlockType.class, type -> type.has(metal), type ->
                    register(("metal/" + type.name() + "/" + metal.name()), type.create(metal), type.createBlockItem(new Item.Properties()))
            )
    );

    public static final RegistryObject<Block> BERYLLIUM_COPPER_BELL = register("beryllium_copper_bell", () -> new TFCBellBlock(ExtendedProperties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL).blockEntity(MetallumBlockEntities.BELL).ticks(BellBlockEntity::serverTick, BellBlockEntity::clientTick), 0.8f, "beryllium_copper"));
    public static final RegistryObject<Block> FLORENTINE_BRONZE_BELL = register("florentine_bronze_bell", () -> new TFCBellBlock(ExtendedProperties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL).blockEntity(MetallumBlockEntities.BELL).ticks(BellBlockEntity::serverTick, BellBlockEntity::clientTick), 0.8f, "florentine_bronze"));
    public static final RegistryObject<Block> ENDERIUM_BARS = register("enderium_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 7.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> TITANIUM_BARS = register("titanium_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 7.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> TUNGSTEN_BARS = register("tungsten_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 7.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryObject<Block> TUNGSTEN_STEEL_BARS = register("tungsten_steel_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 7.0F).sound(SoundType.METAL).noOcclusion()));

    public static final Map<MetallumMetal, RegistryObject<LiquidBlock>> METAL_FLUIDS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            register("metal/fluid/" + metal.name(), () -> new LiquidBlock(MetallumFluids.METALS.get(metal).source(), BlockBehaviour.Properties.of().noCollission().strength(100f).lightLevel(state -> 15)))//todo .noDrops()))
    );

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(BLOCKS, MetallumItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
