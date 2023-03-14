package tfc_metallum.common.block_entities;

import net.dries007.tfc.common.blockentities.TFCBellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MetallumBellBlockEntity extends TFCBellBlockEntity {

    public MetallumBellBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return MetallumBlockEntities.BELL.get();
    }
}
