from enum import Enum

from itertools import repeat
from typing import Union

from mcresources import ResourceManager, RecipeContext, utils
from mcresources.type_definitions import ResourceIdentifier, Json

from constants import *

class Rules(Enum):
    hit_any = 'hit_any'
    hit_not_last = 'hit_not_last'
    hit_last = 'hit_last'
    hit_second_last = 'hit_second_last'
    hit_third_last = 'hit_third_last'
    draw_any = 'draw_any'
    draw_last = 'draw_last'
    draw_not_last = 'draw_not_last'
    draw_second_last = 'draw_second_last'
    draw_third_last = 'draw_third_last'
    punch_any = 'punch_any'
    punch_last = 'punch_last'
    punch_not_last = 'punch_not_last'
    punch_second_last = 'punch_second_last'
    punch_third_last = 'punch_third_last'
    bend_any = 'bend_any'
    bend_last = 'bend_last'
    bend_not_last = 'bend_not_last'
    bend_second_last = 'bend_second_last'
    bend_third_last = 'bend_third_last'
    upset_any = 'upset_any'
    upset_last = 'upset_last'
    upset_not_last = 'upset_not_last'
    upset_second_last = 'upset_second_last'
    upset_third_last = 'upset_third_last'
    shrink_any = 'shrink_any'
    shrink_last = 'shrink_last'
    shrink_not_last = 'shrink_not_last'
    shrink_second_last = 'shrink_second_last'
    shrink_third_last = 'shrink_third_last'

