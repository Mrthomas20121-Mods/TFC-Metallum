package tfc_metallum_modern;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tfc_metallum_modern.client.TFCMetallumModernSounds;
import tfc_metallum_modern.common.TFCMetallumModernCreativeTabs;
import tfc_metallum_modern.common.block.TFCMetallumModernBlockEntities;
import tfc_metallum_modern.common.block.TFCMetallumModernBlocks;
import tfc_metallum_modern.client.MetallumModernClientEventHandler;
import tfc_metallum_modern.common.fluid.TFCMetallumModernFluids;
import tfc_metallum_modern.common.item.TFCMetallumModernItems;

@Mod(TFCMetallumModern.MOD_ID)
public class TFCMetallumModern {

	public static final String MOD_ID = "tfc_metallum_modern";

	public static final Logger LOGGER = LogUtils.getLogger();

	public TFCMetallumModern() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		TFCMetallumModernBlocks.BLOCKS.register(bus);
		TFCMetallumModernItems.ITEMS.register(bus);
		TFCMetallumModernCreativeTabs.CREATIVE_TABS.register(bus);
		TFCMetallumModernSounds.SOUNDS.register(bus);
		TFCMetallumModernFluids.FLUIDS.register(bus);
		TFCMetallumModernFluids.FLUID_TYPES.register(bus);
		TFCMetallumModernBlockEntities.BLOCK_ENTITIES.register(bus);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> MetallumModernClientEventHandler::init);
	}

	public static ResourceLocation identifier(String name) {
		return new ResourceLocation(MOD_ID, name);
	}
	public static ResourceLocation identifierMC(String name) {
		return new ResourceLocation("minecraft", name);
	}
}
