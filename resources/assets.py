import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, atlases, BlockContext
from mcresources.type_definitions import ResourceIdentifier, JsonObject

from constants import *

def generate(rm: ResourceManager):

    for ore, metal in DEPOSITS_DICT.items():
        rm.item_model(('pan', ore, 'result'), {'material': 'tfc_metallum_modern:block/metal/smooth/' + metal}, parent='tfc:item/pan/result')
        for rock_id, rock in enumerate(ROCKS.keys()):
            rm.item_model(('pan', ore, rock + '_full'), {'material': 'tfc:block/rock/gravel/%s' % rock}, parent='tfc:item/pan/full')
            rm.item_model(('pan', ore, rock + '_half'), {'material': 'tfc:block/rock/gravel/%s' % rock}, parent='tfc:item/pan/half')
            block = rm.blockstate(('deposit', ore, rock)).with_lang(lang('%s %s Deposit', rock, ore)).with_item_model()
            block.with_block_model({
                'all': 'tfc:block/rock/gravel/%s' % rock,
                'overlay': 'tfc_metallum_modern:block/deposit/%s' % ore
            }, parent='tfc:block/ore')
            rare = DEPOSIT_RARES[rock]
            block.with_block_loot('tfc_metallum_modern:deposit/%s/%s' % (ore, rock))
            for path, chance, loot_type in (('panning', 0.5, 'minecraft:fishing'), ('sluicing', 0.55, 'minecraft:empty')):
                rm.loot('deposits/%s_%s' % (ore, rock), *[loot_tables.alternatives({
                    'name': 'tfc_metallum_modern:ore/small_%s' % ore,
                    'conditions': [loot_tables.random_chance(chance)],  # 50% chance (for pan)
                }, {
                    'name': 'tfc:rock/loose/%s' % rock,
                    'conditions': [loot_tables.random_chance(0.5)],  # 25% chance
                },
                {
                    'name': 'tfc:rock/loose/%s' % rock if rock not in ('rhyolite', 'dacite', 'andesite') else 'tfc:groundcover/pumice',
                    'conditions': [loot_tables.random_chance(0.25)],  # 6.25% chance... for when its not pumice this just rerolls loose rocks
                }, {
                    'name': 'tfc:ore/%s' % rare,
                    'conditions': [loot_tables.random_chance(0.0533)],  # 1% chance
                })], path=path, loot_type=loot_type)
    
    # Rock Type Blocks
    for rock, rock_data in ROCKS.items():

        for ore, ore_data in ORES.items():
            if ore_data.graded:
                # Small Ores / Groundcover Blocks
                block = rm.blockstate('tfc_metallum_modern:ore/small_%s' % ore, variants={"": four_ways('tfc_metallum_modern:block/groundcover/%s' % ore)}, use_default_model=False)
                block.with_lang(lang('small %s', ore)).with_block_loot('tfc_metallum_modern:ore/small_%s' % ore).with_tag('tfc:can_be_snow_piled')

                rm.item_model('tfc_metallum_modern:ore/small_%s' % ore).with_lang(lang('small %s', ore)).with_tag('tfc:nuggets')

                for grade in ORE_GRADES.keys():
                    block = rm.blockstate(('ore', grade + '_' + ore, rock), 'tfc_metallum_modern:block/ore/%s_%s/%s' % (grade, ore, rock))

                    if rock == 'claystone' or rock == 'shale':
                        block.with_block_model({
                            'side': 'tfc:block/rock/raw/%s' % rock,
                            'end': 'tfc:block/rock/raw/%s_top' % rock,
                            'overlay': 'tfc_metallum_modern:block/ore/%s_%s' % (grade, ore),
                            'overlay_end': 'tfc_metallum_modern:block/ore/%s_%s' % (grade, ore)
                        }, parent='tfc:block/ore_column')
                    else:
                        block.with_block_model({
                            'all': 'tfc:block/rock/raw/%s' % rock,
                            'overlay': 'tfc_metallum_modern:block/ore/%s_%s' % (grade, ore),
                        }, parent='tfc:block/ore')
                    block.with_item_model()
                    block.with_lang(lang('%s %s %s', grade, rock, ore))
                    block.with_block_loot('tfc_metallum_modern:ore/%s_%s' % (grade, ore))

    # Loose Ore Items
    for ore, ore_data in ORES.items():
        if ore_data.graded:
            for grade in ORE_GRADES.keys():
                rm.item_model('tfc_metallum_modern:ore/%s_%s' % (grade, ore)).with_lang(lang('%s %s', grade, ore))
            rm.item_model('tfc_metallum_modern:ore/small_%s' % ore).with_lang(lang('small %s', ore))
            rm.item_model('tfc_metallum_modern:powder/%s' % ore).with_lang(lang('%s powder', ore)).with_tag('tfc:powders')

    for metal in METALS.keys():
        rm.blockstate(('fluid', 'metal', metal)).with_block_model({'particle': 'block/lava_still'}, parent=None).with_lang(lang('Molten %s', metal)).with_tag('tfc:all_fluids')
        rm.lang('fluid.tfc_metallum_modern.metal.%s' % metal, lang('%s', metal))
        rm.fluid_tag(metal, 'tfc_metallum_modern:metal/%s' % metal, 'tfc_metallum_modern:metal/flowing_%s' % metal)

        item = rm.custom_item_model(('bucket', 'metal', metal), 'forge:fluid_container', {
            'parent': 'forge:item/bucket',
            'fluid': 'tfc_metallum_modern:metal/%s' % metal
        })
        item.with_lang(lang('molten %s bucket', metal))
        cauldron(rm, 'molten %s' % metal, 'metal/%s' % metal, False)

    for metal in BLOOM_METALS:
        rm.item_model('raw_%s_bloom' % metal, 'tfc_metallum_modern:item/metal/bloom/%s/unrefined' % metal).with_lang(lang('Raw %s Bloom' % metal.title())).with_tag('tfc:blooms')
        rm.item_model('refined_%s_bloom' % metal, 'tfc_metallum_modern:item/metal/bloom/%s/refined' % metal).with_lang(lang('Refined %s Bloom' % metal.title())).with_tag('tfc:blooms')
        
    for metal, metal_data in METALS.items():
        # Metal Items
        for metal_item, metal_item_data in METAL_ITEMS.items():
            if metal_item_data.type in metal_data.types or metal_item_data.type == 'all':
                texture = 'tfc_metallum_modern:item/metal/%s/%s' % (metal_item, metal) if metal_item != 'shield' or metal in ('red_steel', 'blue_steel', 'wrought_iron') else 'tfc_metallum_modern:item/metal/shield/%s_front' % metal
                if metal_item == 'fishing_rod':
                    rm.item_model(('metal', metal_item, metal + '_cast'), 'tfc:item/metal/fishing_rod/alt_cast' if metal == 'red_steel' or metal == 'blue_steel' else 'minecraft:item/fishing_rod_cast', parent='minecraft:item/fishing_rod')
                    item = item_model_property(rm, ('metal', metal_item, metal), [{'predicate': {'tfc:cast': 1}, 'model': 'tfc_metallum_modern:item/metal/fishing_rod/%s_cast' % metal}], {'parent': 'minecraft:item/handheld_rod', 'textures': {'layer0': texture}})
                elif metal_item == 'shield':
                    item = rm.item(('metal', metal_item, metal)).with_tag('tfc:shields')  # Shields have a custom model for inventory and blocking
                elif metal_item == 'javelin':
                    item = make_javelin(rm, 'metal/%s/%s' % (metal_item, metal), 'tfc_metallum_modern:item/metal/javelin/%s' % metal)
                elif metal_item in TFC_ARMOR_SECTIONS:
                    item = trim_model(rm, ('metal', metal_item, metal), 'tfc_metallum_modern:item/metal/%s/%s' % (metal_item, metal), 'tfc:item/%s_trim' % metal_item)
                else:
                    item = rm.item_model(('metal', metal_item, metal), texture, parent=metal_item_data.parent_model)

                if metal_item == 'propick':
                    item.with_lang('%s Prospector\'s Pick' % lang(metal))  # .title() works weird w.r.t the possessive.
                elif metal_item == 'propick_head':
                    item.with_lang('%s Prospector\'s Pick Head' % lang(metal))
                else:
                    item.with_lang(lang('%s %s', metal, metal_item))

        # Metal Blocks
        for metal_block, metal_block_data in METAL_BLOCKS.items():
            if metal_block_data.type in metal_data.types or metal_block_data.type == 'all':
                rm.block_tag('minecraft:mineable/pickaxe', 'tfc_metallum_modern:metal/%s/%s' % (metal_block, metal) if metal_block != 'block_slab' and metal_block != 'block_stairs' else 'tfc_metallum_modern:metal/block/%s_%s' % (metal, metal_block.replace('block_', '')))
                metal_dir = 'tfc_metallum_modern:block/metal/%s/%s'
                metal_tex = 'tfc_metallum_modern:block/metal/smooth/%s' % metal
                if metal_block == 'lamp':
                    rm.block_model('tfc_metallum_modern:metal/lamp/%s_hanging_on' % metal, {'metal': metal_tex, 'chain': 'tfc_metallum_modern:block/metal/chain/%s' % metal, 'lamp': 'tfc:block/lamp'}, parent='tfc:block/lamp_hanging')
                    rm.block_model('tfc_metallum_modern:metal/lamp/%s_hanging_off' % metal, {'metal': metal_tex, 'chain': 'tfc_metallum_modern:block/metal/chain/%s' % metal, 'lamp': 'tfc:block/lamp_off'}, parent='tfc:block/lamp_hanging')
                    rm.block_model('tfc_metallum_modern:metal/lamp/%s_on' % metal, {'metal': metal_tex, 'lamp': 'tfc:block/lamp'}, parent='tfc:block/lamp')
                    rm.block_model('tfc_metallum_modern:metal/lamp/%s_off' % metal, {'metal': metal_tex, 'lamp': 'tfc:block/lamp_off'}, parent='tfc:block/lamp')
                    rm.item_model(('metal', 'lamp', metal))
                    rm.blockstate(('metal', metal_block, metal), variants={
                        'hanging=false,lit=false': {'model': 'tfc_metallum_modern:block/metal/lamp/%s_off' % metal},
                        'hanging=true,lit=false': {'model': 'tfc_metallum_modern:block/metal/lamp/%s_hanging_off' % metal},
                        'hanging=false,lit=true': {'model': 'tfc_metallum_modern:block/metal/lamp/%s_on' % metal},
                        'hanging=true,lit=true': {'model': 'tfc_metallum_modern:block/metal/lamp/%s_hanging_on' % metal},
                    }).with_lang(lang('%s lamp', metal)).with_block_loot({
                        'name': 'tfc_metallum_modern:metal/lamp/%s' % metal,
                        'functions': [{'function': 'tfc:copy_fluid'}]
                    }).with_tag('tfc:lamps')
                    rm.lang('block.tfc.metal.lamp.%s.filled' % metal, lang('filled %s lamp', metal))
                elif metal_block == 'chain':
                    chain_tex = 'tfc_metallum_modern:block/metal/chain/%s' % metal
                    rm.block_model(('metal', 'chain', metal), {'all': chain_tex, 'particle': chain_tex}, parent='minecraft:block/chain')
                    rm.blockstate(('metal', 'chain', metal), variants={
                        'axis=x': {'model': 'tfc_metallum_modern:block/metal/chain/%s' % metal, 'x': 90, 'y': 90},
                        'axis=y': {'model': 'tfc_metallum_modern:block/metal/chain/%s' % metal},
                        'axis=z': {'model': 'tfc_metallum_modern:block/metal/chain/%s' % metal, 'x': 90}
                    }).with_lang(lang('%s chain', metal)).with_block_loot('tfc_metallum_modern:metal/chain/%s' % metal)
                    rm.item_model(('metal', 'chain', metal), 'tfc_metallum_modern:item/metal/chain/%s' % metal)
                elif metal_block == 'trapdoor':
                    rm.block(('metal', metal_block, metal)).make_trapdoor(trapdoor_suffix='', texture=metal_dir % (metal_block, metal)).with_lang(lang('%s trapdoor', metal)).with_block_loot('tfc_metallum_modern:metal/%s/%s' % (metal_block, metal))
                elif metal_block == 'anvil':
                    block = rm.blockstate(('metal', '%s' % metal_block, metal), variants={
                        'facing=north': {'model': 'tfc_metallum_modern:block/metal/anvil/%s' % metal, 'y': 90},
                        'facing=east': {'model': 'tfc_metallum_modern:block/metal/anvil/%s' % metal, 'y': 180},
                        'facing=south': {'model': 'tfc_metallum_modern:block/metal/anvil/%s' % metal, 'y': 270},
                        'facing=west': {'model': 'tfc_metallum_modern:block/metal/anvil/%s' % metal}
                    })
                    block.with_block_model({
                        'all': metal_tex,
                        'particle': metal_tex
                    }, parent=metal_block_data.parent_model)
                    block.with_block_loot('tfc_metallum_modern:metal/%s/%s' % (metal_block, metal))
                    block.with_lang(lang('%s %s' % (metal, metal_block)))
                    block.with_item_model()
                elif metal_block == 'bars':
                    bars = 'metal/bars/%s' % metal
                    rm.blockstate_multipart(bars,
                        ({'model': 'tfc_metallum_modern:block/bars/%s_bars_post_ends' % metal}),
                        ({'north': False, 'south': False, 'east': False, 'west': False}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_post' % metal}),
                        ({'north': True, 'south': False, 'east': False, 'west': False}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_cap' % metal}),
                        ({'north': False, 'south': False, 'east': True, 'west': False}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_cap' % metal, 'y': 90}),
                        ({'north': False, 'south': True, 'east': False, 'west': False}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_cap_alt' % metal}),
                        ({'north': False, 'south': False, 'east': False, 'west': True}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_cap_alt' % metal, 'y': 90}),
                        ({'north': True}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_side' % metal}),
                        ({'east': True}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_side' % metal, 'y': 90}),
                        ({'south': True}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_side_alt' % metal}),
                        ({'west': True}, {'model': 'tfc_metallum_modern:block/bars/%s_bars_side_alt' % metal, 'y': 90}),
                        ).with_lang(lang(bars)).with_tag('minecraft:mineable/pickaxe').with_block_loot('tfc_metallum_modern:%s' % bars).with_lang(lang('%s bars', metal))
                    for var in ('post_ends', 'post', 'cap', 'cap_alt', 'side', 'side_alt'):
                        rm.block_model('bars/%s_bars_%s' % (metal, var), parent='minecraft:block/iron_bars_%s' % var, textures={'particle': 'tfc_metallum_modern:block/metal/bars/%s' % metal, 'bars': 'tfc_metallum_modern:block/metal/bars/%s' % metal, 'edge': metal_tex})
                    rm.item_model(bars, 'tfc_metallum_modern:block/%s' % bars)
                elif metal_block == 'block' or metal_block == 'block_stairs' or metal_block == 'block_slab':
                    block = rm.blockstate(('metal', 'block', metal)).with_block_model().with_lang(lang('%s plated block', metal)).with_item_model().with_block_loot('tfc_metallum_modern:metal/block/%s' % metal).with_tag('minecraft:mineable/pickaxe')
                    block.make_slab()
                    rm.block(('metal', 'block', '%s_slab' % metal)).with_lang(lang('%s plated slab', metal))
                    rm.block(('metal', 'block', '%s_stairs' % metal)).with_lang(lang('%s plated stairs', metal)).with_block_loot('tfc_metallum_modern:metal/block/%s_stairs' % metal)
                    block.make_stairs()
                    slab_loot(rm, 'tfc_metallum_modern:metal/block/%s_slab' % metal)
                else:
                    block = rm.blockstate(('metal', '%s' % metal_block, metal))
                    block.with_block_model({
                        'all': metal_tex,
                        'particle': metal_tex
                    }, parent=metal_block_data.parent_model)
                    block.with_block_loot('tfc_metallum_modern:metal/%s/%s' % (metal_block, metal))
                    block.with_lang(lang('%s %s' % (metal, metal_block)))
                    block.with_item_model()
    


def four_ways(model: str) -> List[Dict[str, Any]]:
    return [
        {'model': model, 'y': 90},
        {'model': model},
        {'model': model, 'y': 180},
        {'model': model, 'y': 270}
    ]


def four_rotations(model: str, rots: Tuple[Any, Any, Any, Any], suffix: str = '', prefix: str = '') -> Dict[str, Dict[str, Any]]:
    return {
        '%sfacing=east%s' % (prefix, suffix): {'model': model, 'y': rots[0]},
        '%sfacing=north%s' % (prefix, suffix): {'model': model, 'y': rots[1]},
        '%sfacing=south%s' % (prefix, suffix): {'model': model, 'y': rots[2]},
        '%sfacing=west%s' % (prefix, suffix): {'model': model, 'y': rots[3]}
    }

def make_javelin(rm: ResourceManager, name_parts: str, texture: str) -> 'ItemContext':
    rm.item_model(name_parts + '_throwing_base', {'particle': texture}, parent='minecraft:item/trident_throwing')
    rm.item_model(name_parts + '_in_hand', {'particle': texture}, parent='minecraft:item/trident_in_hand')
    rm.item_model(name_parts + '_gui', texture)
    model = rm.domain + ':item/' + name_parts
    correct_perspectives = {
        'none': {'parent': model + '_gui'},
        'fixed': {'parent': model + '_gui'},
        'ground': {'parent': model + '_gui'},
        'gui': {'parent': model + '_gui'}
    }
    rm.custom_item_model(name_parts + '_throwing', 'forge:separate_transforms', {
        'gui_light': 'front',
        'base': {'parent': model + '_throwing_base'},
        'perspectives': correct_perspectives
    })

    return rm.custom_item_model(name_parts, 'forge:separate_transforms', {
        'textures': {'particle': texture},
        'gui_light': 'front',
        'overrides': [{'predicate': {'tfc:throwing': 1}, 'model': model + '_throwing'}],
        'base': {'parent': model + '_in_hand'},
        'perspectives': correct_perspectives
    })

def slab_loot(rm: ResourceManager, loot: str):
    return rm.block_loot(loot, {
        'name': loot,
        'functions': [{
            'function': 'minecraft:set_count',
            'conditions': [loot_tables.block_state_property(loot + '[type=double]')],
            'count': 2,
            'add': False
        }]
    })

def item_model_property(rm: ResourceManager, name_parts: utils.ResourceIdentifier, overrides: utils.Json, data: Dict[str, Any]) -> ItemContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'assets', res.domain, 'models', 'item', res.path), {
        **data,
        'overrides': overrides
    })
    return ItemContext(rm, res)

def trim_model(rm: ResourceManager, name_parts: utils.ResourceIdentifier, base: str, trim: str, overlay: str = None) -> 'ItemContext':
    return rm.custom_item_model(name_parts, 'tfc:trim', {
        'parent': 'forge:item/default',
        'textures': {
            'armor': base,
            'trim': trim,
            'overlay': overlay
        }
    })

def cauldron(rm: ResourceManager, name: str, fluid: str, water: bool = True):
    block = rm.blockstate(('cauldron', fluid))
    block.with_block_model({
        'content': 'block/water_still' if water else 'tfc:block/molten_still',
        'inside': 'block/cauldron_inner',
        'particle': 'block/cauldron_side',
        'top': 'block/cauldron_top',
        'bottom': 'block/cauldron_bottom',
        'side': 'block/cauldron_side'
    }, parent='minecraft:block/template_cauldron_full')
    block.with_block_loot('minecraft:cauldron')
    block.with_lang(lang('%s cauldron', name))
    block.with_tag('minecraft:mineable/pickaxe')