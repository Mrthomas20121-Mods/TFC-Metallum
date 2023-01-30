package tfc_metallum.common.blocks.rock;

import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public enum MetallumOre {
    BAUXITE(true),
    BERTRANDITE(true),
    COBALTITE(true),
    KERNITE(true),
    GALENA(true),
    MONAZITE(true),
    NATIVE_OSMIUM(true),
    NATIVE_IRIDIUM(true),
    NATIVE_PLATINUM(true),
    RUTILE(true),
    STIBNITE(true),
    URANINITE(true),
    WOLFRAMITE(true),
    CERTUS_QUARTZ(false);

    private final boolean grade;

    MetallumOre(boolean grade) {
        this.grade = grade;
    }

    public boolean isGraded() {
        return grade;
    }

    public Block create(RegistryRock rock) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(rock.category().hardness(6.5F), 10.0F).requiresCorrectToolForDrops();
        return new Block(properties);
    }
}
