package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="livelierpokemon-abilities")
public class AbilityConfig implements ConfigData {
    @ConfigEntry.Category("Server Compatibility")
    @Comment("Toggles server-only mode which disables certain features so players don't need to download this mod.")
    public boolean SERVER_MODE = false;

    @ConfigEntry.Category("Abilities")
    @Comment("Explodes on death. Does not affect the world.")
    public boolean AFTERMATH = true;
    public boolean aftermath_affects_environment = false;
    public double aftermath_explosion_radius = 2;
    @Comment("Removes weather when nearby.")
    public boolean AIR_LOCK = true;
    @Comment("Immune to projectiles")
    public boolean ARMOR_TAIL = true;
    @Comment("Nearby players will take damage and wake up when trying to sleep.")
    public boolean BAD_DREAMS = true;
    public int bad_dreams_damage = 6;
    @Comment("Fetch thrown Pokeballs if they fail.")
    public boolean BALL_FETCH = true;
    public int ball_fetch_radius = 10;
    public boolean ball_fetch_show_message = true;
    @Comment("Nearby entities are weakened.")
    public boolean BEADS_OF_RUIN = true;
    @Comment("Immune to projectiles and explosions.")
    public boolean BULLETPROOF = true;
    @Comment("Removes weather when nearby.")
    public boolean CLOUD_NINE = true;
    @Comment("Chance to inflict mining fatigue when attacked.")
    public boolean CURSED_BODY = true;
    public double cursed_body_trigger_chance = 0.3;
    public int cursed_body_duration = 240;
    @Comment("Nearby Dark Pokemon gain Strength.")
    public boolean DARK_AURA = true;
    public int dark_aura_radius = 16;
    @Comment("Prevents Creepers from exploding.")
    public boolean DAMP = true;
    public int damp_radius = 4;
    @Comment("Grants resistance to self.")
    public boolean DAUNTLESS_SHIELD = true;
    @Comment("Immune to projectiles")
    public boolean DAZZLING = true;
    @Comment("Summon strong winds on spawn and gives nearby players Jump Boost and Slow Falling. Set duration to -1 for until switched out.")
    public boolean DELTA_STREAM = true;
    public int delta_stream_radius = 24;
    public int delta_stream_duration_seconds = -1;
    @Comment("Summon extremely harsh sun on spawn. Set duration to -1 for until switched out.")
    public boolean DESOLATE_LAND = true;
    public int desolate_land_radius = 24;
    public int desolate_land_duration_seconds = -1;
    @Comment("Spawn with 2 extra absorption hearts.")
    public boolean DISGUISE = true;
    @Comment("Summon rain on spawn and extinguish fire. Set duration to -1 for until switched out.")
    public boolean DRIZZLE = true;
    public int drizzle_radius = 16;
    public int drizzle_duration_seconds = 60;
    @Comment("Summon sun on spawn and gives nearby players Night Vision. Set duration to -1 for until switched out.")
    public boolean DROUGHT = true;
    public int drought_radius = 16;
    public int drought_duration_seconds = 60;
    @Comment("Chance to light attacker on fire when attacked.")
    public boolean FLAME_BODY = true;
    public double flame_body_trigger_chance = 0.3;
    public int flame_body_duration = 60;
    @Comment("Grants resistance to its owner.")
    public boolean FRIEND_GUARD = true;
    public int friend_guard_friendship_threshold = 220;
    @Comment("Immunity to all status effects.")
    public boolean FULL_METAL_BODY = true;
    @Comment("All nearby entities are immune to Slowness and nearby Electric Pokemon gain Strength.")
    public boolean ELECTRIC_SURGE = true;
    public boolean electric_surge_show_terrain = true;
    public int electric_surge_radius = 12;
    @Comment("Chance to inflict poison when attacked.")
    public boolean EFFECT_SPORE = true;
    public double effect_spore_trigger_chance = 0.3;
    public int effect_spore_duration = 120;
    @Comment("Nearby Fairy Pokemon gain Strength.")
    public boolean FAIRY_AURA = true;
    public int fairy_aura_radius = 16;
    @Comment("Immunity to fire and gain Strength.")
    public boolean FLARE_BOOST = true;
    @Comment("Immunity to fire and gain Strength.")
    public boolean FLASH_FIRE = true;
    @Comment("Immunity to all status effects.")
    public boolean GOOD_AS_GOLD = true;
    @Comment("Chance to inflict slowness when attacked.")
    public boolean GOOEY = true;
    public double gooey_trigger_chance = 0.3;
    public int gooey_duration = 240;
    @Comment("Periodically grows nearby crops and nearby Grass Pokemon gain Strength.")
    public boolean GRASSY_SURGE = true;
    public boolean grassy_surge_show_terrain = true;
    public int grassy_surge_radius = 12;
    @Comment("All nearby entities are immune to Slowness and nearby Electric Pokemon gain Strength.")
    public boolean HADRON_ENGINE = true;
    public boolean hadron_engine_show_terrain = true;
    public int hadron_engine_radius = 12;
    @Comment("Periodically cleanses all status effects on its owner.")
    public boolean HEALER = true;
    @Comment("Grants fire resistance to self.")
    public boolean HEATPROOF = true;
    @Comment("Grants strength to self.")
    public boolean HUGE_POWER = true;
    @Comment("Spawn with 2 extra absorption hearts.")
    public boolean ICE_FACE = true;
    @Comment("Nearby entities glow.")
    public boolean ILLUMINATE = true;
    public int illuminate_radius = 16;
    @Comment("Deals the same damage back to the attacker on death.")
    public boolean INNARDS_OUT = true;
    @Comment("Immunity to poison.")
    public boolean IMMUNITY = true;
    @Comment("Grants strength to self.")
    public boolean INTREPID_SWORD = true;
    @Comment("Inflicts damage when attacked.")
    public boolean IRON_BARBS = true;
    public int iron_barbs_damage = 4;
    @Comment("Lightning is redirected to this Pokemon")
    public boolean LIGHTNING_ROD = true;
    @Comment("Immune to Slowness and Paralysis")
    public boolean LIMBER = true;
    @Comment("Status effects are inflicted to the attacker instead.")
    public boolean MAGIC_BOUNCE = true;
    @Comment("Immunity to all status effects.")
    public boolean MAGIC_GUARD = true;
    @Comment("Nearby items are pulled to its owner.")
    public boolean MAGNET_PULL = true;
    public int magnet_pull_range = 6;
    @Comment("Status effects are inflicted to the attacker instead.")
    public boolean MIRROR_ARMOR = true;
    @Comment("All nearby entities are immune to negative status effects and nearby Dragon Pokemon gain Weakness.")
    public boolean MISTY_SURGE = true;
    public boolean misty_surge_show_terrain = true;
    public int misty_surge_radius = 12;
    @Comment("Summon sun on spawn and gives nearby players Night Vision. Set duration to -1 for until switched out.")
    public boolean ORICHALCUM_PULSE = true;
    public int orichalcum_pulse_radius = 16;
    public int orichalcum_pulse_duration_seconds = 60;
    @Comment("Periodically cleanses all status effects on its owner.")
    public boolean PASTEL_VEIL = true;
    @Comment("Chance to inflict wither when attacked.")
    public boolean PERISH_BODY = true;
    public double perish_body_trigger_chance = 0.3;
    public int perish_body_duration = 120;
    @Comment("Poison heals instead.")
    public boolean POISON_HEAL = true;
    @Comment("Chance to inflict poison when attacked.")
    public boolean POISON_POINT = true;
    public double poison_point_trigger_chance = 0.3;
    public int poison_point_duration = 120;
    @Comment("Grants strength to its owner.")
    public boolean POWER_SPOT = true;
    public int power_spot_friendship_threshold = 220;
    @Comment("Weapons that damage this Pokemon lose more durability.")
    public boolean PRESSURE = true;
    public int pressure_durability_loss = 3;
    @Comment("Summon heavy rain on spawn. Set duration to -1 for until switched out.")
    public boolean PRIMORDIAL_SEA = true;
    public int primordial_sea_radius = 24;
    public int primordial_sea_duration_seconds = -1;
    @Comment("All nearby projectiles stop and nearby Psychic Pokemon gain Strength.")
    public boolean PSYCHIC_SURGE = true;
    public boolean psychic_surge_show_terrain = true;
    public int psychic_surge_radius = 12;
    public int psychic_surge_block_radius = 6;
    @Comment("Grants strength to self.")
    public boolean PURE_POWER = true;
    @Comment("Immune to projectiles")
    public boolean QUEENLY_MAJESTY = true;
    @Comment("Inflicts damage when attacked.")
    public boolean ROUGH_SKIN = true;
    public int rough_skin_damage = 4;
    @Comment("Chance to inflict blindness when attacked.")
    public boolean SAND_SPIT = true;
    public double sand_spit_trigger_chance = 0.3;
    public int sand_spit_duration = 120;
    @Comment("Summon sandstorm on spawn and damages nearby monsters (excluding Husk). Set duration to -1 for until switched out.")
    public boolean SAND_STREAM = true;
    public int sand_stream_radius = 16;
    public int sand_stream_duration_seconds = 60;
    @Comment("Periodically grows nearby crops.")
    public boolean SEED_SOWER = true;
    public int seed_sower_radius = 16;
    @Comment("Summon snow on spawn and freezes nearby monsters (excluding Stray). Set duration to -1 for until switched out.")
    public boolean SNOW_WARNING = true;
    public int snow_warning_radius = 16;
    public int snow_warning_duration_seconds = 60;
    @Comment("Chance to inflict slowness when attacked.")
    public boolean STATIC = true;
    public double static_trigger_chance = 0.3;
    public int static_duration = 240;
    @Comment("Spawns with slowness and weakness.")
    public boolean SLOW_START = true;
    public int slow_start_duration = 240;
    @Comment("Grants speed to self.")
    public boolean SPEED_BOOST = true;
    @Comment("Periodically cleanses all status effects on its owner.")
    public boolean SWEET_VEIL = true;
    @Comment("Grants dolphin's grace to self.")
    public boolean SWIFT_SWIM = true;
    @Comment("Grants luck to its owner.")
    public boolean SERENE_GRACE = true;
    public int serene_grace_friendship_threshold = 220;
    @Comment("Status effects are inflicted to the attacker instead.")
    public boolean SYNCHRONIZE = true;
    @Comment("Nearby entities are weakened.")
    public boolean SWORD_OF_RUIN = true;
    @Comment("Nearby entities are weakened.")
    public boolean TABLET_OF_RUIN = true;
    @Comment("Chance to inflict slowness when attacked.")
    public boolean TANGLING_HAIR = true;
    public double tangling_hair_trigger_chance = 0.3;
    public int tangling_hair_duration = 240;
    @Comment("Chance to inflict poison when attacked.")
    public boolean TOXIC_DEBRIS = true;
    public double toxic_debris_trigger_chance = 1.0;
    public int toxic_debris_duration = 120;
    @Comment("Takes the same damage from all sources.")
    public boolean UNAWARE = true;
    public double unaware_damage = 4;
    @Comment("Nearby entities are weakened.")
    public boolean VESSEL_OF_RUIN = true;
    @Comment("Grants haste to its owner.")
    public boolean VICTORY_STAR = true;
    public int victory_star_friendship_threshold = 220;
    @Comment("Immunity to fire and gain Resistance.")
    public boolean WELL_BAKED_BODY = true;
    @Comment("Immunity to all damage except fire.")
    public boolean WONDER_GUARD = true;

