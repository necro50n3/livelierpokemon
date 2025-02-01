# livelierpokemon
Documentation on the list of features implemented.

## Status Effects
- Paralysis: Applies slowness and periodically freezes entity

## Owner Effects
Applies an aura effect to the Pokemon's owner. At base level applies effect level 1 but at increased friendship it upgrades to level 2.
- Power Spot: Applies Strength
- Friend Guard: Applies Resistance
- Serene Grace: Applies Luck
- Victory Star: Applies Haste
- Healer / Pastel Veil / Sweet Veil: Periodically clears all status effects on the Owner
- Magnet Pull: Increases the item pickup radius of the Owner


## Self Effects
Applies a permanent* status effect on the Pokemon.
- Disguise / Ice Face: Applies Absorption
- Heatproof: Applies Fire Resistance
- Swift Swim: Applies Dolphin's Grace
- Intrepid Sword / Huge Power / Pure Power: Applies Strength
- Dauntless Shield: Applies Resistance
- Speed Boost: Applies Speed
- Slow Start: Applies Weakness and Slowness for 10 seconds
- Poison Heal: Poison heals instead of dealing damage


## Aura Effects
Applies an aura effect on all valid targets.
- Illuminate: Applies Glowing only when sent out by a Player
- Tablets of Ruin / Beads of Ruin / Sword of Ruin / Vessel of Ruin: Applies Weakness
- Seed Sower: Increases rate of crop growth
- Electric Surge / Hadron Engine: All nearby entities are immune to Slowness and nearby Electric Pokemon gain Strength
- Grassy Surge / Seed Sower: Increases rate of crop growth, entities heal slowly and nearby Grass Pokemon gain Strength
- Misty Surge: All nearby entities are immune to negative status effects and nearby Dragon Pokemon gain Weakness
- Psychic Surge: All nearby projectiles stop and nearby Psychic Pokemon gain Strength


## Revenge Effects.
Applies an effect when the Pokemon is attacked by another entity making contact (ie. not from a projectile)
- Rough Skin / Iron Barbs: Damages the attacker
- Effect Spore / Poison Point / Toxic Debris: Chance to inflict Poison
- Flame Body: Chance to light attacker on fire
- Cursed Body: Chance to inflict Mining Fatigue
- Tangling Hair / Gooey: Chance to inflict Slowness
- Static: Change to inflict Paralysis
- Pressure: Items used to damage the Pokemon will lose durability faster
- Magic Bounce / Synchronize / Mirror Armor: Status effects will be reflected back to the attacker
- Aftermath: Will create an explosion on death that does not affect the environment
- Innards Out: Deals the same damage back to the attacker on death


## Immunities
- Magic Guard / Good as Gold / Full Metal Body: Immune to all status effects
- Poison Types / Steel Types / Immunity: Immune to Poison
- Electric Types / Limber: Immune to Slowness and Paralysis
- Flash Fire / Flare Boost: Immune to fire (if not already) and gains Strength
- Well-Baked Body: Immune to fire and gains Resistance
- Wonder Guard: Can only be damaged by attacks it would be weak to, but only has 1 hp
- Queenly Majesty / Armor Tail / Dazzling: Immune to projectiles
- Bulletproof: Immune to projectiles and explosions


## Unique Effects
Unique effects that are uncategorised.
- Ball Fetch: Will retrieve an Owner's Poke Ball that failed to capture a Pokemon
- Fire Types / Sudowoodo / Bonsly: Will escape to shade during rain and thunderstorm
- Bad Dreams: Players who sleep nearby will wake up and take damage
- Unaware: The Pokemon takes fixed damage from all sources
- Lightning Rod: If lightning strikes nearby it will be redirected to the Pokemon and have no effect
- Abra: Will randomly teleport around
- Voltorb/Electrode: Will target nearby players and explode when nearby. Does not trigger on shiny Pokemon
- Damp: Prevents Creepers, Voltorb and Electrode from exploding


## Other
Vanilla Minecraft mechanics modified to incorporate Cobblemon.
- Golurk: You can craft Golurk using Oxidized Copper Blocks the same way you would build an Iron Golem
- Creepers: Creepers flee from cat Pokemon
- Skeletons: Skeletons flee from dog Pokemon
- Mining: Mining in the overworld has a small chance to spawn a Geodude