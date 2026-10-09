package tfc_metallum_modern.common;

import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.client.TFCMetallumModernSounds;

import java.util.Locale;

public enum TFCMetallumModernArmorMaterials implements ArmorMaterial, PhysicalDamageType.Multiplier {

    ALUMINUM(160, 200, 215, 150, 1, 3, 4, 1, 9, 0f, 0f, 10, 10, 6.25f),
    BRITANNIUM( 285, 300, 336, 262, 1, 4, 4, 1, 9, 0f, 0f, 8.25f, 12.5f, 12.5f),
    FLORENTINE_BRONZE( 250, 288, 311, 240, 1, 4, 4, 1, 9, 0f, 0f, 12.5f, 12.5f, 8.25f),
    PEWTER( 270, 315, 323, 251, 1, 4, 4, 1, 9, 0f, 0f, 15, 10, 8.25f),
    COBALT(429, 495, 528, 370, 1, 4, 5, 2, 12, 0f, 0f, 20, 20, 13.2f),
    FERROBORON(643, 742, 792, 555, 1, 4, 5, 2, 12, 0f, 0f, 20, 20, 15f),
    INVAR(543, 642, 692, 455, 1, 4, 5, 2, 12, 0f, 0f, 20, 20, 13.2f),
    OSMIUM(441, 505, 538, 380, 1, 4, 5, 2, 12, 0f, 0f, 13.2f, 20, 20),
    OSMIRIDIUM(541, 605, 638, 480, 1, 4, 5, 2, 12, 0f, 0f, 13.2f, 20, 20),
    TITANIUM(541, 605, 638, 480, 2, 5, 6, 2, 12, 1f, 0f, 25, 25, 25),
    PLATINE_STEEL(884, 1020, 1010, 715, 3, 6, 8, 3, 23, 4f, 0.1f, 50, 50, 62.5f),
    TITAN_STEEL(860, 960, 1088, 748, 3, 6, 8, 3, 23, 3f, 0.1f, 55, 55, 55),
    TUNGSTEN_STEEL(884, 1020, 1010, 715, 3, 6, 8, 3, 23, 3f, 0.2f, 62.5f, 45, 62.5f);

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

    TFCMetallumModernArmorMaterials(int feetDamage, int legDamage, int chestDamage, int headDamage, int feetReduction, int legReduction, int chestReduction, int headReduction, int enchantability, float toughness, float knockbackResistance, float crushingModifier, float piercingModifier, float slashingModifier)
    {
        this.serializedName = TFCMetallumModern.identifier(name().toLowerCase(Locale.ROOT));
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
    public int getDefenseForType(ArmorItem.Type slot)
    {
        return switch (slot)
        {
            case BOOTS -> feetReduction;
            case LEGGINGS -> legReduction;
            case CHESTPLATE -> chestReduction;
            case HELMET -> headReduction;
        };
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type)
    {
        return switch (type)
        {
            case BOOTS -> feetDamage;
            case LEGGINGS -> legDamage;
            case CHESTPLATE -> chestDamage;
            case HELMET -> headDamage;
        };
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return TFCMetallumModernSounds.ARMOR_EQUIP.get(this).get();
    }

    /**
     * Use {@link #getId()} because it doesn't have weird namespaced side effects.
     */
    @NotNull
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

    @NotNull
    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.EMPTY;
    }
}