def generate(rm: ResourceManager):

    blast_furnace_recipe(rm, 'platinum', '1 tfc_metallum_modern:metal/oxidized_platinum', '1 tfc_metallum_modern:metal/platinum', '#tfc:flux')
    blast_furnace_recipe(rm, 'titanium', '1 tfc_metallum_modern:metal/oxidized_titanium', '1 tfc_metallum_modern:metal/titanium', '#tfc:flux')
    blast_furnace_recipe(rm, 'tungsten', '1 tfc_metallum_modern:metal/oxidized_tungsten', '1 tfc_metallum_modern:metal/tungsten', '#tfc:flux')

    for metal, metal_data in METALS.items():
            if 'part' in metal_data.types:
                damage_shaped(rm, 'crafting/metal/block/%s' % metal, [' SH', 'SWS', ' S '], {'S': '#forge:sheets/%s' % metal, 'W': '#minecraft:planks', 'H': '#tfc:hammers'}, '8 tfc_metallum_modern:metal/block/%s' % metal)
                craft_decorations(rm, 'crafting/metal/block/%s' % metal, 'tfc_metallum_modern:metal/block/%s' % metal, has_wall=False)
            if 'utility' in metal_data.types:
                rm.crafting_shaped('crafting/metal/anvil/%s' % metal, ['XXX', ' X ', 'XXX'], {'X': '#forge:double_ingots/%s' % metal}, 'tfc_metallum_modern:metal/anvil/%s' % metal).with_advancement('#forge:double_ingots/%s' % metal)
                rm.crafting_shapeless('crafting/metal/lamp/%s' % metal, ('tfc:lamp_glass', 'tfc_metallum_modern:metal/unfinished_lamp/%s' % metal), 'tfc_metallum_modern:metal/lamp/%s' % metal).with_advancement('tfc_metallum_modern:metal/unfinished_lamp/%s' % metal)
                extra_products_shapeless(rm, 'crafting/metal/lamp/%s_uncraft' % metal, ('tfc_metallum_modern:metal/lamp/%s' % metal, ), 'tfc_metallum_modern:metal/unfinished_lamp/%s' % metal, 'tfc:lamp_glass').with_advancement('tfc_metallum_modern:metal/lamp/%s' % metal)
            if 'tool' in metal_data.types:
                for tool in METAL_TOOL_HEADS:
                    suffix = '_blade' if tool in ('knife', 'saw', 'scythe', 'sword') else '_head'
                    advanced_shaped(rm, 'crafting/metal/%s/%s' % (tool, metal), ['X', 'Y'], {'X': 'tfc_metallum_modern:metal/%s%s/%s' % (tool, suffix, metal), 'Y': '#forge:rods/wooden'}, item_stack_provider('tfc_metallum_modern:metal/%s/%s' % (tool, metal), copy_forging=True), (0, 0)).with_advancement('tfc_metallum_modern:metal/%s%s/%s' % (tool, suffix, metal))
                advanced_shaped(rm, 'crafting/metal/fishing_rod/%s' % metal, ['  X', ' XY', 'XZY'], {'X': '#forge:rods/wooden', 'Y': '#forge:string', 'Z': 'tfc_metallum_modern:metal/fish_hook/%s' % metal}, item_stack_provider('tfc_metallum_modern:metal/fishing_rod/%s' % metal, copy_forging=True), (1, 2)).with_advancement('tfc_metallum_modern:metal/fish_hook/%s' % metal)

    for rock in ROCKS:
        for ore, ore_data in ORES.items():
            if ore_data.graded:
                collapse_recipe(rm, 'ore/poor_%s_%s' % (rock, ore), 'tfc_metallum_modern:ore/normal_%s/%s' % (ore, rock), 'tfc_metallum_modern:ore/poor_%s/%s' % (ore, rock))
                collapse_recipe(rm, 'ore/normal_%s_%s' % (rock, ore), 'tfc_metallum_modern:ore/rich_%s/%s' % (ore, rock), 'tfc_metallum_modern:ore/normal_%s/%s' % (ore, rock))

        for deposit in ORE_DEPOSITS:
            rm.block_tag('tfc:can_landslide', 'tfc_metallum_modern:deposit/%s/%s' % (deposit, rock))
            landslide_recipe(rm, '%s_%s_deposit' % (deposit, rock), 'tfc_metallum_modern:deposit/%s/%s' % (deposit, rock), 'tfc_metallum_modern:deposit/%s/%s' % (deposit, rock))

    for ore, ore_data in ORES.items():
        if ore_data.metal and ore_data.graded:
            temp = METALS[ore_data.metal].melt_temperature
            heat_recipe(rm, ('ore', 'small_%s' % ore), 'tfc_metallum_modern:ore/small_%s' % ore, temp, None, '%d tfc_metallum_modern:metal/%s' % (10, ore_data.metal))
            heat_recipe(rm, ('ore', 'poor_%s' % ore), 'tfc_metallum_modern:ore/poor_%s' % ore, temp, None, '%d tfc_metallum_modern:metal/%s' % (15, ore_data.metal))
            heat_recipe(rm, ('ore', 'normal_%s' % ore), 'tfc_metallum_modern:ore/normal_%s' % ore, temp, None, '%d tfc_metallum_modern:metal/%s' % (25, ore_data.metal))
            heat_recipe(rm, ('ore', 'rich_%s' % ore), 'tfc_metallum_modern:ore/rich_%s' % ore, temp, None, '%d tfc_metallum_modern:metal/%s' % (35, ore_data.metal))


    for metal, metal_data in METALS.items():
        melt_metal = metal if metal_data.melt_metal is None else metal_data.melt_metal
        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            if item_data.type == 'all' or item_data.type in metal_data.types:
                item_name = 'tfc_metallum_modern:metal/block/%s_%s' % (metal, item.replace('block_', '')) if 'block_' in item else 'tfc_metallum_modern:metal/%s/%s' % (item, metal)
                heat_recipe(rm, ('metal', '%s_%s' % (metal, item)), item_name, metal_data.melt_temperature, None, '%d tfc_metallum_modern:metal/%s' % (item_data.smelt_amount, melt_metal), use_durability=item_data.durability)

    # Mold, Ceramic Firing
    for tool, tool_data in METAL_ITEMS.items():
        if tool_data.mold:
            heat_recipe(rm, ('%s_mold' % tool), 'tfc:ceramic/unfired_%s_mold' % tool, POTTERY_MELT, 'tfc:ceramic/%s_mold' % tool)

    for alloy_name, alloy_components in ALLOYS.items():
        alloy_recipe(rm, alloy_name, alloy_name, *alloy_components)

    # anvil recipes
    metal = '?'
    
    def item(_variant: str) -> str:
        return 'tfc_metallum_modern:metal/%s/%s' % (_variant, metal)

    def item_tag(namespace: str, _variant: str) -> str:
        return '#%s:%ss/%s' % (namespace, _variant, metal)


    for metal, metal_data in METALS.items():

        # Misc
        if 'part' in metal_data.types:
            anvil_recipe(rm, '%s_sheet' % metal, item_tag('forge', 'double_ingot'), item('sheet'), metal_data.tier, Rules.hit_last, Rules.hit_second_last, Rules.hit_third_last)
            anvil_recipe(rm, '%s_rod' % metal, item_tag('forge', 'ingot'), '2 tfc_metallum_modern:metal/rod/%s' % metal, metal_data.tier, Rules.bend_last, Rules.draw_second_last, Rules.draw_third_last)

        # Tools
        if 'tool' in metal_data.types:
            anvil_recipe(rm, '%s_tuyere' % metal, item_tag('forge', 'double_sheet'), 'tfc_metallum_modern:metal/tuyere/%s' % metal, metal_data.tier, Rules.bend_last, Rules.bend_second_last)
            anvil_recipe(rm, '%s_pickaxe_head' % metal, item_tag('forge', 'ingot'), item('pickaxe_head'), metal_data.tier, Rules.punch_last, Rules.bend_not_last, Rules.draw_not_last, bonus=True)
            anvil_recipe(rm, '%s_shovel_head' % metal, item_tag('forge', 'ingot'), item('shovel_head'), metal_data.tier, Rules.punch_last, Rules.hit_not_last, bonus=True)

            anvil_recipe(rm, '%s_axe_head' % metal, item_tag('forge', 'ingot'), item('axe_head'), metal_data.tier, Rules.punch_last, Rules.hit_second_last, Rules.upset_third_last, bonus=True)
            anvil_recipe(rm, '%s_hoe_head' % metal, item_tag('forge', 'ingot'), item('hoe_head'), metal_data.tier, Rules.punch_last, Rules.hit_not_last, Rules.bend_not_last, bonus=True)
            anvil_recipe(rm, '%s_hammer_head' % metal, item_tag('forge', 'ingot'), item('hammer_head'), metal_data.tier, Rules.punch_last, Rules.shrink_not_last, bonus=True)
            anvil_recipe(rm, '%s_propick_head' % metal, item_tag('forge', 'ingot'), item('propick_head'), metal_data.tier, Rules.punch_last, Rules.draw_not_last, Rules.bend_not_last, bonus=True)
            anvil_recipe(rm, '%s_saw_blade' % metal, item_tag('forge', 'ingot'), item('saw_blade'), metal_data.tier, Rules.hit_last, Rules.hit_second_last, bonus=True)
            anvil_recipe(rm, '%s_sword_blade' % metal, item_tag('forge', 'double_ingot'), item('sword_blade'), metal_data.tier, Rules.hit_last, Rules.bend_second_last, Rules.bend_third_last, bonus=True)
            anvil_recipe(rm, '%s_mace_head' % metal, item_tag('forge', 'double_ingot'), item('mace_head'), metal_data.tier, Rules.hit_last, Rules.shrink_not_last, Rules.bend_not_last, bonus=True)
            anvil_recipe(rm, '%s_scythe_blade' % metal, item_tag('forge', 'ingot'), item('scythe_blade'), metal_data.tier, Rules.hit_last, Rules.draw_second_last, Rules.bend_third_last, bonus=True)
            anvil_recipe(rm, '%s_knife_blade' % metal, item_tag('forge', 'ingot'), item('knife_blade'), metal_data.tier, Rules.hit_last, Rules.draw_second_last, Rules.draw_third_last, bonus=True)
            anvil_recipe(rm, '%s_javelin_head' % metal, item_tag('forge', 'ingot'), item('javelin_head'), metal_data.tier, Rules.hit_last, Rules.hit_second_last, Rules.draw_third_last, bonus=True)
            anvil_recipe(rm, '%s_chisel_head' % metal, item_tag('forge', 'ingot'), item('chisel_head'), metal_data.tier, Rules.hit_last, Rules.hit_not_last, Rules.draw_not_last, bonus=True)

            anvil_recipe(rm, '%s_shield' % metal, item_tag('forge', 'double_sheet'), item('shield'), metal_data.tier, Rules.upset_last, Rules.bend_second_last, Rules.bend_third_last, bonus=True)
            anvil_recipe(rm, '%s_fish_hook' % metal, item_tag('forge', 'sheet'), item('fish_hook'), metal_data.tier, Rules.draw_not_last, Rules.bend_any, Rules.hit_any, bonus=True)

        # Armor
        if 'armor' in metal_data.types:
            anvil_recipe(rm, '%s_unfinished_helmet' % metal, item_tag('forge', 'double_sheet'), item('unfinished_helmet'), metal_data.tier, Rules.hit_last, Rules.bend_second_last, Rules.bend_third_last)
            anvil_recipe(rm, '%s_unfinished_chestplate' % metal, item_tag('forge', 'double_sheet'), item('unfinished_chestplate'), metal_data.tier, Rules.hit_last, Rules.hit_second_last, Rules.upset_third_last)
            anvil_recipe(rm, '%s_unfinished_greaves' % metal, item_tag('forge', 'double_sheet'), item('unfinished_greaves'), metal_data.tier, Rules.bend_any, Rules.draw_any, Rules.hit_any)
            anvil_recipe(rm, '%s_unfinished_boots' % metal, item_tag('forge', 'sheet'), item('unfinished_boots'), metal_data.tier, Rules.bend_last, Rules.bend_second_last, Rules.shrink_third_last)
            rm.crafting_shaped('crafting/%s_horse_armor' % metal, ['YXY', 'ZZZ'], {'Z': item_tag('forge', 'double_sheet'), 'Y': 'tfc:jute_fiber', 'X': 'minecraft:leather_horse_armor'}, 'tfc_metallum_modern:metal/horse_armor/%s' % metal).with_advancement(item_tag('forge', 'double_sheet'))

        if 'utility' in metal_data.types:
            anvil_recipe(rm, '%s_trapdoor' % metal, item_tag("forge", "sheet"), item('trapdoor'), metal_data.tier, Rules.bend_last, Rules.draw_second_last, Rules.draw_third_last)
            anvil_recipe(rm, '%s_lamp' % metal, item_tag('forge', 'ingot'), item('unfinished_lamp'), metal_data.tier, Rules.bend_last, Rules.bend_second_last, Rules.draw_third_last)
            anvil_recipe(rm, '%s_chain' % metal, item_tag('forge', 'ingot'), '16 tfc_metallum_modern:metal/chain/%s' % metal, metal_data.tier, Rules.hit_any, Rules.hit_any, Rules.draw_last)
            anvil_recipe(rm, '%s_bars' % metal, '#forge:sheets/%s' % metal, '8 tfc_metallum_modern:metal/bars/%s' % metal, 3, Rules.upset_last, Rules.punch_second_last, Rules.punch_third_last)
            anvil_recipe(rm, '%s_bars_double' % metal, '#forge:double_sheets/%s' % metal, '16 tfc_metallum_modern:metal/bars/%s' % metal, 3, Rules.upset_last, Rules.punch_second_last, Rules.punch_third_last)

    hit_x3 = Rules.hit_last, Rules.hit_second_last, Rules.hit_third_last

    for metal in BLOOM_METALS:
        anvil_recipe(rm, 'refined_%s_bloom' % metal, 'tfc_metallum_modern:raw_%s_bloom' % metal, 'tfc_metallum_modern:refined_%s_bloom' % metal, 2, *hit_x3)
        anvil_recipe(rm, '%s_from_bloom' % metal, 'tfc_metallum_modern:refined_%s_bloom' % metal, 'tfc_metallum_modern:metal/ingot/%s' % metal, 2, *hit_x3)

    for metal_in, metal_out in (
        ('high_carbon_platine_steel', 'platine_steel'),
        ('high_carbon_titan_steel', 'titan_steel'),
        ('high_carbon_tungsten_steel', 'tungsten_steel')
    ):
        anvil_recipe(rm, '%s_ingot' % metal_out, 'tfc_metallum_modern:metal/ingot/%s' % metal_in, 'tfc_metallum_modern:metal/ingot/%s' % metal_out, METALS[metal_in].tier, *hit_x3)

    # welding recipes
    for metal, metal_data in METALS.items():
        if 'part' in metal_data.types:
            welding_recipe(rm, '%s_double_ingot' % metal, item_tag('forge', 'ingot'), item_tag('forge', 'ingot'), item('double_ingot'), metal_data.tier - 1)
            welding_recipe(rm, '%s_double_sheet' % metal, item_tag('forge', 'sheet'), item_tag('forge', 'sheet'), item('double_sheet'), metal_data.tier - 1)

        if 'armor' in metal_data.types:
            welding_recipe(rm, '%s_helmet' % metal, item('unfinished_helmet'), item('sheet'), item('helmet'), metal_data.tier - 1)
            welding_recipe(rm, '%s_chestplate' % metal, item('unfinished_chestplate'), item('double_sheet'), item('chestplate'), metal_data.tier - 1)
            welding_recipe(rm, '%s_greaves' % metal, item('unfinished_greaves'), item('sheet'), item('greaves'), metal_data.tier - 1)
            welding_recipe(rm, '%s_boots' % metal, item('unfinished_boots'), item('sheet'), item('boots'), metal_data.tier - 1)

        if 'tool' in metal_data.types:
            welding_recipe(rm, '%s_shears' % metal, item('knife_blade'), item('knife_blade'), item('shears'), metal_data.tier - 1, combine_forging=True)

    for metal_in_1, metal_in_2, metal_out in (
        ('weak_platine_steel', 'black_steel', 'high_carbon_platine_steel'),
        ('weak_titan_steel', 'black_steel', 'high_carbon_titan_steel'),
        ('weak_tungsten_steel', 'black_steel', 'high_carbon_tungsten_steel')
    ):
        welding_recipe(rm, '%s_ingot' % metal_out, '#forge:ingots/%s' % metal_in_1, '#forge:ingots/%s' % metal_in_2, 'tfc_metallum_modern:metal/ingot/%s' % metal_out, METALS[metal_out].tier - 1)

    for metal, metal_data in METALS.items():
        for tool, tool_data in METAL_ITEMS.items():
            if tool == 'ingot' or (tool_data.mold and 'tool' in metal_data.types and metal_data.tier <= 2):
                casting_recipe(rm, '%s_%s' % (metal, tool), tool, metal, tool_data.smelt_amount, 0.1 if tool == 'ingot' else 1)
            if tool == 'ingot':
                casting_recipe(rm, '%s_fire_%s' % (metal, tool), 'fire_ingot', metal, tool_data.smelt_amount, 0.01, result_item='tfc_metallum_modern:metal/ingot/%s' % metal)

    for ore, ore_data in ORES.items():
        if ore_data.graded:
            for grade, data in ORE_GRADES.items():
                quern_recipe(rm, '%s_%s' % (grade, ore), 'tfc_metallum_modern:ore/%s_%s' % (grade, ore), 'tfc_metallum_modern:powder/%s' % ore, count=data.grind_amount)
            quern_recipe(rm, 'small_%s' % ore, 'tfc_metallum_modern:ore/small_%s' % ore, 'tfc_metallum_modern:powder/%s' % ore, count=2)


