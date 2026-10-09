package tfc_metallum_modern.common;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.ToolTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;
import tfc_metallum_modern.TFCMetallumModern;

import java.util.List;

public class TFCMetallumModernTiers {

    public static final Tier ALUMINUM = register("aluminum", Tiers.STONE, Tiers.IRON, TFCMetallumModernTags.Blocks.NEEDS_ALUMINUM_TOOL, 1, 600, 5.25f, 3.25f, 8);
    public static final Tier BRITANNIUM = register("britannium", Tiers.IRON, Tiers.DIAMOND, TFCMetallumModernTags.Blocks.NEEDS_BRITANNIUM_TOOL, 2, 1250, 6f, 4.25f, 11);
    public static final Tier FLORENTINE_BRONZE = register("florentine_bronze", Tiers.IRON, Tiers.DIAMOND, TFCMetallumModernTags.Blocks.NEEDS_FLORENTINE_BRONZE_TOOL, 2, 1200, 6.5f, 4.2f, 12);
    public static final Tier PEWTER = register("pewter", Tiers.IRON, Tiers.DIAMOND, TFCMetallumModernTags.Blocks.NEEDS_PEWTER_TOOL, 2, 1200, 6.1f, 4.25f, 10);
    public static final Tier ELECTRUM = register("electrum", Tiers.IRON, Tiers.DIAMOND, TFCMetallumModernTags.Blocks.NEEDS_ELECTRUM_TOOL, 2, 800, 5.25f, 3.55f, 10);
    public static final Tier FERROBORON = register("ferroboron", List.of(BRITANNIUM, ELECTRUM, FLORENTINE_BRONZE, PEWTER), List.of(Tiers.DIAMOND), TFCMetallumModernTags.Blocks.NEEDS_FERROBORON_TOOL, 3, 3000, 8.5f, 5f, 12);
    public static final Tier COBALT = register("cobalt", List.of(BRITANNIUM, ELECTRUM, FLORENTINE_BRONZE, PEWTER), List.of(Tiers.DIAMOND), TFCMetallumModernTags.Blocks.NEEDS_COBALT_TOOL, 3, 2200, 8f, 5f, 12);
    public static final Tier INVAR = register("invar", List.of(BRITANNIUM, ELECTRUM, FLORENTINE_BRONZE, PEWTER), List.of(Tiers.DIAMOND), TFCMetallumModernTags.Blocks.NEEDS_INVAR_TOOL, 3, 2300, 8.2f, 6f, 12);
    public static final Tier OSMIUM = register("osmium", List.of(BRITANNIUM, ELECTRUM, FLORENTINE_BRONZE, PEWTER), List.of(Tiers.DIAMOND), TFCMetallumModernTags.Blocks.NEEDS_OSMIUM_TOOL, 3, 2100, 8f, 4.8f, 12);
    public static final Tier OSMIRIDIUM = register("osmiridium", OSMIUM, Tiers.DIAMOND, TFCMetallumModernTags.Blocks.NEEDS_OSMIRIDIUM_TOOL, 3, 4200, 8f, 4.8f, 12);
    public static final Tier TITANIUM = register("titanium", List.of(TFCTiers.STEEL), List.of(TFCTiers.BLACK_STEEL, Tiers.NETHERITE), TFCMetallumModernTags.Blocks.NEEDS_TITANIUM_TOOL, 5, 4200, 8.2f, 5f, 24);
    public static final Tier PLATINE_STEEL = register("platine_steel", Tiers.NETHERITE, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 5, 6500, 12f, 9f, 22);
    public static final Tier TITAN_STEEL = register("titan_steel", Tiers.NETHERITE, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 5, 6500, 12f, 9f, 22);
    public static final Tier TUNGSTEN_STEEL = register("tungsten_steel", Tiers.NETHERITE, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 5, 6500, 12f, 9f, 22);

    private static Tier register(String name, Tier before, @Nullable Tier after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        return register(name, List.of(before), after == null ? List.of() : List.of(after), tag, level, uses, speed, damage, enchantmentValue);
    }

    private static Tier register(String name, List<Object> before, List<Object> after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        final Tier tier = new ToolTier(name, level, uses, speed, damage, enchantmentValue, tag, () -> Ingredient.EMPTY);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) TierSortingRegistry.registerTier(tier, TFCMetallumModern.identifier(name), before, after);
        return tier;
    }
}
