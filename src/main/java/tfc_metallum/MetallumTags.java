package tfc_metallum;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MetallumTags {

    public static final class Blocks {

        public static TagKey<Block> NEEDS_ALUMINUM_TOOLS = create("needs_aluminum_tools");

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(TFCMetallum.mod_id, id));
        }
    }
}
