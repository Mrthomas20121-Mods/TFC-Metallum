package tfc_metallum;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TFCDecoration.mod_id)
public class TFCMetallum {

	public static final String mod_id = "tfc_metallum";

	public TFCMetallum() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		// TFCDecBlocks.BLOCKS.register(bus);
		// TFCDecItems.ITEMS.register(bus);
	}
}
