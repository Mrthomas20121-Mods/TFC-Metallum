package tfc_metallum_modern.common.block;

import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.util.TFCMetallumModernMetal;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TFCMetallumModernBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFCMetallumModern.MOD_ID);

    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> ANVIL = register("anvil", AnvilBlockEntity::new, TFCMetallumModernBlocks.METALS.values().stream().map(m -> m.get(TFCMetallumModernMetal.BlockType.ANVIL)).filter(Objects::nonNull));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
