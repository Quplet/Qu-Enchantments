<img src="src/main/resources/assets/qu-enchantments/banner.png" width="2048">

# Qu Enchantments Mod (For Fabric)

Welcome! This is a mod adding various enchantments that ~~in my opinion~~ add much needed variation to the enchantments 
the game while maintaining a Vanilla feel.

This project is my first Fabric mod and is mostly me just learning my way around the modding environment. So while I'm 
proud of my work, I will not claim it is professional-level. I do think what I've done is cool, however.

## Installation

This mod requires Fabric. I have no plans at the moment for making a Forge equivalent. I have not extensively tested 
what Fabric Loader versions work and don't work. This mod uses Minecraft 1.18.2+, so I believe any Fabric Loader 
0.13.3 and beyond should work. This mod does utilize the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) 
so make sure you have that as well.

## Content

This mod currently has 5 new enchantments as of 0.5.5-1.18.2.

- Normal Enchantments
  - Freezing Aspect Enchantment (I-II) - Slows enemy on hit.
  - Leeching Aspect Enchantment (I-II) - Heals a small amount on hit.
  - Molten Walker Enchantment (I-II) (Work in Progress) - Harden's lava for a short time as you walk.

- Corrupted Enchantments 
  - Shaped Glass Enchantment (I-V) - Increased damage, but increases damage to item. Corrupts all damage enchantments.
  - Nightblood Enchantment (I-II) - Will oneshot any non-boss enemy (and Wither Skeletons), but will drain your xp, hunger,
then health while held. Corrupts all aspect enchantments.

Corrupted Enchantments are a new type of enchantments that offer a powerful ability with a drawback. They will consume 
other enchantments of the same type. Might want to pick your battles wisely with these...

## Modpacks

If you want to use this mod for a modpack, I've put in some features to allow for more compatibility between the 
functionality of my mod and others that add enchantments.

Corrupted enchantments are sorted by categories based on their effect. Right now there are only two that do anything: 
`Damage` and `Aspect`. `Walker` also exists, but currently has no corrupted enchantments for it. (Coming soon)

You can add or edit what enchantments fall under which category by changing the .json files in `data/qu-enchantments/tags/enchantment/`.

## License

Copyright 2022 Quplet, Apache License 2.0. Please credit if you use or distribute my work.