    @ConfigEntry.Category("Types")
    @Comment("Electric Pokemon are immune to Slowness and Paralysis.")
    public boolean ELECTRIC = true;
    @Comment("Poison Pokemon are immune to poison.")
    public boolean POISON = true;
    @Comment("Steel Pokemon are immune to poison.")
    public boolean STEEL = true;

    @ConfigEntry.Category("Pokemon")
    @Comment("Abra randomly teleport around.")
    public boolean ABRA = true;
    @Comment("Drifloon will kidnap isolated villager babies and transform them into zombies after some time.")
    public boolean DRIFLOON = true;
    public int drifloon_radius = 16;
    public int drifloon_convert_time = 400;
    @Comment("Geodude can spawn rarely when stone is broken.")
    public boolean GEODUDE = true;
    public double geodude_spawn_rate = 0.002;
    @Comment("Golurk can be created using chiseled polished black tumblestone and a carved pumpkin.")
    public boolean GOLURK = true;
    @Comment("Kecleon turn invisible for a short time when attacked.")
    public boolean KECLEON = true;
    public int kecleon_duration = 120;
    @Comment("Suicune will purify nearby Zombie Villagers when holding a Golden Apple.")
    public boolean SUICUNE = true;
    public int suicune_purify_radius = 8;
    @Comment("Non-shiny Voltorb and Electrode will chase players and explode when close.")
    public boolean VOLTORB = true;
    public boolean voltorb_affects_environment = false;
    public double voltorb_explosion_radius = 1.5;
    public double electrode_explosion_radius = 2;

    @ConfigEntry.Category("Other")
    @Comment("Fire types, Bonsly and Sudowoodo look for cover in the rain.")
    public boolean AVOID_RAIN = true;
    @Comment("Creepers flee from cat Pokemon.")
    public boolean CREEPER_FLEE_CAT = true;
    @Comment("Skeletons flee from dog Pokemon.")
    public boolean SKELETON_FLEE_DOG = true;
}
