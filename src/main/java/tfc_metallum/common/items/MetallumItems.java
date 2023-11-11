package tfc_metallum.common.items;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.MetallumItemGroup;
import tfc_metallum.common.blocks.rock.MetallumOre;
import tfc_metallum.common.fluids.MetallumFluids;
import tfc_metallum.util.BloomMetal;
import tfc_metallum.util.MetallumMetal;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static net.dries007.tfc.common.TFCItemGroup.MISC;

public class MetallumItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TFCMetallum.mod_id);

    public static final Map<MetallumMetal, Map<MetallumMetal.ItemType, RegistryObject<Item>>> METAL_ITEMS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            Helpers.mapOfKeys(MetallumMetal.ItemType.class, type -> type.has(metal), type ->
                    register("metal/" + type.name() + "/" + metal.name(), () -> type.create(metal))
            )
    );

    public static final Map<MetallumOre, Map<Ore.Grade, RegistryObject<Item>>> GRADED_ORES = Helpers.mapOfKeys(MetallumOre.class, MetallumOre::isGraded, ore ->
            Helpers.mapOfKeys(Ore.Grade.class, grade ->
                    register("ore/" + grade.name() + '_' + ore.name(), MetallumItemGroup.ORES)
            )
    );

    public static final Map<MetallumOre, RegistryObject<Item>> ORES = Helpers.mapOfKeys(MetallumOre.class, ore -> !ore.isGraded(), ore ->
            register("ore/"+ ore.name(), MetallumItemGroup.ORES)
    );
    public static final Map<MetallumMetal, RegistryObject<BucketItem>> METAL_FLUID_BUCKETS = Helpers.mapOfKeys(MetallumMetal.class, metal ->
            register("bucket/metal/" + metal.name(), () -> new BucketItem(MetallumFluids.METALS.get(metal).source(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(MISC)))
    );

    public static final Map<BloomMetal, RegistryObject<Item>> RAW_BLOOM = Helpers.mapOfKeys(BloomMetal.class, metal ->
            register("raw_"+ metal.name() +"_bloom", MetallumItemGroup.ORES)
    );

    public static final Map<BloomMetal, RegistryObject<Item>> REFINED_BLOOM = Helpers.mapOfKeys(BloomMetal.class, metal ->
            register("refined_"+ metal.name() +"_bloom", MetallumItemGroup.ORES)
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
