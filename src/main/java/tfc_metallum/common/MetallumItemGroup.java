package tfc_metallum.common;

import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.blocks.MetallumBlocks;
import tfc_metallum.common.blocks.rock.MetallumOre;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.BloomMetal;
import tfc_metallum.util.MetallumMetal;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TFCMetallum.mod_id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MetallumItemGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TFCMetallum.mod_id);
    public static final TFCCreativeTabs.CreativeTabHolder METAL;
    public static final TFCCreativeTabs.CreativeTabHolder ORES;

    private static void fillMetalTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        for (MetallumMetal metal : MetallumMetal.values()) {
            out.accept(new ItemStack((MetallumItems.METAL_ITEMS.get(metal)).get(MetallumMetal.ItemType.INGOT).get()));
            out.accept(new ItemStack((MetallumBlocks.METALS.get(metal)).get(MetallumMetal.BlockType.ANVIL).get()));
            out.accept(new ItemStack((MetallumBlocks.METALS.get(metal)).get(MetallumMetal.BlockType.LAMP).get()));
            out.accept(new ItemStack((MetallumBlocks.METALS.get(metal)).get(MetallumMetal.BlockType.CHAIN).get()));
            out.accept(new ItemStack((MetallumBlocks.METALS.get(metal)).get(MetallumMetal.BlockType.TRAPDOOR).get()));
        }
    }

    private static void fillOresTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        for (MetallumOre ore : MetallumOre.values()) {
            out.accept(new ItemStack((MetallumItems.GRADED_ORES.get(ore).get(Ore.Grade.POOR)).get()));
            out.accept(new ItemStack((MetallumItems.GRADED_ORES.get(ore).get(Ore.Grade.NORMAL)).get()));
            out.accept(new ItemStack((MetallumItems.GRADED_ORES.get(ore).get(Ore.Grade.RICH)).get()));
            out.accept(new ItemStack((MetallumBlocks.SMALL_ORES.get(ore)).get()));
            out.accept(new ItemStack((MetallumItems.ORES.get(ore).get())));
        }
        for (BloomMetal metal : BloomMetal.values()) {
            out.accept(new ItemStack((MetallumItems.RAW_BLOOM.get(metal)).get()));
            out.accept(new ItemStack((MetallumItems.REFINED_BLOOM.get(metal)).get()));
        }
    }

    static {
        METAL = register("metal", () -> new ItemStack((MetallumItems.METAL_ITEMS.get(MetallumMetal.ALUMINUM)).get(MetallumMetal.ItemType.INGOT).get()), MetallumItemGroup::fillMetalTab);
        ORES = register("rock", () -> new ItemStack((MetallumItems.GRADED_ORES.get(MetallumOre.BAUXITE).get(Ore.Grade.NORMAL)).get()), MetallumItemGroup::fillOresTab);
    }

    private static TFCCreativeTabs.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems) {
        RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(
                name, () -> CreativeModeTab.builder().icon(icon).title(Component.translatable("tfc.creative_tab." + name)).displayItems(displayItems).build());
        return new TFCCreativeTabs.CreativeTabHolder(reg, displayItems);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TFCCreativeTabs.MISC.tab().getKey()) {
            for (MetallumMetal metal : MetallumMetal.values()) {
                event.accept(new ItemStack((MetallumItems.METAL_FLUID_BUCKETS.get(metal)).get()));
            }
        }
        if (event.getTabKey() == TFCCreativeTabs.DECORATIONS.tab().getKey()) {
            event.accept(new ItemStack((MetallumBlocks.BERYLLIUM_COPPER_BELL).get()));
            event.accept(new ItemStack((MetallumBlocks.FLORENTINE_BRONZE_BELL).get()));
            event.accept(new ItemStack((MetallumBlocks.ENDERIUM_BARS).get()));
            event.accept(new ItemStack((MetallumBlocks.TITANIUM_BARS).get()));
            event.accept(new ItemStack((MetallumBlocks.TUNGSTEN_BARS).get()));
            event.accept(new ItemStack((MetallumBlocks.TUNGSTEN_STEEL_BARS).get()));
        }
    }

}
