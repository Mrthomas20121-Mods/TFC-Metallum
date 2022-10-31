package tfc_metallum.common;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import tfc_metallum.TFCMetallum;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum MetallumArmorMaterials implements ArmorMaterial, PhysicalDamageType.Multiplier {

    ALUMINUM(300, 350, 315, 290, 1, 5, 6, 1, 32, 0f, 0f, 15, 15, 8.25f),
    MITHRIL(320, 370, 335, 310, 1, 6, 6, 1, 12, 0f, 0.5f, 10, 15, 10f),
    NICKEL_SILVER(290, 390, 265, 240, 1, 4, 5, 1, 19, 0f, 0.2f, 12, 10, 8.25f),
    INVAR(350, 410, 365, 340, 1, 5, 7, 1, 14, 0f, 0f, 18, 10, 8.25f),
    COMPRESSED_IRON(450, 500, 538, 380, 1, 4, 5, 2, 15, 0f, 0f, 21, 21, 14.2f),
    COBALT(520, 570, 535, 510, 1, 7, 5, 1, 13, 0f, 0f, 15, 15, 13.25f),
    OSMIRIDIUM(610, 600, 590, 545, 1,  6, 7, 1, 15, 0f, 0f, 17, 17, 17f),
    OSMIUM(610, 600, 590, 545, 1,  6, 7, 1, 15, 0f, 0f, 13, 13, 13f),
    URANIUM(520, 510, 490, 475, 1, 6, 8, 1, 12, 0.5f, 0.5f, 10, 17, 10f),
    BERYLLIUM_COPPER(620, 610, 600, 555, 2, 6, 5, 3, 19, 0.5f, 0f, 13, 13, 13f),
    PEWTER(600, 590, 580, 535, 1, 7, 6, 1, 20, 0.5f, 0f, 17, 10, 8.25f),
    BORON(580, 570, 560, 515, 1, 6, 7, 1, 18, 0.5f, 0f, 22, 7, 10.25f),
    FERROBORON(620, 600, 640, 440, 2, 5, 6, 2, 12, 1f, 0f, 25, 30, 16.5f),
    SIGNALUM(720, 700, 690, 675, 1, 7, 7, 1, 12, 0.5f, 0f, 21, 20, 15f),
    LUMIUM(720, 700, 690, 675, 1, 7, 7, 1, 13, 0.5f, 0f, 20, 21, 15f),
    REFINED_OBSIDIAN(720, 700, 690, 675, 1, 7, 7, 1, 12, 0.5f, 0f, 21, 20, 15f),
    REFINED_GLOWSTONE(720, 700, 690, 675, 1, 7, 7, 1, 13, 0.5f, 0f, 25, 20, 17f),
    ENDERIUM(740, 720, 710, 695, 2, 8, 8, 1, 10, 1f, 0.5f, 40, 40, 25f),
    TITANIUM(610, 600, 590, 510, 1, 4, 6, 1, 10, 1f, 0f, 10, 10, 19f),
    THORIUM(800, 990, 1000, 610, 2, 6, 5, 3, 12, 1f, 0f, 35, 15, 15),
    TUNGSTEN(750, 730, 720, 705, 1, 6, 6, 2, 15, 1f, 0f, 20, 25, 17f),
    TUNGSTEN_STEEL(904, 1040, 1030, 735, 4, 6, 8, 4, 23, 3f, 0.1f, 62.5f, 50f, 62.5f);

    private final ResourceLocation serializedName;
    private final int feetDamage;
    private final int legDamage;
    private final int chestDamage;
    private final int headDamage;
    private final int feetReduction;
    private final int legReduction;
    private final int chestReduction;
    private final int headReduction;
    private final int enchantability;
    private final float toughness;
    private final float knockbackResistance;
    private final float crushingModifier;
    private final float piercingModifier;
    private final float slashingModifier;

    MetallumArmorMaterials(int feetDamage, int legDamage, int chestDamage, int headDamage, int feetReduction, int legReduction, int chestReduction, int headReduction, int enchantability, float toughness, float knockbackResistance, float crushingModifier, float piercingModifier, float slashingModifier)
    {
        this.serializedName = new ResourceLocation(TFCMetallum.mod_id, name().toLowerCase(Locale.ROOT));
        this.feetDamage = feetDamage;
        this.legDamage = legDamage;
        this.chestDamage = chestDamage;
        this.headDamage = headDamage;
        this.feetReduction = feetReduction;
        this.legReduction = legReduction;
        this.chestReduction = chestReduction;
        this.headReduction = headReduction;
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;

        // Since each slot is applied separately, the input values are values for a full set of armor of this type.
        this.crushingModifier = crushingModifier * 0.25f;
        this.piercingModifier = piercingModifier * 0.25f;
        this.slashingModifier = slashingModifier * 0.25f;
    }

    @Override
    public float crushing()
    {
        return crushingModifier;
    }

    @Override
    public float piercing()
    {
        return piercingModifier;
    }

    @Override
    public float slashing()
    {
        return slashingModifier;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot)
    {
        return switch (slot)
                {
                    case FEET -> feetDamage;
                    case LEGS -> legDamage;
                    case CHEST -> chestDamage;
                    case HEAD -> headDamage;
                    default -> 0;
                };
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot)
    {
        return switch (slot)
                {
                    case FEET -> feetReduction;
                    case LEGS -> legReduction;
                    case CHEST -> chestReduction;
                    case HEAD -> headReduction;
                    default -> 0;
                };
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantability;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound()
    {
        return TFCSounds.ARMOR_EQUIP.get(this).get();
    }

    /**
     * Use {@link #getId()} because it doesn't have weird namespaced side effects.
     */
    @Nonnull
    @Override
    @Deprecated
    public String getName()
    {
        // Note that in HumanoidArmorLayer, the result of getName() is used directly in order to infer the armor texture location
        // So, this needs to be properly namespaced despite being a string.
        return serializedName.toString();
    }

    public ResourceLocation getId()
    {
        return serializedName;
    }

    @Override
    public float getToughness()
    {
        return toughness;
    }

    @Override
    public float getKnockbackResistance()
    {
        return knockbackResistance;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.EMPTY;
    }
}
