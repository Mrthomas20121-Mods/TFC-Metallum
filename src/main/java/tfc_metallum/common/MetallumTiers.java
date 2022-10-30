package tfc_metallum.common;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.ToolTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import tfc_metallum.MetallumTags;
import tfc_metallum.TFCMetallum;

import javax.annotation.Nullable;
import java.util.List;

import static net.dries007.tfc.common.TFCTiers.*;

public final class MetallumTiers {

    public static final Tier ALUMINUM = register("aluminum", List.of(BLACK_BRONZE, BRONZE, BISMUTH_BRONZE), List.of(TFCTiers.WROUGHT_IRON), MetallumTags.Blocks.NEEDS_ALUMINUM_TOOLS, 2, 880, 8f, 4f, 32);
    public static final Tier MITHRIL = register("mithril", ALUMINUM, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_MITHRIL_TOOLS, 2, 1600, 10f, 4.25f, 12);
    public static final Tier NICKEL_SILVER = register("nickel_silver", MITHRIL, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_NICKEL_SILVER_TOOLS, 2, 4490, 11.9f, 5.3f, 19);
    public static final Tier INVAR = register("invar", NICKEL_SILVER, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_INVAR_TOOLS, 2, 4500, 12f, 5.25f, 14);
    public static final Tier COMPRESSED_IRON = register("compressed_iron", TFCTiers.WROUGHT_IRON, STEEL, MetallumTags.Blocks.NEEDS_COMPRESSED_IRON_TOOLS, 3, 2500, 8.2f, 4.8f, 15);
    public static final Tier COBALT = register("cobalt", INVAR, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_COBALT_TOOLS, 2, 2200, 13f, 4.75f, 13);
    public static final Tier OSMIUM = register("osmium", TFCTiers.WROUGHT_IRON, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_OSMIUM_TOOLS, 3, 5000, 17f, 7f, 15);
    public static final Tier OSMIRIDIUM = register("osmiridium", OSMIUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_OSMIRIDIUM_TOOLS, 3, 5900, 17.5f, 7.5f, 15);
    public static final Tier BORON = register("boron", OSMIRIDIUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_PEWTER_TOOLS, 3, 6200, 15f, 5.9f, 28);
    public static final Tier URANIUM = register("uranium", BORON, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_URANIUM_TOOLS, 3, 3900, 12f, 5.1f, 12);
    public static final Tier BERYLLIUM_COPPER = register("beryllium_copper", URANIUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_BERYLLIUM_COPPER_TOOLS, 3, 5200, 13.9f, 6.1f, 15);
    public static final Tier PEWTER = register("pewter", BERYLLIUM_COPPER, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_BORON_TOOLS, 3, 6000, 14.1f, 6f, 24);
    public static final Tier FERROBORON = register("steel", TFCTiers.STEEL, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_FERROBORON_TOOLS, 4, 4500, 12.5f, 6.75f, 19);
    public static final Tier SIGNALUM = register("signalum", FERROBORON, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_SIGNALUM_TOOLS, 4, 5900, 16f, 7f, 12);
    public static final Tier LUMIUM = register("lumium", SIGNALUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_LUMIUM_TOOLS, 4, 6500, 16f, 7f, 13);
    public static final Tier REFINED_OBSIDIAN = register("refined_osbidian", LUMIUM, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_REFINED_OBSIDIAN_TOOLS, 4, 5900, 15.9f, 7.1f, 15);
    public static final Tier REFINED_GLOWSTONE = register("refined_glowstone", REFINED_OBSIDIAN, TFCTiers.STEEL, MetallumTags.Blocks.NEEDS_REFINED_GLOWSTONE_TOOLS, 4, 6300, 15.9f, 7.1f, 15);
    public static final Tier ENDERIUM = register("enderium", TFCTiers.STEEL, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_ENDERIUM_TOOLS, 5, 7900, 19f, 10f, 10);
    public static final Tier TITANIUM = register("titanium", ENDERIUM, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_TITANIUM_TOOLS, 5, 3900, 17f, 8f, 20);
    public static final Tier THORIUM = register("thorium", TITANIUM, BLACK_STEEL, MetallumTags.Blocks.NEEDS_THORIUM_TOOLS, 6, 8000, 18f, 10f, 12);
    public static final Tier TUNGSTEN = register("tungsten", THORIUM, TFCTiers.BLACK_STEEL, MetallumTags.Blocks.NEEDS_TUNGSTEN_TOOLS, 5, 6600, 18f, 9.9f, 18);
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