def craft_decorations(rm: ResourceManager, recipe_name: str, base_block: str, has_wall: bool = True):
        rm.crafting_shaped(recipe_name + '_slab', ['XXX'], base_block, (6, base_block + '_slab')).with_advancement(base_block)
        rm.crafting_shaped(recipe_name + '_stairs', ['X  ', 'XX ', 'XXX'], base_block, (8, base_block + '_stairs')).with_advancement(base_block)
        if has_wall:
            rm.crafting_shaped(recipe_name + '_wall', ['XXX', 'XXX'], base_block, (6, base_block + '_wall')).with_advancement(base_block)

def simple_pot_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredients: Json, fluid: str, output_fluid: str = None, output_items: Json = None, duration: int = 2000, temp: int = 300):
    rm.recipe(('pot', name_parts), 'tfc:pot', {
        'ingredients': ingredients,
        'fluid_ingredient': fluid_stack_ingredient(fluid),
        'duration': duration,
        'temperature': temp,
        'fluid_output': fluid_stack(output_fluid) if output_fluid is not None else None,
        'item_output': [utils.item_stack(item) for item in output_items] if output_items is not None else None
    })


def disable_recipe(rm: ResourceManager, name_parts: ResourceIdentifier):
    # noinspection PyTypeChecker
    rm.recipe(name_parts, None, {}, conditions='forge:false')


