package tfc_metallum;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tfc_metallum.common.blocks.MetallumBlocks;
import tfc_metallum.common.items.MetallumItems;

@Mod(TFCMetallum.mod_id)
public class TFCMetallum {

	public static final String mod_id = "tfc_metallum";

	public TFCMetallum() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MetallumBlocks.BLOCKS.register(bus);
		MetallumItems.ITEMS.register(bus);
	}
}
