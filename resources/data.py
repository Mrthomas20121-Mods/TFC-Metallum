from enum import Enum, auto

from mcresources import ResourceManager, utils, loot_tables
from mcresources.type_definitions import ResourceIdentifier

from constants import *
from recipes import fluid_ingredient, heat_recipe

class Size(Enum):
    tiny = auto()
    very_small = auto()
    small = auto()
    normal = auto()
    large = auto()
    very_large = auto()
    huge = auto()


class Weight(Enum):
    very_light = auto()
    light = auto()
    medium = auto()
    heavy = auto()
    very_heavy = auto()

def generate(rm: ResourceManager):
    # === Metals ===
    for metal, metal_data in METALS.items():
        rm.data(('tfc', 'metals', metal), {
            'tier': metal_data.tier,
            'fluid': 'tfc_metallum_modern:metal/%s' % metal,
            'melt_temperature': metal_data.melt_temperature,
            'specific_heat_capacity': metal_data.specific_heat_capacity(),
            'ingots': utils.ingredient('#forge:ingots/%s' % metal),
            'double_ingots': utils.ingredient('#forge:double_ingots/%s' % metal) if 'part' in metal_data.types else None,
            'sheets': utils.ingredient('#forge:sheets/%s' % metal) if 'part' in metal_data.types else None
        })

    for metal, metal_data in METALS.items():
        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            item_name = 'tfc_metallum_modern:metal/block/%s_%s' % (metal, item.replace('block_', '')) if 'block_' in item else 'tfc_metallum_modern:metal/%s/%s' % (item, metal)
            if item_data.type in metal_data.types or item_data.type == 'all':
                item_heat(rm, 'metal/%s_%s' % (metal, item), '#%s/%s' % (item_data.tag, metal) if item_data.tag else item_name, metal_data.ingot_heat_capacity(), metal_data.melt_temperature, mb=item_data.smelt_amount)
    # ores and deposites
    for ore, ore_data in ORES.items():
        if ore_data.metal and ore_data.graded:
            metal_data = METALS[ore_data.metal]
            item_heat(rm, ('ore', ore), ['tfc_metallum_modern:ore/small_%s' % ore, 'tfc_metallum_modern:ore/normal_%s' % ore, 'tfc_metallum_modern:ore/poor_%s' % ore, 'tfc_metallum_modern:ore/rich_%s' % ore], metal_data.ingot_heat_capacity(), int(metal_data.melt_temperature), mb=40)

    for rock in ROCKS.keys():
        for ore in ORE_DEPOSITS:
            panning(rm, 'deposits/%s_%s' % (ore, rock), 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock), ['tfc_metallum_modern:item/pan/%s/%s_full' % (ore, rock), 'tfc_metallum_modern:item/pan/%s/%s_half' % (ore, rock), 'tfc_metallum_modern:item/pan/%s/result' % ore], 'tfc_metallum_modern:panning/deposits/%s_%s' % (ore, rock))
            sluicing(rm, 'deposits/%s_%s' % (ore, rock), 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock), 'tfc_metallum_modern:panning/deposits/%s_%s' % (ore, rock))


def four_ways(model: str) -> List[Dict[str, Any]]:
    return [
        {'model': model, 'y': 90},
        {'model': model},
        {'model': model, 'y': 180},
        {'model': model, 'y': 270}
    ]

def item_size(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, size: Size, weight: Weight):
    rm.data(('tfc', 'item_sizes', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'size': size.name,
        'weight': weight.name
    })


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, heat_capacity: float, melt_temperature: Optional[float] = None, mb: Optional[int] = None, destroy_at: Optional[int] = None):
    if melt_temperature is not None:
        forging_temperature = round(melt_temperature * 0.6)
        welding_temperature = round(melt_temperature * 0.8)
    else:
        forging_temperature = welding_temperature = None
    if mb is not None:
        # Interpret heat capacity as a specific heat capacity - so we need to scale by the mB present. Baseline is 100 mB (an ingot)
        # Higher mB = higher heat capacity = heats and cools slower = consumes proportionally more fuel
        heat_capacity = round(10 * heat_capacity * mb) / 1000
    rm.data(('tfc', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })
    if destroy_at is not None:
        heat_recipe(rm, 'destroy_' + name_parts, ingredient, destroy_at)

def panning(rm: ResourceManager, name_parts: utils.ResourceIdentifier, block: utils.Json, models: List[str], loot_table: str):
    rm.data(('tfc', 'panning', name_parts), {
        'ingredient': block,
        'model_stages': models,
        'loot_table': loot_table
    })


def sluicing(rm: ResourceManager, name_parts: utils.ResourceIdentifier, block: utils.Json, loot_table: str):
    rm.data(('tfc', 'sluicing', name_parts), {
        'ingredient': utils.ingredient(block),
        'loot_table': loot_table
    })

def block_and_item_tag(rm: ResourceManager, name_parts: utils.ResourceIdentifier, *values: utils.ResourceIdentifier, replace: bool = False):
    rm.block_tag(name_parts, *values, replace=replace)
    rm.item_tag(name_parts, *values, replace=replace)