def collapse_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient, result: Optional[utils.Json] = None, copy_input: Optional[bool] = None):
    assert result is not None or copy_input
    rm.recipe(('collapse', name_parts), 'tfc:collapse', {
        'ingredient': ingredient,
        'result': result,
        'copy_input': copy_input
    })


def landslide_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, result: utils.Json):
    rm.recipe(('landslide', name_parts), 'tfc:landslide', {
        'ingredient': ingredient,
        'result': result
    })

def chisel_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, result: str, mode: str):
    rm.recipe(('chisel', mode, name_parts), 'tfc:chisel', {
        'ingredient': ingredient,
        'result': result,
        'mode': mode,
        'extra_drop': item_stack_provider(result) if mode == 'slab' else None
    })

def stone_cutting(rm: ResourceManager, name_parts: utils.ResourceIdentifier, item: str, result: str, count: int = 1) -> RecipeContext:
    return rm.recipe(('stonecutting', name_parts), 'minecraft:stonecutting', {
        'ingredient': utils.ingredient(item),
        'result': result,
        'count': count
    })

def no_remainder_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, group: str = None, conditions: utils.Json = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:no_remainder_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })

def no_remainder_shaped(rm: ResourceManager, name_parts: utils.ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:no_remainder_shaped_crafting', {
        'type': 'minecraft:crafting_shaped',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })

