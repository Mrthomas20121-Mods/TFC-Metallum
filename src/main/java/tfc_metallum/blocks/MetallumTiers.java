package tfc_metallum.blocks;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.ToolTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import tfc_metallum.MetallumTags;
import tfc_metallum.TFCMetallum;

import javax.annotation.Nullable;
import java.util.List;

public final class MetallumTiers {

    public static final Tier ALUMINUM = register("aluminum", List.of(TFCTiers.BLACK_BRONZE, TFCTiers.BRONZE, TFCTiers.BISMUTH_BRONZE), List.of(TFCTiers.WROUGHT_IRON), MetallumTags.Blocks.NEEDS_ALUMINUM_TOOLS, 2, 880, 8f, 4f, 32);
    public static final Tier MITHRIL = register("mithril", ALUMINUM, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_MITHRIL_TOOLS, 2, 1600, 10f, 4.25f, 12);
    public static final Tier INVAR = register("invar", MITHRIL, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_INVAR_TOOLS, 2, 4500, 12f, 5.25f, 14);
    public static final Tier COBALT = register("cobalt", INVAR, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_COBALT_TOOLS, 2, 2200, 13f, 4.75f, 13);
    public static final Tier OSMIUM = register("osmium", TFCTiers.WROUGHT_IRON, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_OSMIUM_TOOLS, 3, 5000, 17f, 7f, 15);
    public static final Tier PEWTER = register("pewter", OSMIUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_PEWTER_TOOLS, 4, 6200, 15f, 5.9f, 28);
    public static final Tier SIGNALUM = register("signalum", PEWTER, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_SIGNALUM_TOOLS, 4, 5900, 16f, 7f, 12);
    public static final Tier LUMIUM = register("lumium", SIGNALUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_LUMIUM_TOOLS, 4, 6500, 16f, 7f, 13);
    public static final Tier ENDERIUM = register("enderium", TFCTiers.STEEL, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_ENDERIUM_TOOLS, 5, 7900, 19f, 10f, 10);
    public static final Tier TITANIUM = register("titanium", ENDERIUM, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_TITANIUM_TOOLS, 5, 3900, 17f, 8f, 20);
    public static final Tier TUNGSTEN = register("tungsten", TITANIUM, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_TUNGSTEN_TOOLS, 5, 6600, 18f, 9.9f, 18);
    public static final Tier TUNGSTEN_STEEL = register("tungsten_steel", List.of(TFCTiers.RED_STEEL, TFCTiers.BLUE_STEEL), List.of(), TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 6, 9200, 20f, 11.1f, 24);

    private static Tier register(String name, Tier before, @Nullable Tier after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        return register(name, List.of(before), after == null ? List.of() : List.of(after), tag, level, uses, speed, damage, enchantmentValue);
    }

    private static Tier register(String name, List<Object> before, List<Object> after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        final Tier tier = new ToolTier(name, level, uses, speed, damage, enchantmentValue, tag, () -> Ingredient.EMPTY);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) TierSortingRegistry.registerTier(tier, new ResourceLocation(TFCMetallum.mod_id, name), before, after);
        return tier;
    }
}
