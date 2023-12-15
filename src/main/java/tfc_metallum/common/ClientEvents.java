package tfc_metallum.common;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.common.items.TFCFishingRodItem;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.dries007.tfc.client.render.blockentity.TFCBellBlockEntityRenderer;
import tfc_metallum.common.block_entities.MetallumBlockEntities;
import tfc_metallum.common.blocks.MetallumBlocks;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.MetallumMetal;

public class ClientEvents {

    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientEvents::clientSetup);
//        bus.addListener(ClientEvents::onTextureStitch);
        bus.addListener(ClientEvents::registerEntitiesRenderer);
        bus.addListener(ClientEvents::registerLayerDefinitions);
    }

/*    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        final ResourceLocation sheet = event.getAtlas().location();
        if (sheet.equals(RenderHelpers.BLOCKS_ATLAS)) {
            for (MetallumMetal metal : MetallumMetal.values())
            {
                event.addSprite(new ResourceLocation("tfc_metallum:block/metal/full/" + metal.getSerializedName()));
            }
            event.addSprite(Helpers.identifier("entity/bell/beryllium_copper"));
            event.addSprite(Helpers.identifier("entity/bell/florentine_bronze"));
        }
    }*/

    public static void clientSetup(FMLClientSetupEvent event) {

        final RenderType cutout = RenderType.cutout();

        MetallumBlocks.SMALL_ORES.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

        MetallumBlocks.ORES.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));

        MetallumBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));
        for(MetallumMetal metal: MetallumMetal.values()) {
            for(MetallumMetal.BlockType type: MetallumMetal.BlockType.values()) {
                if(type.has(metal)) {
                    ItemBlockRenderTypes.setRenderLayer(MetallumBlocks.METALS.get(metal).get(type).get(), cutout);
                }
            }
        }

        ItemBlockRenderTypes.setRenderLayer(MetallumBlocks.ENDERIUM_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallumBlocks.TITANIUM_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallumBlocks.TUNGSTEN_BARS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(MetallumBlocks.TUNGSTEN_STEEL_BARS.get(), cutout);

        event.enqueueWork(() -> {

            for (MetallumMetal metal : MetallumMetal.values())
            {
                if (metal.hasTools())
                {
                    Item rod = MetallumItems.METAL_ITEMS.get(metal).get(MetallumMetal.ItemType.FISHING_ROD).get();
                    ItemProperties.register(rod, Helpers.identifier("cast"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player player && TFCFishingRodItem.isThisTheHeldRod(player, stack) && player.fishing != null ? 1.0F : 0.0F;
                        }
                    });

                    Item shield = MetallumItems.METAL_ITEMS.get(metal).get(MetallumMetal.ItemType.SHIELD).get();
                    ItemProperties.register(shield, new ResourceLocation("blocking"), (stack, level, entity, unused) -> {
                        if (entity == null)
                        {
                            return 0.0F;
                        }
                        else
                        {
                            return entity instanceof Player && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f;
                        }
                    });

                    Item javelin = MetallumItems.METAL_ITEMS.get(metal).get(MetallumMetal.ItemType.JAVELIN).get();
                    ItemProperties.register(javelin, Helpers.identifier("throwing"), (stack, level, entity, unused) ->
                            entity != null && ((entity.isUsingItem() && entity.getUseItem() == stack) || (entity instanceof Monster monster && monster.isAggressive())) ? 1.0F : 0.0F
                    );
                }
            }
        });
    }

    public static void registerEntitiesRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MetallumBlockEntities.BELL.get(), TFCBellBlockEntityRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RenderHelpers.modelIdentifier("bell_body"), BellRenderer::createBodyLayer);
    }
}
