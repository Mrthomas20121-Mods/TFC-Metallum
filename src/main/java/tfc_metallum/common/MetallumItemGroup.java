package tfc_metallum.common;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.MetallumMetal;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

public class MetallumItemGroup extends CreativeModeTab {

    public static final CreativeModeTab METAL = new MetallumItemGroup("metals", () -> new ItemStack((MetallumItems.METAL_ITEMS.get(MetallumMetal.ALUMINUM)).get(MetallumMetal.ItemType.INGOT).get()));
    public static final CreativeModeTab ORES = new MetallumItemGroup("ores", () -> new ItemStack((TFCItems.GRADED_ORES.get(Ore.NATIVE_COPPER).get(Ore.Grade.NORMAL)).get()));

    private final Lazy<ItemStack> iconStack;

    private MetallumItemGroup(String label, Supplier<ItemStack> iconSupplier) {
        super("tfc." + label);
        this.iconStack = Lazy.of(() -> FoodCapability.setStackNonDecaying(iconSupplier.get()));
    }

    @Nonnull
    public ItemStack makeIcon() {
        return this.iconStack.get();
    }
}
