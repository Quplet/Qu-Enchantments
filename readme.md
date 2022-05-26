<img src="src/main/resources/assets/qu-enchantments/banner.png" width="2048">

# Qu Enchantments Mod (For Fabric)

Welcome! This is a mod adding various enchantments that ~~in my opinion~~ add much needed variation to the enchantments 
in the game while maintaining a Vanilla feel.

This project is my first Fabric mod and is mostly me just learning my way around the modding environment. So while I'm 
proud of my work, I will not claim it is professional-level. I do think what I've done is cool, however.

## Installation

This mod requires Fabric. I have no plans at the moment for making a Forge equivalent. I have not extensively tested 
what Fabric Loader versions work and don't work. This mod uses Minecraft 1.19+, so I believe any Fabric Loader 
0.14.5 and beyond should work. This mod does utilize the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) 
so make sure you have that as well.

## Content

This mod currently has 11 new enchantments as of 1.0.0-1.19.

- Normal Enchantments
  - Freezing Aspect Enchantment (I-II) (Sword) - Slows enemy on hit.
  - Leeching Aspect Enchantment (I-II) (Sword) - Heals a small amount on hit.
  - Molten Walker Enchantment (I-II) (Boots) (Textures are a work in Progress) - Harden's lava for a short time as you walk.
  - Bashing Enchantment (Shield) - When hit while guarding, knocks your attacker back a small amount.
  - Blessing of Speed (Rune) - Gives a small passive speed boost.
  - Blessing of Regeneration (Rune) - Gives a passive increase to natural regeneration.
  - Blessing of Aggression (Rune) - Gives a small passive increase to attack speed.

- Corrupted Enchantments 
  - Shaped Glass Enchantment (I-V) (Sword/Axe) - Increased damage, but increases damage to item. Corrupts all damage enchantments.
  - Nightblood Enchantment (I-II) (Sword) - Will oneshot any non-boss enemy (and Wither Skeletons), but will drain your xp, hunger,
then health while held. Corrupts all aspect enchantments.
  - Skywalker Enchantment (I-II) (Boots) - While crouching, will condense cloud blocks underneath you for you to walk on. 
  Corrupts all walker enchantments.
  - Essence of Ender (I-III) (Armor) - Chance for an enemy to randomly teleport a short distance away when they attack you. 
  While wearing, water and rain have a chance to teleport and harm the wearer. Corrupts all thorns enchantments.

Corrupted Enchantments are a new type of enchantments that offer a powerful ability with a drawback. They will consume 
other enchantments of the same type. Might want to pick your battles wisely with these...

## Modpacks

If you want to use this mod for a modpack, I've put in some features to allow for more compatibility between the 
functionality of my mod and others that add enchantments.

Corrupted enchantments are sorted by categories based on their effect. Right now there are only four that do anything: 
`Damage`, `Aspect`, `Walker`, and `Thorns`.

You can add or edit what enchantments fall under which category by changing the .json files in `data/qu-enchantments/tags/enchantment/`.

The Nightblood corrupted enchantment will one-hit kill every entity not listed in 
`data/qu-enchantments/tags/entity_types/nightblood_immune_entities.json`.

## Dependencies and Credits

This mod relies on [Fabric-ASM](https://github.com/Chocohead/Fabric-ASM) by Chocohead. It is built into the mod, so there is no need to download anything.
Thanks to E_Leven for all the textures I requested made (Hot Obsidian Block).

## License

Copyright 2022 Quplet, Apache License 2.0. Please credit if you use or distribute my work.
