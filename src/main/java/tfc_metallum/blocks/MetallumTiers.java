package tfc_metallum.blocks;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.ToolTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import tfc_metallum.MetallumTags;

import javax.annotation.Nullable;
import java.util.List;

public final class MetallumTiers {

    public static final Tier ALUMINUM = register("aluminum", TFCTiers.BLACK_BRONZE, TFCTiers.WROUGHT_IRON, MetallumTags.Blocks.NEEDS_ALUMINUM_TOOLS, 2, 880, 8f, 4f, 16);
    public static final Tier COBALT = ;
    public static final Tier ENDERIUM = ;
    public static final Tier INVAR = ;
    public static final Tier LUMIUM = ;
    public static final Tier MANYULLYN = ;
    public static final Tier MITHRIL = ;
    public static final Tier OSMIUM = ;
    public static final Tier PEWTER = ;
    public static final Tier SIGNALUM = ;
    public static final Tier TITANIUM = ;
    public static final Tier TUNGSTEN = ;
    public static final Tier TUNGSTEN_STEEL = ;

    private static Tier register(String name, Tier before, @Nullable Tier after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        return register(name, List.of(before), after == null ? List.of() : List.of(after), tag, level, uses, speed, damage, enchantmentValue);
    }

    private static Tier register(String name, List<Object> before, List<Object> after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        final Tier tier = new ToolTier(name, level, uses, speed, damage, enchantmentValue, tag, () -> Ingredient.EMPTY);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) TierSortingRegistry.registerTier(tier, Helpers.identifier(name), before, after);
        return tier;
    }
}
