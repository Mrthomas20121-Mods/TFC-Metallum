package tfc_metallum_modern.common.block;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.MoltenFluidBlock;
import net.dries007.tfc.common.blocks.OreDeposit;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.common.fluid.TFCMetallumModernFluids;
import tfc_metallum_modern.common.item.TFCMetallumModernItems;
import tfc_metallum_modern.util.TFCMetallumModernMetal;
import tfc_metallum_modern.util.TFCMetallumModernOre;
import tfc_metallum_modern.util.TFCMetallumModernOreDeposit;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TFCMetallumModernBlocks {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TFCMetallumModern.MOD_ID);

    public static final Map<TFCMetallumModernMetal, Map<TFCMetallumModernMetal.BlockType, RegistryObject<Block>>> METALS = Helpers.mapOfKeys(TFCMetallumModernMetal.class, metal ->
            Helpers.mapOfKeys(TFCMetallumModernMetal.BlockType.class, type -> type.has(metal), type ->
                    register(type.createName(metal), type.create(metal), type.createBlockItem(new Item.Properties()))
            )
    );

    public static final Map<TFCMetallumModernMetal, RegistryObject<LiquidBlock>> METAL_FLUIDS = Helpers.mapOfKeys(TFCMetallumModernMetal.class, metal ->
            registerNoItem("fluid/metal/" + metal.name(), () -> new MoltenFluidBlock(TFCMetallumModernFluids.METALS.get(metal).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()))
    );

    public static final Map<Rock, Map<TFCMetallumModernOre, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(TFCMetallumModernOre.class, TFCMetallumModernOre::isGraded, ore ->
                    Helpers.mapOfKeys(Ore.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    public static final Map<TFCMetallumModernOre, RegistryObject<Block>> SMALL_ORES = Helpers.mapOfKeys(TFCMetallumModernOre.class, TFCMetallumModernOre::isGraded, type ->
            register(("ore/small_" + type.name()), () -> GroundcoverBlock.looseOre(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.05F, 0.0F).sound(SoundType.NETHER_ORE).noCollission().pushReaction(PushReaction.DESTROY)))
    );
    public static final Map<Rock, Map<TFCMetallumModernOreDeposit, RegistryObject<Block>>> ORE_DEPOSITS = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(TFCMetallumModernOreDeposit.class, ore ->
                    register("deposit/" + ore.name() + "/" + rock.name(), () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.GRAVEL).strength(rock.category().hardness(2.0f)))) // Same hardness as gravel
            )
    );

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(TFCMetallumModernBlocks.BLOCKS, TFCMetallumModernItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
