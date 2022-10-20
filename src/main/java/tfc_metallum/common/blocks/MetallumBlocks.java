package tfc_metallum.common.blocks;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.MetallumItemGroup;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.MetallumMetal;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MetallumBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TFCMetallum.mod_id);

    public static final Map<MetallumMetal, Map<MetallumMetal.BlockType, RegistryObject<Block>>> METALS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            Helpers.mapOfKeys(MetallumMetal.BlockType.class, type -> type.has(metal), type ->
                    register(("metal/" + type.name() + "/" + metal.name()), type.create(metal), type.createBlockItem(new Item.Properties().tab(MetallumItemGroup.METAL)))
            )
    );

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab group)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties().tab(group)));
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