def damage_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, group: str = None, conditions: utils.Json = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:damage_inputs_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': utils.item_stack(result),
        'conditions': utils.recipe_condition(conditions)
    })

def damage_shaped(rm: ResourceManager, name_parts: utils.ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:damage_inputs_shaped_crafting', {
            'type': 'minecraft:crafting_shaped',
            'group': group,
            'pattern': pattern,
            'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
            'result': utils.item_stack(result),
            'conditions': utils.recipe_condition(conditions)
        }
    )

def extra_products_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: str, extra_result: str) -> RecipeContext:
    return delegate_recipe(rm, name_parts, 'tfc:extra_products_shapeless_crafting', {
        'type': 'minecraft:crafting_shapeless',
        'ingredients': utils.ingredient_list(ingredients),
        'result': utils.item_stack(result)
    }, {
        'extra_products': utils.item_stack_list(extra_result)
    })

def write_crafting_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, data: Json) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipes', res.path), data)
    return RecipeContext(rm, res)

def delegate_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, recipe_type: str, delegate: Json, data: Json = {}) -> RecipeContext:
    return write_crafting_recipe(rm, name_parts, {
        'type': recipe_type,
        **data,
        'recipe': delegate,
    })

def advanced_shaped(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: Sequence[str], ingredients: Json, result: Json, input_xy: Tuple[int, int], group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipes', res.path), {
        'type': 'tfc:advanced_shaped_crafting',
        'group': group,
        'pattern': pattern,
        'key': utils.item_stack_dict(ingredients, ''.join(pattern)[0]),
        'result': item_stack_provider(result),
        'input_row': input_xy[1],
        'input_column': input_xy[0],
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)

def advanced_shapeless(rm: ResourceManager, name_parts: ResourceIdentifier, ingredients: Json, result: Json, primary_ingredient: Json = None, group: str = None, conditions: Optional[Json] = None) -> RecipeContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'data', res.domain, 'recipes', res.path), {
        'type': 'tfc:advanced_shapeless_crafting',
        'group': group,
        'ingredients': utils.item_stack_list(ingredients),
        'result': result,
        'primary_ingredient': None if primary_ingredient is None else utils.ingredient(primary_ingredient),
        'conditions': utils.recipe_condition(conditions)
    })
    return RecipeContext(rm, res)

