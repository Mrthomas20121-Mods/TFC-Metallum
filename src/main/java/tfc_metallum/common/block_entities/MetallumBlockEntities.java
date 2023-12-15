package tfc_metallum.common.block_entities;

import net.dries007.tfc.common.blockentities.TFCBellBlockEntity;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.blocks.MetallumBlocks;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class MetallumBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFCMetallum.mod_id);

    public static final RegistryObject<BlockEntityType<MetallumBellBlockEntity>> BELL = register("bell", MetallumBellBlockEntity::new, Stream.of(MetallumBlocks.FLORENTINE_BRONZE_BELL, MetallumBlocks.BERYLLIUM_COPPER_BELL));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
