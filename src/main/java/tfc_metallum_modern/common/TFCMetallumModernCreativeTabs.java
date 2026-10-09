package tfc_metallum_modern.common;

import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.blocks.DecorationBlockRegistryObject;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.common.block.TFCMetallumModernBlocks;
import tfc_metallum_modern.common.item.BloomItemHolder;
import tfc_metallum_modern.common.item.TFCMetallumModernItems;
import tfc_metallum_modern.util.TFCMetallumModernMetal;
import tfc_metallum_modern.util.TFCMetallumModernOre;
import tfc_metallum_modern.util.TFCMetallumModernOreDeposit;

import java.util.Map;
import java.util.function.Supplier;

public class TFCMetallumModernCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TFCMetallumModern.MOD_ID);

    public static final TFCCreativeTabs.CreativeTabHolder ORES = register("ores", () -> new ItemStack(TFCMetallumModernItems.GRADED_ORES.get(TFCMetallumModernOre.BAUXITE).get(Ore.Grade.NORMAL).get()), TFCMetallumModernCreativeTabs::fillOresTab);

    public static final TFCCreativeTabs.CreativeTabHolder METAL = register("metals", () -> new ItemStack(TFCMetallumModernItems.METAL_ITEMS.get(TFCMetallumModernMetal.ALUMINUM).get(TFCMetallumModernMetal.ItemType.INGOT).get()), TFCMetallumModernCreativeTabs::fillMetalTab);

    private static void fillMetalTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out) {
        for (TFCMetallumModernMetal metal : TFCMetallumModernMetal.values()) {
            for (TFCMetallumModernMetal.BlockType type : new TFCMetallumModernMetal.BlockType[]{
                    TFCMetallumModernMetal.BlockType.ANVIL,
                    TFCMetallumModernMetal.BlockType.BLOCK,
                    TFCMetallumModernMetal.BlockType.BLOCK_SLAB,
                    TFCMetallumModernMetal.BlockType.BLOCK_STAIRS,
                    TFCMetallumModernMetal.BlockType.BARS,
                    TFCMetallumModernMetal.BlockType.CHAIN,
                    TFCMetallumModernMetal.BlockType.TRAPDOOR,
                    TFCMetallumModernMetal.BlockType.LAMP,
            }) {
                accept(out, TFCMetallumModernBlocks.METALS, metal, type);
            }

            accept(out, TFCMetallumModernItems.METAL_ITEMS, metal, TFCMetallumModernMetal.ItemType.UNFINISHED_LAMP);

            for (TFCMetallumModernMetal.ItemType itemType : new TFCMetallumModernMetal.ItemType[]{
                    TFCMetallumModernMetal.ItemType.INGOT,
                    TFCMetallumModernMetal.ItemType.DOUBLE_INGOT,
                    TFCMetallumModernMetal.ItemType.SHEET,
                    TFCMetallumModernMetal.ItemType.DOUBLE_SHEET,
                    TFCMetallumModernMetal.ItemType.ROD,

                    TFCMetallumModernMetal.ItemType.TUYERE,

                    TFCMetallumModernMetal.ItemType.PICKAXE,
                    TFCMetallumModernMetal.ItemType.PROPICK,
                    TFCMetallumModernMetal.ItemType.AXE,
                    TFCMetallumModernMetal.ItemType.SHOVEL,
                    TFCMetallumModernMetal.ItemType.HOE,
                    TFCMetallumModernMetal.ItemType.CHISEL,
                    TFCMetallumModernMetal.ItemType.HAMMER,
                    TFCMetallumModernMetal.ItemType.SAW,
                    TFCMetallumModernMetal.ItemType.KNIFE,
                    TFCMetallumModernMetal.ItemType.SCYTHE,
                    TFCMetallumModernMetal.ItemType.JAVELIN,
                    TFCMetallumModernMetal.ItemType.SWORD,
                    TFCMetallumModernMetal.ItemType.MACE,
                    TFCMetallumModernMetal.ItemType.FISHING_ROD,
                    TFCMetallumModernMetal.ItemType.SHEARS,

                    TFCMetallumModernMetal.ItemType.HELMET,
                    TFCMetallumModernMetal.ItemType.CHESTPLATE,
                    TFCMetallumModernMetal.ItemType.GREAVES,
                    TFCMetallumModernMetal.ItemType.BOOTS,

                    TFCMetallumModernMetal.ItemType.SHIELD,
                    TFCMetallumModernMetal.ItemType.HORSE_ARMOR,

                    TFCMetallumModernMetal.ItemType.PICKAXE_HEAD,
                    TFCMetallumModernMetal.ItemType.PROPICK_HEAD,
                    TFCMetallumModernMetal.ItemType.AXE_HEAD,
                    TFCMetallumModernMetal.ItemType.SHOVEL_HEAD,
                    TFCMetallumModernMetal.ItemType.HOE_HEAD,
                    TFCMetallumModernMetal.ItemType.CHISEL_HEAD,
                    TFCMetallumModernMetal.ItemType.HAMMER_HEAD,
                    TFCMetallumModernMetal.ItemType.SAW_BLADE,
                    TFCMetallumModernMetal.ItemType.KNIFE_BLADE,
                    TFCMetallumModernMetal.ItemType.SCYTHE_BLADE,
                    TFCMetallumModernMetal.ItemType.JAVELIN_HEAD,
                    TFCMetallumModernMetal.ItemType.SWORD_BLADE,
                    TFCMetallumModernMetal.ItemType.MACE_HEAD,
                    TFCMetallumModernMetal.ItemType.FISH_HOOK,

                    TFCMetallumModernMetal.ItemType.UNFINISHED_HELMET,
                    TFCMetallumModernMetal.ItemType.UNFINISHED_CHESTPLATE,
                    TFCMetallumModernMetal.ItemType.UNFINISHED_GREAVES,
                    TFCMetallumModernMetal.ItemType.UNFINISHED_BOOTS,
            }) {
                accept(out, TFCMetallumModernItems.METAL_ITEMS, metal, itemType);
            }
        }
    }

    private static void fillOresTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for(TFCMetallumModernMetal metal: TFCMetallumModernMetal.values()) {
            if(metal.hasBloom()) {
                BloomItemHolder holder = TFCMetallumModernItems.BLOOMS.get(metal);
                accept(out, holder.RAW());
                accept(out, holder.REFINE());
            }
        }

        for (TFCMetallumModernOre ore : TFCMetallumModernOre.values())
        {
            if (ore.isGraded())
            {
                accept(out, TFCMetallumModernItems.GRADED_ORES, ore, Ore.Grade.POOR);
                accept(out, TFCMetallumModernBlocks.SMALL_ORES, ore);
                accept(out, TFCMetallumModernItems.GRADED_ORES, ore, Ore.Grade.NORMAL);
                accept(out, TFCMetallumModernItems.GRADED_ORES, ore, Ore.Grade.RICH);
            }
        }
        for (TFCMetallumModernOreDeposit deposit : TFCMetallumModernOreDeposit.values())
        {
            TFCMetallumModernBlocks.ORE_DEPOSITS.values().forEach(map -> accept(out, map, deposit));
        }
        for (TFCMetallumModernOre ore : TFCMetallumModernOre.values())
        {
            if (ore.isGraded())
            {
                TFCMetallumModernBlocks.GRADED_ORES.values().forEach(map -> map.get(ore).values().forEach(reg -> accept(out, reg)));
            }
        }
    }

    private static TFCCreativeTabs.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("tfc_metallum_modern.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new TFCCreativeTabs.CreativeTabHolder(reg, displayItems);
    }

    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        out.accept(reg.get());
    }

    private static void accept(CreativeModeTab.Output out, DecorationBlockRegistryObject decoration)
    {
        out.accept(decoration.stair().get());
        out.accept(decoration.slab().get());
        out.accept(decoration.wall().get());
    }

}
