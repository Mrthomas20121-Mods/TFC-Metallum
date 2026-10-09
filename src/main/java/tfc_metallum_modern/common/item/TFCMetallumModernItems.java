package tfc_metallum_modern.common.item;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.util.TFCMetallumModernMetal;
import tfc_metallum_modern.util.TFCMetallumModernOre;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class TFCMetallumModernItems {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TFCMetallumModern.MOD_ID);

    public static final Map<TFCMetallumModernOre, Map<Ore.Grade, RegistryObject<Item>>> GRADED_ORES = Helpers.mapOfKeys(TFCMetallumModernOre.class, TFCMetallumModernOre::isGraded, ore ->
            Helpers.mapOfKeys(Ore.Grade.class, grade ->
                    register("ore/" + grade.name() + '_' + ore.name())
            )
    );

    public static final Map<TFCMetallumModernOre, RegistryObject<Item>> ORE_POWDERS = Helpers.mapOfKeys(TFCMetallumModernOre.class, TFCMetallumModernOre::isGraded, ore -> register("powder/" + ore.name()));

    public static final Map<TFCMetallumModernMetal, Map<TFCMetallumModernMetal.ItemType, RegistryObject<Item>>> METAL_ITEMS = Helpers.mapOfKeys(TFCMetallumModernMetal.class, metal ->
            Helpers.mapOfKeys(TFCMetallumModernMetal.ItemType.class, type -> type.has(metal), type ->
                    register("metal/" + type.name() + "/" + metal.name(), () -> type.create(metal))
            )
    );

    public static final Map<TFCMetallumModernMetal, BloomItemHolder> BLOOMS = Helpers.mapOfKeys(TFCMetallumModernMetal.class, TFCMetallumModernMetal::hasBloom,
            (metal) -> new BloomItemHolder(register("raw_" + metal.name() + "_bloom"), register("refined_" + metal.name() + "_bloom")));

    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