def quern_recipe(rm: ResourceManager, name: ResourceIdentifier, item: str, result: str, count: int = 1) -> RecipeContext:
    result = result if not isinstance(result, str) else utils.item_stack((count, result))
    return rm.recipe(('quern', name), 'tfc:quern', {
        'ingredient': utils.ingredient(item),
        'result': result
    })


def scraping_recipe(rm: ResourceManager, name: ResourceIdentifier, item: str, result: str, count: int = 1, input_texture=None, output_texture=None, extra_drop: str=None) -> RecipeContext:
    return rm.recipe(('scraping', name), 'tfc:scraping', {
        'ingredient': utils.ingredient(item),
        'result': utils.item_stack((count, result)),
        'input_texture': input_texture,
        'output_texture': output_texture,
        'extra_drop': utils.item_stack(extra_drop) if extra_drop else None
    })


def clay_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    stack = utils.item_stack(result)
    if ('count' in stack and stack['count'] == 1) or 'count' not in stack:
        rm.item_tag('clay_recycle_5', stack['item'])
    else:
        rm.item_tag('clay_recycle_1', stack['item'])
    knapping_recipe(rm, name_parts, 'tfc:clay', pattern, result, None, outside_slot_required)


def fire_clay_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    stack = utils.item_stack(result)
    if ('count' in stack and stack['count'] == 1) or 'count' not in stack:
        rm.item_tag('fire_clay_recycle_5', stack['item'])
    else:
        rm.item_tag('fire_clay_recycle_1', stack['item'])
    knapping_recipe(rm, name_parts, 'tfc:fire_clay', pattern, result, None, outside_slot_required)


def leather_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: Json, outside_slot_required: bool = None):
    knapping_recipe(rm, name_parts, 'tfc:leather', pattern, result, None, outside_slot_required)


def rock_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: ResourceIdentifier, ingredient: str = None, outside_slot_required: bool = False):
    knapping_recipe(rm, name_parts, 'tfc:rock', pattern, result, ingredient, outside_slot_required)


def horn_knapping(rm: ResourceManager, name_parts: ResourceIdentifier, pattern: List[str], result: ResourceIdentifier, ingredient: str = None, outside_slot_required: bool = False):
    knapping_recipe(rm, name_parts, 'tfc:goat_horn', pattern, {'item': 'minecraft:goat_horn', 'nbt': '{"instrument": "%s"}' % result}, ingredient, outside_slot_required)


def knapping_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, knap_type: str, pattern: List[str], result: Json, ingredient: Json, outside_slot_required: bool):
    for part in pattern:
        assert 0 < len(part) < 6, 'Incorrect length: %s' % part
    rm.recipe((knap_type.split(':')[1] + '_knapping', name_parts), 'tfc:knapping', {
        'knapping_type': knap_type,
        'outside_slot_required': outside_slot_required,
        'pattern': pattern,
        'ingredient': None if ingredient is None else utils.ingredient(ingredient),
        'result': utils.item_stack(result)
    })


def knapping_type(rm: ResourceManager, name_parts: ResourceIdentifier, item_input: Json, amount_to_consume: Optional[int], click_sound: str, consume_after_complete: bool, use_disabled_texture: bool, spawns_particles: bool, jei_icon_item: Json):
    rm.data(('tfc', 'knapping_types', name_parts), {
        'input': item_stack_ingredient(item_input),
        'amount_to_consume': amount_to_consume,
        'click_sound': click_sound,
        'consume_after_complete': consume_after_complete,
        'use_disabled_texture': use_disabled_texture,
        'spawns_particles': spawns_particles,
        'jei_icon_item': utils.item_stack(jei_icon_item)
    })


def heat_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, ingredient: Json, temperature: float, result_item: Optional[Union[str, Json]] = None, result_fluid: Optional[str] = None, use_durability: Optional[bool] = None, chance: Optional[float] = None) -> RecipeContext:
    result_item = item_stack_provider(result_item) if isinstance(result_item, str) else result_item
    result_fluid = None if result_fluid is None else fluid_stack(result_fluid)
    return rm.recipe(('heating', name_parts), 'tfc:heating', {
        'ingredient': utils.ingredient(ingredient),
        'result_item': result_item,
        'result_fluid': result_fluid,
        'temperature': temperature,
        'use_durability': use_durability if use_durability else None,
        'chance': chance,
    })


def casting_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, mold: str, metal: str, amount: int, break_chance: float, result_item: str = None):
    rm.recipe(('casting', name_parts), 'tfc:casting', {
        'mold': {'item': 'tfc:ceramic/%s_mold' % mold},
        'fluid': fluid_stack_ingredient('%d tfc_metallum_modern:metal/%s' % (amount, metal)),
        'result': utils.item_stack('tfc_metallum_modern:metal/%s/%s' % (mold, metal)) if result_item is None else utils.item_stack(result_item),
        'break_chance': break_chance
    })


