# livelierpokemon
Documentation on the list of features implemented.

## Effects applied to Owner
Applies an aura effect to the Pokemon's owner if they are within 8 blocks that lasts 16 seconds and is refreshed every 3 seconds. At base level applies effect level 1 but at 220 friendship it upgrades to level 2.
- Power Spot: Applies Strength
- Friend Guard: Applies Resistance
- Serene Grace: Applies Luck
- Victory Star: Applies Haste
- Healer / Pastel Veil / Sweet Veil: Periodically clears all status effects on the Owner
- Magnet Pull: Increases the item pickup radius of the Owner


## Effects applied to Pokemon
Applies a permanent* status effect on the Pokemon.
- Disguise / Ice Face: Applies Absorption
- Heatproof: Applies Fire Resistance
- Swift Swim: Applies Dolphin's Grace
- Intrepid Sword / Huge Power: Applies Strength
- Intrepid Sheild: Applies Resistance
- Speed Boost: Applies Speed
- Slow Start: Applies Weakness and Slowness for 10 seconds


## Effects applied to all Entities
Applies an aura effect on all nearby entities besides itself.
- Illuminate: Applies Glowing only when sent out by the Owner
- Tablets of Ruin / Beads of Ruin / Sword of Ruin / Vessel of Ruin: Applies Weakness


## Effects applied when a Pokemon is attacked.
Applies an effect when the Pokemon is attacked by another entity making contact (ie. not from a projectile)
- Rough Skin / Iron Barbs: Damages the attacker
- Effect Spore / Poison Point: Chance to inflict Poison
- Flame Body: Chance to light attacker on fire
- Cursed Body: Chance to inflict Mining Fatigue
- Tangling Hair / Static / Gooey: Chance to inflict Slowness
- Pressure: Items used to damage the Pokemon will lose durability faster
- Magic Bounce / Synchronize / Mirror Armor: Status effects will be reflected back to the attacker
- Aftermath: Will create an explosion on death that does not affect the environment


## Immunities
- Magic Guard / Good as Gold / Full Metal Body: Immune to all status effects
- Poison Types / Steel Types / Immunity: Immune to Poison
- Electric Types: Immune to Slowness
- Flash Fire: Immune to fire (if not already) and gains Strength 1
- Levitate: Immune to fall damage
- Wonder Guard: Can only be damaged by fire and lava


## Unique effects
Unique effects that are uncategorised.
- Ball Fetch: Will retrieve an Owner's Poke Ball that failed to capture a Pokemon
- Fire Types / Sudowoodo / Bonsly: Will escape to shade during rain and thunderstorm
- Bad Dreams: Players who sleep nearby will wake up and take damage
~~- Nosepass: Will naturally face 0,0~~ This is in the base mod now
- Unaware: The Pokemon will take fixed damage from an attacker no matter their weapon
- Lightning Rod: If lightning strikes nearby it will be redirected to the Pokemon and have no effect
- Abra: Will randomly teleport around
- Voltorb/Electrode: Will target nearby players and explode when nearby. Does not trigger on shinies


## Other
Vanilla Minecraft mechanics modified to incorporate Cobblemon.
- Golurk: You can craft Golurk using Oxidized Copper Blocks the same way you would build an Iron Golem
- Creepers: Creepers flee from cat Pokemon
- Mining: Mining in the overworld has a small chance to spawn a Geodude
