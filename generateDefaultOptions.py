import sys

def main():
    name = sys.argv[1]
    formatted_name = format_name(name)

    with open('desc.txt', 'w') as f:

        f.writelines([
            f'    // {formatted_name} options\n',
            '    @SerialEntry\n',
            f'    public boolean {name}Enabled = true;\n',
            '    @SerialEntry\n',
            f'    public boolean {name}RandomSelection = true;\n',
            '    @SerialEntry\n',
            f'    public boolean {name}EnchantingTable = true;\n',
            '    @SerialEntry\n',
            f'    public boolean {name}BookOffer = true;\n\n'
        ])

        f.writelines([
            f'    private static final Collection<Option<?>> {name}Options = ImmutableList.of(\n',
            '            baseEnabled.binding(\n',
            '                            true,\n'
            f'                            () -> NewModConfig.CONFIG_HANDLER.instance().{name}Enabled,\n',
            f'                            newVal -> NewModConfig.CONFIG_HANDLER.instance().{name}Enabled = newVal\n',
            '                    )\n',
            '                    .build(),\n\n',

            '            baseRandomSelection.binding(\n',
            '                            true,\n'
            f'                            () -> NewModConfig.CONFIG_HANDLER.instance().{name}RandomSelection,\n',
            f'                            newVal -> NewModConfig.CONFIG_HANDLER.instance().{name}RandomSelection = newVal\n',
            '                    )\n',
            '                    .build(),\n\n',

            '            baseEnchantingTable.binding(\n',
            '                            true,\n'
            f'                            () -> NewModConfig.CONFIG_HANDLER.instance().{name}EnchantingTable,\n',
            f'                            newVal -> NewModConfig.CONFIG_HANDLER.instance().{name}EnchantingTable = newVal\n',
            '                    )\n',
            '                    .build(),\n\n',

            '            baseBookOffer.binding(\n',
            '                            true,\n'
            f'                            () -> NewModConfig.CONFIG_HANDLER.instance().{name}BookOffer,\n',
            f'                            newVal -> NewModConfig.CONFIG_HANDLER.instance().{name}BookOffer = newVal\n',
            '                    )\n',
            '                    .build()\n',
            '    );\n\n',


            f'    createOptionsGroup("{name}", {name}Options)\n\n',


            f'"qu-enchantments.config.{name}.group.title": "{formatted_name} Options",\n',
            f'"qu-enchantments.config.{name}.group.desc": "Configuration options for the {formatted_name} Enchantment",\n'
        ])

def format_name(name):
    new_str = ''

    for char in name:
        new_str += f' {char}' if char.isupper() else char

    return new_str.title()



if __name__ == '__main__':
    main()