def alloy_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, metal: str, *parts: Tuple[str, float, float]):
    rm.recipe(('alloy', name_parts), 'tfc:alloy', {
        'result': 'tfc_metallum_modern:%s' % metal,
        'contents': [{
            'metal': p[0],
            'min': p[1],
            'max': p[2]
        } for p in parts]
    })


def bloomery_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, result: Json, metal: Json, catalyst: Json, time: int):
    rm.recipe(('bloomery', name_parts), 'tfc:bloomery', {
        'result': item_stack_provider(result),
        'fluid': fluid_stack_ingredient(metal),
        'catalyst': item_stack_ingredient(catalyst),
        'duration': time
    })


def blast_furnace_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, metal_in: Json, metal_out: Json, catalyst: Json):
    rm.recipe(('blast_furnace', name_parts), 'tfc:blast_furnace', {
        'fluid': fluid_stack_ingredient(metal_in),
        'result': fluid_stack(metal_out),
        'catalyst': utils.ingredient(catalyst)
    })


def barrel_sealed_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, translation: str, duration: int, input_item: Optional[Json] = None, input_fluid: Optional[Json] = None, output_item: Optional[Json] = None, output_fluid: Optional[Json] = None, on_seal: Optional[Json] = None, on_unseal: Optional[Json] = None, sound: Optional[str] = None):
    rm.recipe(('barrel', name_parts), 'tfc:barrel_sealed', {
        'input_item': item_stack_ingredient(input_item) if input_item is not None else None,
        'input_fluid': fluid_stack_ingredient(input_fluid) if input_fluid is not None else None,
        'output_item': item_stack_provider(output_item) if isinstance(output_item, str) else output_item,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'duration': duration,
        'on_seal': on_seal,
        'on_unseal': on_unseal,
        'sound': sound
    })
    res = utils.resource_location('tfc', name_parts)
    rm.lang('tfc.recipe.barrel.' + res.domain + '.barrel.' + res.path.replace('/', '.'), lang(translation))


def barrel_instant_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, input_item: Optional[Json] = None, input_fluid: Optional[Json] = None, output_item: Optional[Json] = None, output_fluid: Optional[Json] = None, sound: Optional[str] = None):
    rm.recipe(('barrel', name_parts), 'tfc:barrel_instant', {
        'input_item': item_stack_ingredient(input_item) if input_item is not None else None,
        'input_fluid': fluid_stack_ingredient(input_fluid) if input_fluid is not None else None,
        'output_item': item_stack_provider(output_item) if output_item is not None else None,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'sound': sound
    })


def barrel_instant_fluid_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, primary_fluid: Optional[Json] = None, added_fluid: Optional[Json] = None, output_fluid: Optional[Json] = None, sound: Optional[str] = None):
    rm.recipe(('barrel', name_parts), 'tfc:barrel_instant_fluid', {
        'primary_fluid': fluid_stack_ingredient(primary_fluid) if primary_fluid is not None else None,
        'added_fluid': fluid_stack_ingredient(added_fluid) if added_fluid is not None else None,
        'output_fluid': fluid_stack(output_fluid) if output_fluid is not None else None,
        'sound': sound
    })


def loom_recipe(rm: ResourceManager, name: utils.ResourceIdentifier, ingredient: Json, result: Json, steps: int, in_progress_texture: str):
    return rm.recipe(('loom', name), 'tfc:loom', {
        'ingredient': item_stack_ingredient(ingredient),
        'result': utils.item_stack(result),
        'steps_required': steps,
        'in_progress_texture': in_progress_texture
    })


def anvil_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: Json, result: Json, tier: int, *rules: Rules, bonus: bool = None):
    rm.recipe(('anvil', name_parts), 'tfc:anvil', {
        'input': utils.ingredient(ingredient),
        'result': item_stack_provider(result),
        'tier': tier,
        'rules': [r.name for r in rules],
        'apply_forging_bonus': bonus
    })


def welding_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, first_input: Json, second_input: Json, result: Json, tier: int, combine_forging: bool = None):
    rm.recipe(('welding', name_parts), 'tfc:welding', {
        'first_input': utils.ingredient(first_input),
        'second_input': utils.ingredient(second_input),
        'tier': tier,
        'result': item_stack_provider(result),
        'combine_forging_bonus': combine_forging
    })

def glass_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, steps: List[str], batch: str, result: str):
    rm.recipe(('glassworking', name_parts), 'tfc:glassworking', {
        'operations': steps,
        'batch': utils.ingredient(batch),
        'result': utils.item_stack(result)
    })

def sewing_recipe(rm: ResourceManager, name_parts: utils.ResourceIdentifier, stitches: List[int], squares: List[int], result: str):
    rm.recipe(('sewing', name_parts), 'tfc:sewing', {
        'stitches': stitches,
        'squares': squares,
        'result': utils.item_stack(result)
    })

