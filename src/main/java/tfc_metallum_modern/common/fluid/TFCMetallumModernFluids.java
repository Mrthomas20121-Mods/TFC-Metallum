package tfc_metallum_modern.common.fluid;

import net.dries007.tfc.common.fluids.*;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tfc_metallum_modern.TFCMetallumModern;
import tfc_metallum_modern.common.block.TFCMetallumModernBlocks;
import tfc_metallum_modern.util.TFCMetallumModernMetal;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class TFCMetallumModernFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TFCMetallumModern.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, TFCMetallumModern.MOD_ID);

    public static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/molten_still");
    public static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/molten_flow");

    /**
     * A mask for fluid color - most fluids should be using this
     */
    public static final int ALPHA_MASK = 0xFF000000;

    /**
     * Fluid instances
     */
    public static final Map<TFCMetallumModernMetal, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(TFCMetallumModernMetal.class, metal -> register(
            "metal/" + metal.getSerializedName(),
            properties -> properties
                    .block(TFCMetallumModernBlocks.METAL_FLUIDS.get(metal))
                    .bucket(TFCItems.FLUID_BUCKETS.get(FluidId.asType(metal)))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.tfc_metallum_modern.metal." + metal.getSerializedName())
                    .rarity(metal.getRarity()),
            new FluidTypeClientProperties(ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    private static FluidType.Properties lavaLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(15)
                .density(3000)
                .viscosity(6000)
                .temperature(1300)
                .canConvertToSource(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(false)
                .canSwim(false)
                .supportsBoating(false)
                .fallDistanceModifier(0);
    }

    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }
}
