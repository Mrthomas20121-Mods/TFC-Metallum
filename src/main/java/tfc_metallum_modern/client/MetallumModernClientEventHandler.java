package tfc_metallum_modern.client;

import net.dries007.tfc.common.items.TFCFishingRodItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.common.block.TFCMetallumModernBlocks;
import tfc_metallum_modern.common.item.TFCMetallumModernItems;
import tfc_metallum_modern.util.TFCMetallumModernMetal;

public class MetallumModernClientEventHandler {

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(MetallumModernClientEventHandler::clientSetup);
    }

    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {

            for (TFCMetallumModernMetal metal : TFCMetallumModernMetal.values())
            {
                if (metal.hasTools())
                {
                    Item rod = TFCMetallumModernItems.METAL_ITEMS.get(metal).get(TFCMetallumModernMetal.ItemType.FISHING_ROD).get();
                    ItemProperties.register(rod, TFCMetallumModern.identifier("cast"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player player && TFCFishingRodItem.isThisTheHeldRod(player, stack) && player.fishing != null ? 1.0F : 0.0F;
                        }
                    });

                    Item shield = TFCMetallumModernItems.METAL_ITEMS.get(metal).get(TFCMetallumModernMetal.ItemType.SHIELD).get();
                    ItemProperties.register(shield, TFCMetallumModern.identifierMC("blocking"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f;
                        }
                    });

                    Item javelin = TFCMetallumModernItems.METAL_ITEMS.get(metal).get(TFCMetallumModernMetal.ItemType.JAVELIN).get();
                    ItemProperties.register(javelin, TFCMetallumModern.identifier("throwing"), (stack, level, entity, unused) ->
                            entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
                    );
                }
            }

            final RenderType cutout = RenderType.cutout();

            TFCMetallumModernBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));
            TFCMetallumModernBlocks.ORE_DEPOSITS.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));

            TFCMetallumModernBlocks.METALS.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        });
    }
}
