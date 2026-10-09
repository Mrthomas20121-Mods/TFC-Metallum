
from mcresources import ResourceManager

from constants import *


def generate(rm: ResourceManager):

    rm.block_tag('needs_aluminum_tools')
    rm.block_tag('needs_brittanium_tools')
    rm.block_tag('needs_florentine_bronze_tools')
    rm.block_tag('needs_pewter_tools')
    rm.block_tag('needs_electrum_tools')
    rm.block_tag('needs_invar_tools')
    rm.block_tag('needs_ferroboron_tools')
    rm.block_tag('needs_titanium_tools')

    for metal, metal_data in METALS.items():
        # Metal Ingots / Sheets, for Ingot/Sheet Piles
        rm.item_tag('forge:ingots/%s' % metal)
        rm.item_tag('tfc:pileable_ingots', '#forge:ingots/%s' % metal)
        if len(metal_data.types) > 0:
            rm.item_tag('forge:sheets/%s' % metal)
            rm.item_tag('tfc:pileable_double_ingots', '#forge:double_ingots/%s' % metal)
            rm.item_tag('tfc:pileable_sheets', '#forge:sheets/%s' % metal)

        # Metal Tools
        if 'tool' in metal_data.types:
            rm.item_tag('tfc:metal_item/%s_tools' % metal, *['tfc_metallum_modern:metal/%s/%s' % (item, metal) for item in METAL_TOOL_HEADS])

        # Metal Tool Tags
        if 'tool' in metal_data.types:
            for tool_type, tool_tag in TOOL_TAGS.items():
                rm.item_tag(tool_tag, 'tfc_metallum_modern:metal/%s/%s' % (tool_type, metal))
                rm.item_tag('tfc:usable_on_tool_rack', 'tfc_metallum_modern:metal/%s/%s' % (tool_type, metal))
            rm.item_tag('tfc:usable_on_tool_rack', 'tfc_metallum_modern:metal/fishing_rod/%s' % metal, 'tfc_metallum_modern:metal/tuyere/%s' % metal)

        # Metal Blocks + Items
        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            if item_data.type in metal_data.types or item_data.type == 'all':
                item_name = 'tfc_metallum_modern:metal/block/%s_%s' % (metal, item.replace('block_', '')) if 'block_' in item else 'tfc_metallum_modern:metal/%s/%s' % (item, metal)
                if item_data.tag is not None:
                    rm.item_tag(item_data.tag, '#%s/%s' % (item_data.tag, metal))
                    rm.item_tag(item_data.tag + '/' + metal, item_name)

                rm.item_tag('metal_item/%s' % metal, item_name)

        if 'utility' in metal_data.types:
            rm.block_and_item_tag('tfc:trapdoors', 'tfc_metallum_modern:metal/trapdoor/%s' % metal)
            rm.block_and_item_tag('tfc:lamps', 'tfc_metallum_modern:metal/lamp/%s' % metal)
        if 'part' in metal_data.types:
            rm.block_tag('minecraft:stairs', 'tfc_metallum_modern:metal/block/%s_stairs' % metal)
            rm.block_tag('minecraft:slabs', 'tfc_metallum_modern:metal/block/%s_slab' % metal)
            rm.block_and_item_tag('tfc:metal_plated_blocks', 'tfc_metallum_modern:metal/block/%s' % metal)

        if 'armor' in metal_data.types:
            rm.item_tag('minecraft:trimmable_armor', *['tfc_metallum_modern:metal/%s/%s' % (section, metal) for section in TFC_ARMOR_SECTIONS])

        if metal_data.tier < 3:
            rm.fluid_tag('usable_in_tool_head_mold', 'tfc_metallum_modern:metal/%s' % metal)

        # bronze metals
        if metal == 'florentine_bronze':
            rm.item_tag('forge:double_sheets/any_bronze', '#forge:double_sheets/%s' % metal)

    # ores 
    for ore, ore_data in ORES.items():
        for rock in ROCKS.keys():
            if ore_data.graded:
                for grade in ORE_GRADES.keys():
                    rm.block_tag('tfc:prospectable', 'tfc_metallum_modern:ore/%s_%s/%s' % (grade, ore, rock))
                    rm.block('tfc_metallum_modern:ore/%s_%s/%s/prospected' % (grade, ore, rock)).with_lang(lang(ore))

    for rock in ROCKS.keys():
        for ore, ore_data in ORES.items():
            if ore_data.graded:
                for grade in ORE_GRADES.keys():
                    rm.block_tag('tfc:rock/ores', 'tfc_metallum_modern:ore/%s_%s/%s' % (grade, ore, rock))
            else:
                rm.block_tag('tfc:rock/ores', 'tfc_metallum_modern:ore/%s/%s' % (ore, rock))

        for ore in ORE_DEPOSITS:
            rm.block_and_item_tag('forge:gravel', 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock))
            rm.block_and_item_tag('tfc:ore_deposits', 'tfc_metallum_modern:deposit/%s/%s' % (ore, rock))

        # Ore tags
    for ore, data in ORES.items():
        if data.tag not in DEFAULT_FORGE_ORE_TAGS:
            rm.block_and_item_tag('forge:ores', '#forge:ores/%s' % data.tag)
        if data.graded:  # graded ores -> each grade is declared as a TFC tag, then added to the forge tag
            rm.block_and_item_tag('forge:ores/%s' % data.tag, '#tfc_metallum_modern:ores/%s/poor' % data.tag, '#tfc_metallum_modern:ores/%s/normal' % data.tag, '#tfc_metallum_modern:ores/%s/rich' % data.tag)
            rm.item_tag('tfc:ore_pieces', 'tfc_metallum_modern:ore/poor_%s' % ore, 'tfc_metallum_modern:ore/normal_%s' % ore, 'tfc_metallum_modern:ore/rich_%s' % ore)
            rm.item_tag('tfc:small_ore_pieces', 'tfc_metallum_modern:ore/small_%s' % ore)
        else:
            rm.item_tag('tfc:ore_pieces', 'tfc_metallum_modern:ore/%s' % ore)
        for rock in ROCKS.keys():
            if data.graded:
                rm.block_and_item_tag('ores/%s/poor' % data.tag, 'tfc_metallum_modern:ore/poor_%s/%s' % (ore, rock))
                rm.block_and_item_tag('ores/%s/normal' % data.tag, 'tfc_metallum_modern:ore/normal_%s/%s' % (ore, rock))
                rm.block_and_item_tag('ores/%s/rich' % data.tag, 'tfc_metallum_modern:ore/rich_%s/%s' % (ore, rock))
            else:
                rm.block_and_item_tag('forge:ores/%s' % data.tag, 'tfc_metallum_modern:ore/%s/%s' % (ore, rock))

    def needs_tool(_tool: str) -> str:
        return {
            'wood': 'forge:needs_wood_tool', 'stone': 'forge:needs_wood_tool',
            'copper': 'minecraft:needs_stone_tool',
            'bronze': 'minecraft:needs_iron_tool',
            'iron': 'minecraft:needs_iron_tool', 'wrought_iron': 'minecraft:needs_iron_tool',
            'diamond': 'minecraft:needs_diamond_tool', 'steel': 'minecraft:needs_diamond_tool',
            'netherite': 'forge:needs_netherite_tool', 'black_steel': 'tfc:needs_black_steel_tool',
            'colored_steel': 'tfc:needs_colored_steel_tool'
        }[_tool]

    for ore, data in ORES.items():
        for rock in ROCKS.keys():
            if data.graded:
                rm.block_tag(needs_tool(data.required_tool), 'tfc_metallum_modern:ore/poor_%s/%s' % (ore, rock), 'tfc_metallum_modern:ore/normal_%s/%s' % (ore, rock), 'tfc_metallum_modern:ore/rich_%s/%s' % (ore, rock))

    rm.block_tag('minecraft:mineable/shovel', *[
        *['tfc_metallum_modern:deposit/%s/%s' % (ore, rock) for ore in ORE_DEPOSITS for rock in ROCKS.keys()],
    ])

    rm.block_tag('minecraft:mineable/pickaxe', *[
        *['tfc_metallum_modern:ore/%s_%s/%s' % (grade, ore, rock) for ore, ore_data in ORES.items() for rock in ROCKS.keys() for grade in ORE_GRADES.keys() if ore_data.graded],
        *['tfc_metallum_modern:ore/small_%s' % ore for ore, ore_data in ORES.items() if ore_data.graded],
        *['tfc_metallum_modern:metal/%s/%s' % (variant, metal) for variant, variant_data in METAL_BLOCKS.items() for metal, metal_data in METALS.items() if variant_data.type in metal_data.types and 'block_' not in variant],
        *['tfc_metallum_modern:metal/block/%s_slab' % metal for metal, metal_data in METALS.items() if 'utility' in metal_data.types],
        *['tfc_metallum_modern:metal/block/%s_stairs' % metal for metal, metal_data in METALS.items() if 'utility' in metal_data.types],
    ])

    rm.fluid_tag('tfc:molten_metals', *['tfc_metallum_modern:metal/%s' % metal for metal in METALS.keys()])

    rm.fluid_tag('tfc:usable_in_tool_head_mold', 'tfc_metallum_modern:metal/aluminum', 'tfc_metallum_modern:metal/britannium', 'tfc_metallum_modern:metal/florentine_bronze', 'tfc_metallum_modern:metal/pewter')

