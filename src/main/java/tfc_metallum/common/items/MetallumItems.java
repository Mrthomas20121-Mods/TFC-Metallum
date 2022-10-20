package tfc_metallum.common.items;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.util.MetallumMetal;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class MetallumItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TFCMetallum.mod_id);

    public static final Map<MetallumMetal, Map<MetallumMetal.ItemType, RegistryObject<Item>>> METAL_ITEMS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            Helpers.mapOfKeys(MetallumMetal.ItemType.class, type -> type.has(metal), type ->
                    register("metal/" + type.name() + "/" + metal.name(), () -> type.create(metal))
            )
    );

    private static RegistryObject<Item> register(String name, CreativeModeTab group)
    {
        return register(name, () -> new Item(new Item.Properties().tab(group)));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
