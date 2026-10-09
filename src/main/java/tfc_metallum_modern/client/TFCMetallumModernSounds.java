package tfc_metallum_modern.client;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.common.TFCMetallumModernArmorMaterials;

import java.util.Map;

public class TFCMetallumModernSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, TFCMetallumModern.MOD_ID);

    public static final Map<TFCMetallumModernArmorMaterials, RegistryObject<SoundEvent>> ARMOR_EQUIP = Helpers.mapOfKeys(TFCMetallumModernArmorMaterials.class, mat -> create("item.armor.equip_" + mat.getId().getPath()));

    private static RegistryObject<SoundEvent> create(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(TFCMetallumModern.identifier(name)));
    }
}
