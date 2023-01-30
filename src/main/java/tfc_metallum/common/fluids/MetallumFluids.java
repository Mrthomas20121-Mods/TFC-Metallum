package tfc_metallum.common.fluids;

import net.dries007.tfc.common.fluids.FlowingFluidRegistryObject;
import net.dries007.tfc.common.fluids.FluidType;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tfc_metallum.TFCMetallum;
import tfc_metallum.common.blocks.MetallumBlocks;
import tfc_metallum.common.items.MetallumItems;
import tfc_metallum.util.MetallumMetal;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.dries007.tfc.common.fluids.TFCFluids.ALPHA_MASK;

public class MetallumFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TFCMetallum.mod_id);

    public static final Map<MetallumMetal, FlowingFluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(MetallumMetal.class, metal -> register(
            "metal/" + metal.getSerializedName(),
            "metal/flowing_" + metal.getSerializedName(),
            properties -> properties.block(MetallumBlocks.METAL_FLUIDS.get(metal)).bucket(MetallumItems.METAL_FLUID_BUCKETS.get(metal)).explosionResistance(100),
            FluidAttributes.builder(new ResourceLocation("tfc_metallum:block/metal/fluid/"+metal.getSerializedName()+"_still"), new ResourceLocation("tfc_metallum:block/metal/fluid/"+metal.getSerializedName()+"_flow"))
                    .translationKey("fluid.tfc_metallum.metal." + metal.getSerializedName())
                    .rarity(metal.getRarity())
                    .luminosity(15)
                    .density(3000)
                    .viscosity(6000)
                    .temperature(1300)
                    .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    private static <F extends FlowingFluid> FlowingFluidRegistryObject<F> register(String sourceName, String flowingName, Consumer<ForgeFlowingFluid.Properties> builder, FluidAttributes.Builder attributes, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        return RegistrationHelpers.registerFluid(FLUIDS, sourceName, flowingName, builder, attributes, sourceFactory, flowingFactory);
    }
}
