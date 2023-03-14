package tfc_metallum.common.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBellBlock;
import net.minecraft.resources.ResourceLocation;

public class MetallumBellBlock extends TFCBellBlock {

    public MetallumBellBlock(ExtendedProperties properties, float pitch, String textureLocation) {
        super(properties, pitch, textureLocation);
    }

    public MetallumBellBlock(ExtendedProperties properties, float pitch, ResourceLocation textureLocation) {
        super(properties, pitch, textureLocation);
    }
}
