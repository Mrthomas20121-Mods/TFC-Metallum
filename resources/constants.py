from typing import Dict, List, Set, NamedTuple, Sequence, Optional, Tuple, Any

class Rock(NamedTuple):
    category: str
    sand: str

class MetalItem(NamedTuple):
    type: str
    smelt_amount: int
    parent_model: str
    tag: Optional[str]
    mold: bool
    durability: bool


class Ore(NamedTuple):
    metal: Optional[str]
    graded: bool
    required_tool: str
    tag: str
    dye_color: Optional[str] = None


class OreGrade(NamedTuple):
    grind_amount: int


class Vein(NamedTuple):
    ore: str  # The name of the ore (as found in ORES)
    vein_type: str  # Either 'cluster', 'pipe' or 'disc'
    rarity: int
    size: int
    min_y: int
    max_y: int
    density: float
    grade: tuple[int, int, int]  # (poor, normal, rich) weights
    rocks: tuple[str, ...]  # Rock, or rock categories
    biomes: str | None
    height: int
    radius: int
    deposits: bool
    indicator_rarity: int  # Above-ground indicators
    underground_rarity: int  # Underground indicators
    underground_count: int
    project: bool | None  # Project to surface
    project_offset: bool | None  # Project offset
    near_lava: bool | None

    @staticmethod
    def new(
        ore: str,
        rarity: int,
        size: int,
        min_y: int,
        max_y: int,
        density: float,
        rocks: tuple[str, ...],

        vein_type: str = 'cluster',
        grade: tuple[int, int, int] = (),
        biomes: str = None,
        height: int = 2,  # For disc type veins, `size` is the width
        radius: int = 5,  # For pipe type veins, `size` is the height
        deposits: bool = False,
        indicator: int = 12,  # Indicator rarity
        deep_indicator: tuple[int, int] = (1, 0),  # Pair of (rarity, count) for underground indicators
        project: str | bool = None,  # Projects to surface. Either True or 'offset'
        near_lava: bool | None = None,
    ):
        assert 0 < density < 1
        assert isinstance(rocks, tuple), 'Forgot the trailing comma in a single element tuple: %s' % repr(rocks)
        assert vein_type in ('cluster', 'disc', 'pipe')
        assert project is None or project is True or project == 'offset'

        underground_rarity, underground_count = deep_indicator
        return Vein(ore, 'tfc:%s_vein' % vein_type, rarity, size, min_y, max_y, density, grade, rocks, biomes, height, radius, deposits, indicator, underground_rarity, underground_count, None if project is None else True, None if project != 'offset' else True, near_lava)

    def config(self) -> dict[str, Any]:
        cfg = {
            'rarity': self.rarity,
            'density': self.density,
            'min_y': self.min_y,
            'max_y': self.max_y,
            'project': self.project,
            'project_offset': self.project_offset,
            'biomes': self.biomes,
            'near_lava': self.near_lava,
        }
        if self.vein_type == 'tfc:cluster_vein':
            cfg.update(size=self.size)
        elif self.vein_type == 'tfc:pipe_vein':
            cfg.update(min_skew=5, max_skew=13, min_slant=0, max_slant=2, sign=0, height=self.size, radius=self.radius)
        else:
            cfg.update(size=self.size, height=self.height)
        return cfg

class Metal(NamedTuple):
    tier: int
    types: Set[str]  # One of 'part', 'tool', 'armor', 'utility'
    heat_capacity_base: float  # Do not access directly, use one of specific or ingot heat capacity.
    melt_temperature: float
    melt_metal: Optional[str]

    def specific_heat_capacity(self) -> float: return round(300 / self.heat_capacity_base) / 100_000
    def ingot_heat_capacity(self) -> float: return 1 / self.heat_capacity_base

TOOL_TAGS: Dict[str, str] = {
    # Rock
    'axe': 'axes',
    'hammer': 'hammers',
    'hoe': 'hoes',
    'javelin': 'javelins',
    'knife': 'knives',
    'shovel': 'shovels',
    # Metal Only
    'pickaxe': 'pickaxes',
    'chisel': 'chisels',
    'mace': 'maces',
    'sword': 'swords',
    'saw': 'saws',
    'propick': 'propicks',
    'scythe': 'scythes',
    'shears': 'shears',
    'tuyere': 'tuyeres'
}

