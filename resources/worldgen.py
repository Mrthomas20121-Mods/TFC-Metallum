from typing import Literal, Union, get_args

from mcresources import ResourceManager, utils
from mcresources.type_definitions import Json, JsonObject, ResourceIdentifier, VerticalAnchor
from constants import *

def generate(rm: ResourceManager):
    for ore in ORE_DEPOSITS:
        configured_placed_feature(rm, '%s_deposit' % ore, 'tfc:soil_disc', {
            'min_radius': 1,
            'max_radius': 3,
            'height': 2,
            'integrity': 0.9,
            'states': [{'replace': 'tfc:rock/gravel/%s' % rock, 'with': 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock)} for rock in ROCKS.keys()]
        }, decorate_chance(12), decorate_square(), decorate_heightmap('ocean_floor_wg'), decorate_biome())

        configured_placed_feature(rm, '%s_deep_deposit' % ore, 'tfc:soil_disc', {
            'min_radius': 3,
            'max_radius': 10,
            'height': 3,
            'integrity': 0.9,
            'states': [{'replace': 'tfc:rock/raw/%s' % rock, 'with': 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock)} for rock in ROCKS.keys()]
        }, decorate_chance(24), decorate_square(), decorate_range(40, 63), decorate_biome())


    rm.placed_feature_tag('in_biome/veins', *[
        *('tfc_metallum_modern:vein/%s' % v for v in ORE_VEINS.keys())
    ])

    for vein_name, vein in ORE_VEINS.items():
        rocks = expand_rocks(vein.rocks)
        ore = ORES[vein.ore]  # standard ore
        if ore.graded:  # graded ore vein
            configured_placed_feature(rm, ('vein', vein_name), vein.vein_type, {
                **vein.config(),
                'random_name': vein_name,
                'blocks': [{
                    'replace': ['tfc:rock/raw/%s' % rock],
                    'with': vein_ore_blocks(vein, rock)
                } for rock in rocks],
                'indicator': {
                    'rarity': vein.indicator_rarity,
                    'depth': 35,
                    'underground_rarity': vein.underground_rarity,
                    'underground_count': vein.underground_count,
                    'blocks': [{
                        'block': 'tfc:ore/small_%s' % vein.ore
                    }]
                },
            })
        else:  # non-graded ore vein (mineral)
            configured_placed_feature(rm, ('vein', vein_name), vein.vein_type, {
                **vein.config(),
                'random_name': vein_name,
                'blocks': [{
                    'replace': ['tfc:rock/raw/%s' % rock],
                    'with': mineral_ore_blocks(vein, rock)
                } for rock in rocks],
            })

Heightmap = Literal['motion_blocking', 'motion_blocking_no_leaves', 'ocean_floor', 'ocean_floor_wg', 'world_surface', 'world_surface_wg']
HeightProviderType = Literal['constant', 'uniform', 'biased_to_bottom', 'very_biased_to_bottom', 'trapezoid', 'weighted_list']

def configured_placed_feature(rm: ResourceManager, name_parts: ResourceIdentifier, feature: Optional[ResourceIdentifier] = None, config: JsonObject = None, *placements: Json):
    res = utils.resource_location(rm.domain, name_parts)
    if feature is None:
        feature = res
    rm.configured_feature(res, feature, config)
    rm.placed_feature(res, res, *placements)

def decorate_square() -> Json:
    return 'minecraft:in_square'

def decorate_range(min_y: VerticalAnchor, max_y: VerticalAnchor, bias: HeightProviderType = 'uniform') -> Json:
    return {
        'type': 'minecraft:height_range',
        'height': height_provider(min_y, max_y, bias)
    }

def decorate_heightmap(heightmap: Heightmap) -> Json:
    assert heightmap in get_args(Heightmap)
    return 'minecraft:heightmap', {'heightmap': heightmap.upper()}

def decorate_chance(rarity_or_probability: Union[int, float]) -> Json:
    return {'type': 'minecraft:rarity_filter', 'chance': round(1 / rarity_or_probability) if isinstance(rarity_or_probability, float) else rarity_or_probability}

def decorate_biome() -> Json:
    return 'tfc:biome'

def height_provider(min_y: VerticalAnchor, max_y: VerticalAnchor, height_type: HeightProviderType = 'uniform') -> Dict[str, Any]:
    assert height_type in get_args(HeightProviderType)
    return {
        'type': height_type,
        'min_inclusive': utils.as_vertical_anchor(min_y),
        'max_inclusive': utils.as_vertical_anchor(max_y)
    }

def vein_ore_blocks(vein: Vein, rock: str) -> List[Dict[str, Any]]:
    poor, normal, rich = vein.grade
    ore_blocks = [{
        'weight': poor,
        'block': 'tfc_metallum_modern:ore/poor_%s/%s' % (vein.ore, rock)
    }, {
        'weight': normal,
        'block': 'tfc_metallum_modern:ore/normal_%s/%s' % (vein.ore, rock)
    }, {
        'weight': rich,
        'block': 'tfc_metallum_modern:ore/rich_%s/%s' % (vein.ore, rock)
    }]
    if vein.deposits:
        ore_blocks.append({
            'weight': 10,
            'block': 'tfc_metallum_modern:deposit/%s/%s' % (vein.ore, rock)
        })
    return ore_blocks


def mineral_ore_blocks(vein: Vein, rock: str) -> List[Dict[str, Any]]:
    return [{'block': 'tfc_metallum_modern:ore/%s/%s' % (vein.ore, rock)}]

def expand_rocks(rocks: list[str]) -> list[str]:
    assert all(r in ROCKS or r in ROCK_CATEGORIES for r in rocks)
    return [
        rock
        for spec in rocks
        for rock in ([spec] if spec in ROCKS else [r for r, d in ROCKS.items() if d.category == spec])
    ]