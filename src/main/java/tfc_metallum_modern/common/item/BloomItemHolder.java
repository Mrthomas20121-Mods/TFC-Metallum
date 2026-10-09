package tfc_metallum_modern.common.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public record BloomItemHolder(RegistryObject<Item> RAW, RegistryObject<Item> REFINE) {}
