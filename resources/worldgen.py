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
        *('tfc:vein/%s' % v for v in ORE_VEINS.keys())
    ])

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
