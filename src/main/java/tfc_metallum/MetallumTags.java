package tfc_metallum;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MetallumTags {

    public static final class Blocks {

        public static TagKey<Block> NEEDS_ALUMINUM_TOOLS = create("needs_aluminum_tools");
        public static TagKey<Block> NEEDS_MITHRIL_TOOLS = create("needs_mithril_tools");
        public static TagKey<Block> NEEDS_INVAR_TOOLS = create("needs_invar_tools");
        public static TagKey<Block> NEEDS_COBALT_TOOLS = create("needs_cobalt_tools");
        public static TagKey<Block> NEEDS_OSMIUM_TOOLS = create("needs_osmium_tools");
        public static TagKey<Block> NEEDS_PEWTER_TOOLS = create("needs_pewter_tools");
        public static TagKey<Block> NEEDS_SIGNALUM_TOOLS = create("needs_signalum_tools");
        public static TagKey<Block> NEEDS_LUMIUM_TOOLS = create("needs_lumium_tools");
        public static TagKey<Block> NEEDS_ENDERIUM_TOOLS = create("needs_enderium_tools");
        public static TagKey<Block> NEEDS_TITANIUM_TOOLS = create("needs_titanium_tools");
        public static TagKey<Block> NEEDS_TUNGSTEN_TOOLS = create("needs_tungsten_tools");

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(TFCMetallum.mod_id, id));
        }
    }
}
