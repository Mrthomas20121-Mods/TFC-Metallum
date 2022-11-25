package tfc_metallum;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MetallumTags {

    public static final class Blocks {

        public static TagKey<Block> NEEDS_ALUMINUM_TOOLS = create("needs_aluminum_tools");
        public static TagKey<Block> NEEDS_MITHRIL_TOOLS = create("needs_mithril_tools");
        public static TagKey<Block> NEEDS_INVAR_TOOLS = create("needs_invar_tools");
        public static TagKey<Block> NEEDS_NICKEL_SILVER_TOOLS = create("needs_nickel_silver_tools");
        public static TagKey<Block> NEEDS_REFINED_OBSIDIAN_TOOLS = create("needs_refined_obsidian_tools");
        public static TagKey<Block> NEEDS_REFINED_GLOWSTONE_TOOLS = create("needs_refined_glowstone_tools");
        public static TagKey<Block> NEEDS_URANIUM_TOOLS = create("needs_uranium_tools");
        public static TagKey<Block> NEEDS_BERYLLIUM_COPPER_TOOLS = create("needs_beryllium_copper_tools");
        public static TagKey<Block> NEEDS_COMPRESSED_IRON_TOOLS = create("needs_compressed_iron_tools");
        public static TagKey<Block> NEEDS_COBALT_TOOLS = create("needs_cobalt_tools");
        public static TagKey<Block> NEEDS_OSMIUM_TOOLS = create("needs_osmium_tools");
        public static TagKey<Block> NEEDS_OSMIRIDIUM_TOOLS = create("needs_osmiridium_tools");
        public static TagKey<Block> NEEDS_PEWTER_TOOLS = create("needs_pewter_tools");
        public static TagKey<Block> NEEDS_BORON_TOOLS = create("needs_boron_tools");
        public static TagKey<Block> NEEDS_FERROBORON_TOOLS = create("needs_ferroboron_tools");
        public static TagKey<Block> NEEDS_SIGNALUM_TOOLS = create("needs_signalum_tools");
        public static TagKey<Block> NEEDS_LUMIUM_TOOLS = create("needs_lumium_tools");
        public static TagKey<Block> NEEDS_ENDERIUM_TOOLS = create("needs_enderium_tools");
        public static TagKey<Block> NEEDS_THORIUM_TOOLS = create("needs_thorium_tools");
        public static TagKey<Block> NEEDS_TITANIUM_TOOLS = create("needs_titanium_tools");
        public static TagKey<Block> NEEDS_TUNGSTEN_TOOLS = create("needs_tungsten_tools");

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(TFCMetallum.mod_id, id));
        }
    }
}
