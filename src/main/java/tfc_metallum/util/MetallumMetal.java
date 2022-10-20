package tfc_metallum.util;

import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import tfc_metallum.blocks.MetallumTiers;
import tfc_metallum.common.MetallumArmorMaterials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;

public enum MetallumMetal implements RegistryMetal {

    ALUMINUM(0xCAE3E4, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.ALUMINUM, MetallumArmorMaterials.ALUMINUM, true, true, true),
    ANDESITE_ALLOY(0xC9CABA, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ANTIMONY(0xD6D6DA, Rarity.COMMON, Metal.Tier.TIER_I, true, false, false),
    COBALT(0x7AB7F1, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.COBALT, MetallumArmorMaterials.COBALT,true, true, true),
    CONSTANTAN(0xF19886, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    ELECTRUM(0xF3D157, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    INVAR(0xDBDCCF, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.INVAR, MetallumArmorMaterials.INVAR, true, true, true),
    MITHRIL(0x89D9F5, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.MITHRIL, MetallumArmorMaterials.MITHRIL, true, true, true),
    NICKEL_SILVER(0xA4A3A4, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    OSMIUM(0xD0F5FB, Rarity.COMMON, Metal.Tier.TIER_III, MetallumTiers.OSMIUM, MetallumArmorMaterials.OSMIUM, true, true, true),
    PEWTER(0xB0ABA6, Rarity.COMMON, Metal.Tier.TIER_III, MetallumTiers.PEWTER, MetallumArmorMaterials.PEWTER, true, true, true),
    URANIUM(0x62A94A, Rarity.COMMON, Metal.Tier.TIER_III, true, false, false),
    RED_ALLOY(0xE97D7F, Rarity.COMMON, Metal.Tier.TIER_II, true, false, false),
    SIGNALUM(0xD53C27, Rarity.COMMON, Metal.Tier.TIER_IV, MetallumTiers.SIGNALUM, MetallumArmorMaterials.SIGNALUM, true, true, true),
    LUMIUM(0xFFFCA4, Rarity.COMMON, Metal.Tier.TIER_IV, MetallumTiers.LUMIUM, MetallumArmorMaterials.LUMIUM, true, true, true),
    ENDERIUM(0x4AA49B, Rarity.COMMON, Metal.Tier.TIER_V, MetallumTiers.ENDERIUM, MetallumArmorMaterials.ENDERIUM, true, true, true),
    TITANIUM(0xD8DAE1, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.TITANIUM, MetallumArmorMaterials.TITANIUM, true, true, true),
    TUNGSTEN(0x97A3B6, Rarity.COMMON, Metal.Tier.TIER_II, MetallumTiers.TUNGSTEN, MetallumArmorMaterials.TUNGSTEN, true, true, true),
    TUNGSTEN_STEEL(0x555E6D, Rarity.COMMON, Metal.Tier.TIER_VI, MetallumTiers.TUNGSTEN_STEEL, MetallumArmorMaterials.TUNGSTEN_STEEL, true, true, true),
    WEAK_TUNGSTEN_STEEL(0x555E6D, Rarity.COMMON, false, false, false);

    private final String serializedName;
    private final boolean parts, armor, utility;
    private final Metal.Tier metalTier;
    @Nullable
    private final net.minecraft.world.item.Tier toolTier;
    @Nullable
    private final ArmorMaterial armorTier;
    private final Rarity rarity;
    private final int color;

    MetallumMetal(int color, Rarity rarity, boolean parts, boolean armor, boolean utility)
    {
        this(color, rarity, Metal.Tier.TIER_0, null, null, parts, armor, utility);
    }

    MetallumMetal(int color, Rarity rarity, Metal.Tier tier, boolean parts, boolean armor, boolean utility)
    {
        this(color, rarity, tier, null, null, parts, armor, utility);
    }

    MetallumMetal(int color, Rarity rarity, Metal.Tier metalTier, @Nullable net.minecraft.world.item.Tier toolTier, @Nullable ArmorMaterial armorTier, boolean parts, boolean armor, boolean utility)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.metalTier = metalTier;
        this.toolTier = toolTier;
        this.armorTier = armorTier;
        this.rarity = rarity;
        this.color = color;

        this.parts = parts;
        this.armor = armor;
        this.utility = utility;
    }

    @Nonnull
    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public int getColor()
    {
        return color;
    }

    public Rarity getRarity()
    {
        return rarity;
    }

    public boolean hasParts()
    {
        return parts;
    }

    public boolean hasArmor()
    {
        return armor;
    }

    public boolean hasTools()
    {
        return toolTier != null;
    }

    public boolean hasUtilities()
    {
        return utility;
    }

    @Nonnull
    @Override
    public net.minecraft.world.item.Tier toolTier()
    {
        return Objects.requireNonNull(toolTier, "Tried to get non-existent tier from " + name());
    }

    @Nonnull
    @Override
    public ArmorMaterial armorTier()
    {
        return Objects.requireNonNull(armorTier, "Tried to get non-existent armor tier from " + name());
    }

    @Nonnull
    @Override
    public Metal.Tier metalTier()
    {
        return metalTier;
    }
}