ROCKS: Dict[str, Rock] = {
    'granite': Rock('igneous_intrusive', 'white'),
    'diorite': Rock('igneous_intrusive', 'white'),
    'gabbro': Rock('igneous_intrusive', 'black'),
    'shale': Rock('sedimentary', 'black'),
    'claystone': Rock('sedimentary', 'brown'),
    'limestone': Rock('sedimentary', 'white'),
    'conglomerate': Rock('sedimentary', 'green'),
    'dolomite': Rock('sedimentary', 'black'),
    'chert': Rock('sedimentary', 'yellow'),
    'chalk': Rock('sedimentary', 'white'),
    'rhyolite': Rock('igneous_extrusive', 'red'),
    'basalt': Rock('igneous_extrusive', 'red'),
    'andesite': Rock('igneous_extrusive', 'red'),
    'dacite': Rock('igneous_extrusive', 'yellow'),
    'quartzite': Rock('metamorphic', 'white'),
    'slate': Rock('metamorphic', 'yellow'),
    'phyllite': Rock('metamorphic', 'brown'),
    'schist': Rock('metamorphic', 'green'),
    'gneiss': Rock('metamorphic', 'green'),
    'marble': Rock('metamorphic', 'yellow')
}

METAL_BLOCKS: Dict[str, MetalItem] = {
    'anvil': MetalItem('utility', 1400, 'tfc:block/anvil', None, False, False),
    'block': MetalItem('part', 100, 'block/block', None, False, False),
    'block_slab': MetalItem('part', 50, 'block/block', None, False, False),
    'block_stairs': MetalItem('part', 75, 'block/block', None, False, False),
    'bars': MetalItem('utility', 25, 'item/generated', None, False, False),
    'chain': MetalItem('utility', 6, 'tfc:block/chain', None, False, False),
    'lamp': MetalItem('utility', 100, 'tfc:block/lamp', None, False, False),
    'trapdoor': MetalItem('utility', 200, 'tfc:block/trapdoor', None, False, False)
}
METAL_ITEMS: Dict[str, MetalItem] = {
    'ingot': MetalItem('all', 100, 'item/generated', 'forge:ingots', True, False),
    'double_ingot': MetalItem('part', 200, 'item/generated', 'forge:double_ingots', False, False),
    'sheet': MetalItem('part', 200, 'item/generated', 'forge:sheets', False, False),
    'double_sheet': MetalItem('part', 400, 'item/generated', 'forge:double_sheets', False, False),
    'rod': MetalItem('part', 50, 'item/handheld_rod', 'forge:rods', False, False),
    'unfinished_lamp': MetalItem('utility', 100, 'item/generated', None, False, False),

    'tuyere': MetalItem('tool', 400, 'item/generated', None, False, True),
    'fish_hook': MetalItem('tool', 200, 'item/generated', None, False, False),
    'fishing_rod': MetalItem('tool', 200, 'item/generated', 'forge:fishing_rods', False, True),
    'pickaxe': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'pickaxe_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'shovel': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'shovel_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'axe': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'axe_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'hoe': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'hoe_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'chisel': MetalItem('tool', 100, 'tfc:item/handheld_flipped', None, False, True),
    'chisel_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'sword': MetalItem('tool', 200, 'item/handheld', None, False, True),
    'sword_blade': MetalItem('tool', 200, 'item/generated', None, True, False),
    'mace': MetalItem('tool', 200, 'item/handheld', None, False, True),
    'mace_head': MetalItem('tool', 200, 'item/generated', None, True, False),
    'saw': MetalItem('tool', 100, 'tfc:item/handheld_flipped', None, False, True),
    'saw_blade': MetalItem('tool', 100, 'item/generated', None, True, False),
    'javelin': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'javelin_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'hammer': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'hammer_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'propick': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'propick_head': MetalItem('tool', 100, 'item/generated', None, True, False),
    'knife': MetalItem('tool', 100, 'tfc:item/handheld_flipped', None, False, True),
    'knife_blade': MetalItem('tool', 100, 'item/generated', None, True, False),
    'scythe': MetalItem('tool', 100, 'item/handheld', None, False, True),
    'scythe_blade': MetalItem('tool', 100, 'item/generated', None, True, False),
    'shears': MetalItem('tool', 200, 'item/handheld', None, False, True),

    'unfinished_helmet': MetalItem('armor', 400, 'item/generated', None, False, False),
    'helmet': MetalItem('armor', 600, 'item/generated', None, False, True),
    'unfinished_chestplate': MetalItem('armor', 400, 'item/generated', None, False, False),
    'chestplate': MetalItem('armor', 800, 'item/generated', None, False, True),
    'unfinished_greaves': MetalItem('armor', 400, 'item/generated', None, False, False),
    'greaves': MetalItem('armor', 600, 'item/generated', None, False, True),
    'unfinished_boots': MetalItem('armor', 200, 'item/generated', None, False, False),
    'boots': MetalItem('armor', 400, 'item/generated', None, False, True),
    'horse_armor': MetalItem('armor', 1200, 'item/generated', None, False, False),

    'shield': MetalItem('tool', 400, 'item/handheld', None, False, True)
}
METAL_ITEMS_AND_BLOCKS: Dict[str, MetalItem] = {**METAL_ITEMS, **METAL_BLOCKS}
METAL_TOOL_HEADS = ('chisel', 'hammer', 'hoe', 'javelin', 'knife', 'mace', 'pickaxe', 'propick', 'saw', 'scythe', 'shovel', 'sword', 'axe')

