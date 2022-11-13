import sys


def main():
    name = sys.argv[1]
    fname = format_name(name)

    f = open('desc.txt', 'w')

    f.writelines([f'"text.autoconfig.qu-enchantments.option.{name}": "{fname}",\n',
                  f'"text.autoconfig.qu-enchantments.option.{name}.isEnabled": "Is Enabled: ",\n',
                  f'"text.autoconfig.qu-enchantments.option.{name}.randomSelection": "Spawn As Loot: ",\n',
                  f'"text.autoconfig.qu-enchantments.option.{name}.enchantingTable": "Available On Enchanting Table: '
                  '",\n',
                  f'"text.autoconfig.qu-enchantments.option.{name}.bookOffer": "Is Tradable: ",\n\n'])
    f.writelines([f'public static class {name[0].upper() + name[1:]} {{\n',
                  '\tpublic boolean isEnabled = true;\n',
                  '\tpublic boolean randomSelection = true;\n',
                  '\tpublic boolean enchantingTable = true;\n',
                  '\tpublic boolean bookOffer = true;\n}\n\n'])
    f.writelines(['@ConfigEntry.Gui.CollapsibleObject\n',
                  f'public static final {name[0].upper() + name[1:]} {name} = new {name[0].upper() + name[1:]}();\n'])

    f.close()


def format_name(name):
    string = ''
    n = 0
    for i in range(len(name)):
        if name[i].isupper():
            string += name[n:i] + ' '
            n = i
    string += name[n:]
    return string[0].upper() + string[1:]


if __name__ == '__main__':
    main()
