package tfc_metallum;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import tfc_metallum.common.ClientEvents;
import tfc_metallum.common.MetallumItemGroup;
import tfc_metallum.common.block_entities.MetallumBlockEntities;
import tfc_metallum.common.blocks.MetallumBlocks;
import tfc_metallum.common.fluids.MetallumFluids;
import tfc_metallum.common.items.MetallumItems;

@Mod(TFCMetallum.mod_id)
public class TFCMetallum {

	public static final String mod_id = "tfc_metallum";

	public static final Logger LOGGER = LogUtils.getLogger();

	public TFCMetallum() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MetallumBlocks.BLOCKS.register(bus);
		MetallumItems.ITEMS.register(bus);
		MetallumFluids.FLUID_TYPES.register(bus);
		MetallumFluids.FLUIDS.register(bus);
		MetallumBlockEntities.BLOCK_ENTITIES.register(bus);
		MetallumItemGroup.CREATIVE_TABS.register(bus);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEvents.init();
		}
	}
}
