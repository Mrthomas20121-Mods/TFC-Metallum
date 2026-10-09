package tfc_metallum_modern.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tfc_metallum_modern.TFCMetallumModern;

public class TFCMetallumModernTags {

    public static class Blocks {

        public static final TagKey<Block> NEEDS_ALUMINUM_TOOL = create("needs_aluminum_tool");
        public static final TagKey<Block> NEEDS_BRITANNIUM_TOOL = create("needs_britannium_tool");
        public static final TagKey<Block> NEEDS_COBALT_TOOL = create("needs_cobalt_tool");
        public static final TagKey<Block> NEEDS_ELECTRUM_TOOL = create("needs_electrum_tool");
        public static final TagKey<Block> NEEDS_FERROBORON_TOOL = create("needs_ferroboron_tool");
        public static final TagKey<Block> NEEDS_FLORENTINE_BRONZE_TOOL = create("needs_florentine_bronze_tool");
        public static final TagKey<Block> NEEDS_INVAR_TOOL = create("needs_invar_tool");
        public static final TagKey<Block> NEEDS_OSMIRIDIUM_TOOL = create("needs_osmiridium_tool");
        public static final TagKey<Block> NEEDS_OSMIUM_TOOL = create("needs_osmium_tool");
        public static final TagKey<Block> NEEDS_PEWTER_TOOL = create("needs_pewter_tool");
        public static final TagKey<Block> NEEDS_TITANIUM_TOOL = create("needs_titanium_tool");

        private static TagKey<Block> create(String id)
        {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(TFCMetallumModern.MOD_ID, id));
        }
    }
}