METALS: Dict[str, Metal] = {
    'antimony': Metal(1, {'part'}, 0.14, 250, None),
    'aluminum': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.35, 1180, None),
    'florentine_bronze': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.35, 1000, None),
    'boron': Metal(2, {'part'}, 0.22, 575, None),
    'ferroboron': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1500, None),
    'cobalt': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1535, None),
    'constantan': Metal(2, {'part'}, 0.35, 1453, None),
    'electrum': Metal(3, {'part'}, 0.35, 1060, None),
    'invar': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1330, None),
    'lead': Metal(2, {'part'}, 0.14, 328, None),
    'britannium': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.22, 850, None),
    'purple_gold': Metal(2, {'part'}, 0.35, 1060, None),
    'uranium': Metal(3, {'part'}, 0.35, 1500, None),
    'pewter': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.14, 270, None),
    'iridium': Metal(3, {'part'}, 0.35, 1535, None),
    'osmium': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1535, None),
    'osmiridium': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1535, None),
    'unrefined_platinum': Metal(5, set(), 0.35, 1535, None),
    'platinum': Metal(5, {'part'}, 0.35, 1535, 'unrefined_titanium'),
    'platine_steel': Metal(6, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'unrefined_titanium': Metal(5, set(), 0.35, 1535, None),
    'titanium': Metal(5, {'part', 'tool', 'armor', 'utility'}, 0.35, 1535, 'unrefined_titanium'),
    'titan_steel': Metal(6, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'unrefined_tungsten': Metal(5, set(), 0.35, 1535, None),
    'tungsten': Metal(5, {'part'}, 0.35, 1535, 'unrefined_tungsten'),
    'tungsten_steel': Metal(6, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'weak_platine_steel': Metal(6, set(), 0.35, 1540, None),
    'weak_titan_steel': Metal(6, set(), 0.35, 1540, None),
    'weak_tungsten_steel': Metal(6, set(), 0.35, 1540, None),
    'high_carbon_platine_steel': Metal(6, set(), 0.35, 1540, None),
    'high_carbon_titan_steel': Metal(6, set(), 0.35, 1540, None),
    'high_carbon_tungsten_steel': Metal(6, set(), 0.35, 1540, None),
}

BLOOM_METALS = [
    'cobalt',
    'iridium',
    'osmium',
    'platinum',
    'titanium',
    'tungsten',
    'uranium'
]

ALLOYS: Dict[str, Tuple[Tuple[str, float, float], ...]] = {
    'florentine_bronze': (('copper', 0.88, 0.92), ('aluminum', 0.08, 0.12)),
    'ferroboron': (('iron', 0.5, 0.55), ('boron', 0.45, 0.5)),
    'constantan': (('copper', 0.5, 0.55), ('nickel', 0.45, 0.5)),
    'electrum': (('silver', 0.5, 0.55), ('gold', 0.45, 0.5)),
    'invar': (('nickel', 0.2, 0.4), ('iron', 0.6, 0.8)),
    'britannium': (('copper', 0.88, 0.92), ('antimony', 0.08, 0.12)),
    'purple_gold': (('gold', 0.88, 0.92), ('aluminum', 0.08, 0.12)),
    'osmiridium': (('iridium', 0.5, 0.55), ('osmium', 0.45, 0.5)),
    'pewter': (('antimony', 0.88, 0.92), ('bismuth', 0.08, 0.12)),
    'weak_platine_steel': (('black_steel', 0.5, 0.55), ('steel', 0.2, 0.25), ('platinum', 0.1, 0.15), ('pewter', 0.1, 0.15)),
    'weak_titan_steel': (('black_steel', 0.5, 0.55), ('steel', 0.2, 0.25), ('titanium', 0.1, 0.15), ('constantan', 0.1, 0.15)),
    'weak_tungsten_steel': (('black_steel', 0.5, 0.55), ('steel', 0.2, 0.25), ('titanium', 0.1, 0.15), ('electrum', 0.1, 0.15)),
}

ORES: Dict[str, Ore] = {
    'stibnite': Ore('antimony', True, 'copper', 'antimony'),
    'bauxite': Ore('aluminum', True, 'copper', 'aluminum'),
    'boracite': Ore('antimony', True, 'copper', 'boron'),
    'cobaltite': Ore('cobalt', True, 'bronze', 'cobalt'),
    'galena': Ore('lead', True, 'bronze', 'lead'),
    'native_iridium': Ore('iridium', True, 'bronze', 'iridium'),
    'native_osmium': Ore('osmium', True, 'bronze', 'osmium'),
    'native_platinum': Ore('unrefined_platinum', True, 'steel', 'platinum'),
    'rutile': Ore('unrefined_titanium', True, 'steel', 'titanium'),
    'wolframite': Ore('unrefined_tungsten', True, 'steel', 'tungsten'),
    'uraninite': Ore('uranium', True, 'bronze', 'uranium'),
}

ORE_GRADES: Dict[str, OreGrade] = {
    'normal': OreGrade(5),
    'poor': OreGrade(3),
    'rich': OreGrade(7)
}

POOR = 70, 25, 5  # = 1550
NORMAL = 35, 40, 25  # = 2400
RICH = 15, 25, 60  # = 2550

ORE_VEINS: dict[str, Vein] = {

    # the deeper you go, the less stibnite you find
    # antimony
    'surface_stibnite': Vein.new('stibnite', 34, 20, 40, 145, 0.25, ('claystone', 'limestone', 'conglomerate', 'chalk',), grade=POOR, deposits=True, indicator=20),
    'normal_stibnite': Vein.new('stibnite', 80, 15, 0, 70, 0.25, ('sedimentary',), grade=NORMAL, indicator=40),
    'deep_stibnite': Vein.new('stibnite', 50, 10, -80, 20, 0.5, ('sedimentary',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # aluminum
    'surface_bauxite': Vein.new('bauxite', 24, 20, 40, 145, 0.25, ('shale', 'chert', 'dolomite', 'conglomerate',), grade=POOR, deposits=True, indicator=14),
    'normal_bauxite': Vein.new('bauxite', 80, 20, 0, 70, 0.25, ('sedimentary',), grade=NORMAL, indicator=40),
    'deep_bauxite': Vein.new('bauxite', 50, 40, -70, 10, 0.11, ('sedimentary',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # lead
    'surface_galena': Vein.new('galena', 22, 18, 50, 145, 0.25, ('limestone', 'marble', 'metamorphic',), grade=POOR, deposits=True, indicator=14),
    'normal_galena': Vein.new('galena', 80, 20, 0, 70, 0.25, ('limestone', 'marble', 'metamorphic',), grade=NORMAL, indicator=40),
    'deep_galena': Vein.new('gelana', 50, 40, -70, 10, 0.11, ('limestone', 'marble', 'metamorphic',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # cobalt
    'normal_cobaltite': Vein.new('cobaltite', 85, 20, 0, 70, 0.25, ('metamorphic',), grade=NORMAL, indicator=40),
    'deep_cobaltite': Vein.new('cobaltite', 50, 40, -70, 10, 0.11, ('metamorphic',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # iridium
    'surface_native_iridium': Vein.new('native_iridium', 22, 18, 50, 135, 0.25, ('igneous_intrusive', 'igneous_extrusive',), grade=POOR, deposits=True, indicator=14),
    'normal_native_iridium': Vein.new('native_iridium', 80, 20, -40, 70, 0.25, ('igneous_intrusive',), grade=NORMAL, indicator=40),

    # osmium
    'surface_native_osmium': Vein.new('native_osmium', 22, 18, 50, 135, 0.25, ('igneous_intrusive', 'igneous_extrusive',), grade=POOR, deposits=True, indicator=14),
    'normal_native_osmium': Vein.new('native_osmium', 80, 20, -40, 70, 0.25, ('igneous_extrusive',), grade=NORMAL, indicator=40),

    # platinum
    'normal_native_platinum': Vein.new('native_platinum', 85, 20, 10, 80, 0.25, ('metamorphic',), grade=NORMAL, indicator=40),
    'deep_native_platinum': Vein.new('native_platinum', 40, 40, -70, 10, 0.11, ('metamorphic',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # titanium
    'normal_wolframite': Vein.new('wolframite', 80, 20, 0, 70, 0.25, ('metamorphic',), grade=NORMAL, indicator=40),
    'deep_wolframite': Vein.new('wolframite', 45, 40, -70, 10, 0.11, ('marble', 'gneiss', 'schist', 'granite', 'diorite',), grade=RICH, indicator=0, deep_indicator=(1, 4)),

    # uranium
    'normal_uraninite': Vein.new('uraninite', 80, 20, 0, 70, 0.25, ('igneous_intrusive', 'igneous_extrusive',), grade=NORMAL, indicator=40),
    'deep_uraninitee': Vein.new('uraninite', 45, 40, -70, 10, 0.11, ('andesite', 'dacite', 'marble', 'granite', 'diorite',), grade=RICH, indicator=0, deep_indicator=(1, 4)),
}

ORE_DEPOSITS = ('stibnite', 'bauxite', 'galena')

DEPOSITS_DICT: Dict[str, str] = {
    'stibnite': 'antimony',
    'bauxite': 'aluminum',
    'galena': 'lead'
}

POTTERY_MELT = 1400 - 1
POTTERY_HEAT_CAPACITY = 1.2  # Heat Capacity

ARMOR_SECTIONS = ('chestplate', 'leggings', 'boots', 'helmet')
TFC_ARMOR_SECTIONS = ('helmet', 'chestplate', 'greaves', 'boots')

DEFAULT_FORGE_ORE_TAGS: Tuple[str, ...] = ('coal', 'diamond', 'emerald', 'gold', 'iron', 'lapis', 'netherite_scrap', 'quartz', 'redstone')

DEPOSIT_RARES: Dict[str, str] = {
    'granite': 'topaz',
    'diorite': 'emerald',
    'gabbro': 'diamond',
    'shale': 'borax',
    'claystone': 'amethyst',
    'limestone': 'lapis_lazuli',
    'conglomerate': 'lignite',
    'dolomite': 'amethyst',
    'chert': 'ruby',
    'chalk': 'sapphire',
    'tuff': 'pyrite',
    'rhyolite': 'pyrite',
    'basalt': 'pyrite',
    'andesite': 'pyrite',
    'dacite': 'pyrite',
    'quartzite': 'opal',
    'slate': 'pyrite',
    'phyllite': 'pyrite',
    'schist': 'pyrite',
    'gneiss': 'gypsum',
    'marble': 'lapis_lazuli'
}

# This is here because it's used all over, and it's easier to import with all constants
def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()

DEFAULT_LANG = {
    **dict(('subtitles.item.armor.equip_%s' % metal, '%s armor equips' % lang(metal)) for metal, data in METALS.items() if 'armor' in data.types),

    # creative tabs
    'tfc_metallum_modern.creative_tab.ores': 'TFC Metallum: Modern Ores',
    'tfc_metallum_modern.creative_tab.metals': 'TFC Metallum: Modern Metal Stuffs',

    # metals
    **dict(('metal.tfc_metallum_modern.%s' % metal, lang(metal)) for metal in METALS.keys()),
}