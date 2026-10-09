from mcresources import ResourceManager, utils
from constants import *;
from assets import generate as generateAssets;
from recipes import generate as generateRecipes;
from data import generate as generateData;
from tags import generate as generateTags;
from argparse import ArgumentParser

RESOURCE_DIR = 'src/generated/resources'

def main(): 
    parser = ArgumentParser(description='Entrypoint for all common scripting infrastructure.')
    parser.add_argument('actions', nargs='+', choices=(
        'all',  # generate all resources (assets / data / book)
        'clean',  # clean all resources (assets / data), including book
    ))

    rm = ResourceManager('tfc_metallum_modern', resource_dir=RESOURCE_DIR)

    args = parser.parse_args()

    for action in args.actions:
        if(action == 'all'):
            rm.lang(DEFAULT_LANG)
            generateAssets(rm);
            generateData(rm);
            generateRecipes(rm);
            generateTags(rm);

            rm.flush()

if __name__ == '__main__':
    main()