def fluid_stack(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return data_in
    fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
    assert not tag, 'fluid_stack() cannot be a tag'
    return {
        'fluid': fluid,
        'amount': amount
    }


def fluid_stack_ingredient(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return {
            'ingredient': fluid_ingredient(data_in['ingredient']),
            'amount': data_in['amount']
        }
    if pair := utils.maybe_unordered_pair(data_in, int, object):
        amount, fluid = pair
        return {'ingredient': fluid_ingredient(fluid), 'amount': amount}
    fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
    if tag:
        return {'ingredient': {'tag': fluid}, 'amount': amount}
    else:
        return {'ingredient': fluid, 'amount': amount}


def fluid_ingredient(data_in: Json) -> Json:
    if isinstance(data_in, dict):
        return data_in
    elif isinstance(data_in, List):
        return [*utils.flatten_list([fluid_ingredient(e) for e in data_in])]
    else:
        fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
        if tag:
            return {'tag': fluid}
        else:
            return fluid


def item_stack_ingredient(data_in: Json):
    if isinstance(data_in, dict):
        if 'type' in data_in:
            return item_stack_ingredient({'ingredient': data_in})
        return {
            'ingredient': utils.ingredient(data_in['ingredient']),
            'count': data_in['count'] if data_in.get('count') is not None else None
        }
    if pair := utils.maybe_unordered_pair(data_in, int, object):
        count, item = pair
        return {'ingredient': fluid_ingredient(item), 'count': count}
    item, tag, count, _ = utils.parse_item_stack(data_in, False)
    if tag:
        return {'ingredient': {'tag': item}, 'count': count}
    else:
        return {'ingredient': {'item': item}, 'count': count}

def fluid_item_ingredient(fluid: Json, delegate: Json = None):
    return {
        'type': 'tfc:fluid_item',
        'ingredient': delegate,
        'fluid_ingredient': fluid_stack_ingredient(fluid)
    }


def item_stack_provider(
    data_in: Json = None,
    # Possible Modifiers
    copy_input: bool = False,
    copy_heat: bool = False,
    copy_food: bool = False,  # copies both decay and traits
    copy_oldest_food: bool = False,  # copies only decay, from all inputs (uses crafting container)
    reset_food: bool = False,  # rest_food modifier - used for newly created food from non-food
    add_glass: bool = False,  # glassworking specific
    add_powder: bool = False,  # glassworking specific
    add_heat: float = None,
    add_trait: str = None,  # applies a food trait and adjusts decay accordingly
    remove_trait: str = None,  # removes a food trait and adjusts decay accordingly
    empty_bowl: bool = False,  # replaces a soup with its bowl
    copy_forging: bool = False,
    add_bait_to_rod: bool = False,  # adds bait to the rod, uses crafting container
    dye_color: str = None,  # applies a dye color to leather dye-able armor
    meal: Json = None  # makes a meal from input specified in json
) -> Json:
    if isinstance(data_in, dict):
        return data_in
    stack = utils.item_stack(data_in) if data_in is not None else None
    modifiers = [k for k, v in (
        # Ordering is important here
        # First, modifiers that replace the entire stack (copy input style)
        # Then, modifiers that only mutate an existing stack
        ('tfc:empty_bowl', empty_bowl),
        ('tfc:copy_input', copy_input),
        ('tfc:copy_heat', copy_heat),
        ('tfc:copy_food', copy_food),
        ('tfc:copy_oldest_food', copy_oldest_food),
        ('tfc:reset_food', reset_food),
        ('tfc:copy_forging_bonus', copy_forging),
        ('tfc:add_bait_to_rod', add_bait_to_rod),
        ('tfc:add_glass', add_glass),
        ('tfc:add_powder', add_powder),
        ({'type': 'tfc:add_heat', 'temperature': add_heat}, add_heat is not None),
        ({'type': 'tfc:add_trait', 'trait': add_trait}, add_trait is not None),
        ({'type': 'tfc:remove_trait', 'trait': remove_trait}, remove_trait is not None),
        ({'type': 'tfc:dye_leather', 'color': dye_color}, dye_color is not None),
        ({'type': 'tfc:meal', **(meal if meal is not None else {})}, meal is not None),
    ) if v]
    if modifiers:
        return {
            'stack': stack,
            'modifiers': modifiers
        }
    return stack

def not_rotten(ingredient: Json) -> Json:
    return {
        'type': 'tfc:not_rotten',
        'ingredient': utils.ingredient(ingredient)
    }

def has_trait(ingredient: Json, trait: str, invert: bool = False) -> Json:
    return {
        'type': 'tfc:lacks_trait' if invert else 'tfc:has_trait',
        'trait': trait,
        'ingredient': utils.ingredient(ingredient)
    }

def lacks_trait(ingredient: Json, trait: str) -> Json:
    return has_trait(ingredient, trait, True)