package tfc_metallum_modern.util;

import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public enum TFCMetallumModernOre  {
    STIBNITE(true),
    BAUXITE(true),
    BORACITE(true),
    COBALTITE(true),
    GALENA(true),
    NATIVE_IRIDIUM(true),
    NATIVE_OSMIUM(true),
    NATIVE_PLATINUM(true),
    RUTILE(true),
    WOLFRAMITE(true),
    URANINITE(true);

    private final boolean graded;

    TFCMetallumModernOre(boolean graded) {
        this.graded = graded;
    }

    public boolean isGraded() {
        return this.graded;
    }

    public Block create(RegistryRock rock) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(rock.category().hardness(6.5F), 10.0F).requiresCorrectToolForDrops();
        return new ExtendedBlock(ExtendedProperties.of(properties).flammable(5, 120));
    }
}
