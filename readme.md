<img src="src/main/resources/assets/qu-enchantments/banner.png" width="2048" alt="banner">

# Qu Enchantments Mod (For Fabric/Quilt)

Welcome! This is a mod adding various enchantments that ~~in my opinion~~ add much needed variation to the enchantments 
in the game while not straying too far from vanilla. It's not just your ordinary More Enchants mod however, I have my own little twist inside...

## 🧰 Installation

This mod requires the Fabric or Quilt loader. I have no plans at the moment for making a Forge equivalent. I have not extensively 
tested what Fabric Loader versions work and don't work. This mod uses Minecraft 1.19+, so I believe any Fabric Loader 
0.14.6 and beyond should work. This mod does utilize the [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) 
so make sure you have that as well.

## 📖 Content

This mod currently has 18 new enchantments and one new item as of 1.2.0-1.19.

<details>
  <summary>Click to expand</summary>

- 📘 Normal Enchantments
  - Freezing Aspect Enchantment (I-II) (Sword) - Slows enemy on hit.
  - Leeching Aspect Enchantment (I-II) (Sword) - Heals a small amount on hit.
  - Inane Aspect Enchantment (I-II) (Sword) - Nullifies all Protection enchantments, blessings, and Totems of Undying for a short duration.
  - Molten Walker Enchantment (I-II) (Boots) - Hardens lava for a short time as you walk.
  - Bashing Enchantment (Shield) - When hit while guarding, knocks your attacker back a small amount.
  - Reflection Enchantment (I-III) (Shield) - Blocked persistent projectiles (arrows and tridents) will be reflected back 
    in the direction you're looking.
  - Accuracy Enchantment (I-II) (Crossbow) - Increases the accuracy of your shot.
  - Arrow's Flight (I-II) (Bow) - Increases the arrow speed of your shot.
  - Blessing of Speed (Rune) - Gives a small passive speed boost.
  - Blessing of Regeneration (Rune) - Gives a passive increase to natural regeneration.
  - Blessing of Aggression (Rune) - Gives a small passive increase to attack speed.
  - Fidelity (Horse Armor) - Horses with this enchantment on their armor will follow and teleport to its bonded player. 
  Any player that tries to ride the horse while it's trying to reach you will be dismounted.

- 📕 Curses
  - Curse of Agitation - Mobs will become hostile to anything wearing this curse.

- 🎱 Corrupted Enchantments - A new type of enchantments that offer a powerful ability with a drawback. They will consume
  other enchantments of the same type to increase their level. Multiple corrupted enchantments cannot be placed on the same item. They can be found
  in various high level loot chests. Might want to pick your battles wisely with these...
  - Shaped Glass Enchantment (I-V) (Sword/Axe) - Increased damage, but increases damage to item. Corrupts all damage enchantments.
  - Nightblood Enchantment (I-II) (Sword) - Will oneshot any non-boss enemy, but will drain your xp, hunger, then health
    while held. Wither Skeletons and the Warden are also immune. Corrupts all aspect enchantments.
  - Skywalker Enchantment (I-II) (Boots) - While crouching, will condense cloud blocks underneath you for you to walk on.
    Remain crouching or you will fall through. Corrupts all walker enchantments.
  - Essence of Ender (I-III) (Armor) - When hit, will teleport the attacker away from the wearer a short distance.
    While wearing, water and rain have a chance to teleport and harm the wearer. Corrupts all thorns enchantments.
  - Omen of Immunity (I-V) (Rune) - While held, you become immune to all effects, positive or negative. You will still take
    damage from fire and lava, but will not remain on fire. Nightblood cannot instantly kill the holder. Corrupts all
    rune enchantments.
  - Strip Miner (I-II) (Mining Tools) - After mining a block, the tool-compatible blocks around it will be destroyed for
    a clear path. These blocks, including the mined one, will not drop their spoils. Corrupts all spoils enchantments
    (Fortune, Silk touch).

- 💎 Items
  - Rune - A new item that can be enchanted for passive effects while held. While being used, the damage meter will drain
    depending on the amount of enchantments present and the rune variant. A rune with a corrupted enchantment will not
    regenerate and will break upon reaching max damage. Use sparingly. There are nine rune variants, each acquired differently.
    Higher level runes will have more charge. Librarian villagers and Wandering Traders can have a rune trade.

- 🛠️ Vanilla Changes
  - Shields can be enchanted at an enchanting table.
  - Horse Armor can be enchanted at an enchanting table with Protection and Thorns enchantments.
</details>

## ⚙ Modpacks
<details>
  <summary>Click to expand</summary>

If you want to use this mod for a modpack, I've put in some features to allow for more compatibility between the
functionality of my mod and others that add enchantments.

Corrupted enchantments are sorted by categories based on their effect. Right now there are only five that do anything:
`Damage`, `Aspect`, `Walker`, `Thorns`, `Mining Tool Drop` and `Rune`.

You can add or edit what enchantments fall under which category by changing the .json files in `data/qu-enchantments/tags/enchantment/`.

The Nightblood corrupted enchantment will one-hit kill every entity not listed in
`data/qu-enchantments/tags/entity_types/nightblood_immune_entities.json`.
</details>



## 📝 Dependencies and Credits

This mod relies on [Fabric-ASM](https://github.com/Chocohead/Fabric-ASM) by Chocohead. It is built into the mod, so there 
is no need to download anything.

Thanks to [E_Leven](https://www.instagram.com/evan_cdg/) for all the textures I requested made (Hot Obsidian Block and the Rune textures).

## 📜 License

Copyright 2022 Quplet, Apache License 2.0. Please credit if you use or distribute my work.
