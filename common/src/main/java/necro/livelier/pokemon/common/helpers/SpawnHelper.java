package necro.livelier.pokemon.common.helpers;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.events.BallFetchEvent;
import necro.livelier.pokemon.common.goals.*;
import necro.livelier.pokemon.common.registries.EffectRegistry;
import necro.livelier.pokemon.common.registries.EventRegistry;
import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.*;
import java.util.function.Consumer;

public class SpawnHelper {
    private static final Map<String, Consumer<PokemonEntity>> ON_SEND = new HashMap<>();
    private static final Map<String, Consumer<PokemonEntity>> ON_SPAWN = new HashMap<>();
    private static final TriConsumer<PokemonEntity, Integer, Goal> goalHelper = (pokemonEntity, priority, goal) -> {
        pokemonEntity.goalSelector.addGoal(priority, goal);
    };

    public static void init() {
        registerEvents();

        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.BEADS_OF_RUIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) ->
                goalHelper.accept(pokemonEntity, 3, new EntityEffectGoal(pokemonEntity, MobEffects.WEAKNESS, false));
            ON_SEND.putIfAbsent("beadsofruin", cons);
            ON_SPAWN.putIfAbsent("beadsofruin", cons);
        }
        if (config.CURSED_BODY) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.DIG_SLOWDOWN, config.cursed_body_duration, config.cursed_body_trigger_chance)
            );
            ON_SEND.putIfAbsent("cursedbody", cons);
            ON_SPAWN.putIfAbsent("cursedbody", cons);
        }
        if (config.DARK_AURA) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.dark_aura_radius, null, (entity) -> SpawnHelper.isType(entity, "dark"))
            );
            ON_SEND.putIfAbsent("darkaura", cons);
            ON_SPAWN.putIfAbsent("darkaura", cons);
        }
        if (config.DAUNTLESS_SHIELD) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.DAMAGE_RESISTANCE, 240, 0)
            );
            ON_SEND.putIfAbsent("dauntlessshield", cons);
            ON_SPAWN.putIfAbsent("dauntlessshield", cons);
        }
        if (config.DISGUISE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> pokemonEntity.addEffect(
                new MobEffectInstance(MobEffects.ABSORPTION, -1, 0)
            );
            ON_SEND.putIfAbsent("disguise", cons);
            ON_SPAWN.putIfAbsent("disguise", cons);
        }
        if (config.EFFECT_SPORE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.POISON, config.effect_spore_duration, config.effect_spore_trigger_chance)
            );
            ON_SEND.putIfAbsent("effectspore", cons);
            ON_SPAWN.putIfAbsent("effectspore", cons);
        }
        if (config.ELECTRIC_SURGE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.electric_surge_radius,
                    config.electric_surge_show_terrain ? ParticleRegistry.ELECTRIC_TERRAIN : null, (entity) -> SpawnHelper.isType(entity, "electric")
                )
            );
            ON_SEND.putIfAbsent("electricsurge", cons);
            ON_SPAWN.putIfAbsent("electricsurge", cons);
        }
        if (config.FAIRY_AURA) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.fairy_aura_radius, null, (entity) -> SpawnHelper.isType(entity, "fairy"))
            );
            ON_SEND.putIfAbsent("fairyaura", cons);
            ON_SPAWN.putIfAbsent("fairyaura", cons);
        }
        if (config.FLAME_BODY) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FlameBodyGoal(pokemonEntity, (float) config.flame_body_trigger_chance, config.flame_body_duration)
            );
            ON_SEND.putIfAbsent("flamebody", cons);
            ON_SPAWN.putIfAbsent("flamebody", cons);
        }
        if (config.FRIEND_GUARD) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerEffectGoal(pokemonEntity, MobEffects.DAMAGE_RESISTANCE, config.friend_guard_friendship_threshold)
            );
            ON_SEND.putIfAbsent("friendguard", cons);
        }
        if (config.GOOEY) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.MOVEMENT_SLOWDOWN, config.gooey_duration, config.gooey_trigger_chance)
            );
            ON_SEND.putIfAbsent("gooey", cons);
            ON_SPAWN.putIfAbsent("gooey", cons);
        }
        if (config.GRASSY_SURGE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> {
                goalHelper.accept(pokemonEntity, 3, new CropGrowthGoal(pokemonEntity, config.grassy_surge_radius));
                goalHelper.accept(pokemonEntity, 3, new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.grassy_surge_radius,
                    config.grassy_surge_show_terrain ? ParticleRegistry.GRASSY_TERRAIN : null, (entity) -> SpawnHelper.isType(entity, "grass")));
            };
            ON_SEND.putIfAbsent("grassysurge", cons);
            ON_SPAWN.putIfAbsent("grassysurge", cons);
        }
        if (config.HADRON_ENGINE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.hadron_engine_radius,
                    config.hadron_engine_show_terrain ? ParticleRegistry.ELECTRIC_TERRAIN : null, (entity) -> SpawnHelper.isType(entity, "electric")
                )
            );
            ON_SEND.putIfAbsent("hadronengine", cons);
            ON_SPAWN.putIfAbsent("hadronengine", cons);
        }
        if (config.HEALER) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerCleanseGoal(pokemonEntity)
            );
            ON_SEND.putIfAbsent("healer", cons);
        }
        if (config.HEATPROOF) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.FIRE_RESISTANCE, 240, 0)
            );
            ON_SEND.putIfAbsent("heatproof", cons);
            ON_SPAWN.putIfAbsent("heatproof", cons);
        }
        if (config.HUGE_POWER) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, 240, 0)
            );
            ON_SEND.putIfAbsent("hugepower", cons);
            ON_SPAWN.putIfAbsent("hugepower", cons);
        }
        if (config.ICE_FACE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> pokemonEntity.addEffect(
                new MobEffectInstance(MobEffects.ABSORPTION, -1, 0)
            );
            ON_SEND.putIfAbsent("iceface", cons);
            ON_SPAWN.putIfAbsent("iceface", cons);
        }
        if (config.ILLUMINATE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new IlluminateGoal(pokemonEntity, config.illuminate_radius)
            );
            ON_SEND.putIfAbsent("illuminate", cons);
        }
        if (config.INTREPID_SWORD) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, 240, 0)
            );
            ON_SEND.putIfAbsent("intrepidsword", cons);
            ON_SPAWN.putIfAbsent("intrepidsword", cons);
        }
        if (config.IRON_BARBS) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new DamageRevengeGoal(pokemonEntity, config.iron_barbs_damage)
            );
            ON_SEND.putIfAbsent("ironbarbs", cons);
            ON_SPAWN.putIfAbsent("ironbarbs", cons);
        }
        if (config.MAGNET_PULL) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new MagnetPullGoal(pokemonEntity, config.magnet_pull_range)
            );
            ON_SEND.putIfAbsent("magnetpull", cons);
        }
        if (config.MISTY_SURGE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new FieldEffectGoal(pokemonEntity, MobEffects.WEAKNESS, config.misty_surge_radius,
                    config.misty_surge_show_terrain ? ParticleRegistry.MISTY_TERRAIN : null, (entity) -> SpawnHelper.isType(entity, "dragon")
                )
            );
            ON_SEND.putIfAbsent("mistysurge", cons);
            ON_SPAWN.putIfAbsent("mistysurge", cons);
        }
        if (config.PASTEL_VEIL) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerCleanseGoal(pokemonEntity)
            );
            ON_SEND.putIfAbsent("pastelveil", cons);
        }
        if (config.PERISH_BODY) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.WITHER, config.perish_body_duration, config.perish_body_trigger_chance)
            );
            ON_SEND.putIfAbsent("perishbody", cons);
            ON_SPAWN.putIfAbsent("perishbody", cons);
        }
        if (config.POISON_POINT) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.POISON, config.poison_point_duration, config.poison_point_trigger_chance)
            );
            ON_SEND.putIfAbsent("poisonpoint", cons);
            ON_SPAWN.putIfAbsent("poisonpoint", cons);
        }
        if (config.POWER_SPOT) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.power_spot_friendship_threshold)
            );
            ON_SEND.putIfAbsent("powerspot", cons);
        }
        if (config.PRESSURE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new PressureGoal(pokemonEntity, config.pressure_durability_loss)
            );
            ON_SEND.putIfAbsent("pressure", cons);
            ON_SPAWN.putIfAbsent("pressure", cons);
        }
        if (config.PSYCHIC_SURGE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> {
                goalHelper.accept(pokemonEntity, 3, new FieldEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, config.psychic_surge_radius,
                    config.psychic_surge_show_terrain ? ParticleRegistry.PSYCHIC_TERRAIN : null, (entity) -> SpawnHelper.isType(entity, "psychic")
                ));
                goalHelper.accept(pokemonEntity, 3, new BlockProjectileGoal(pokemonEntity, config.psychic_surge_block_radius));
            };
            ON_SEND.putIfAbsent("psychicsurge", cons);
            ON_SPAWN.putIfAbsent("psychicsurge", cons);
        }
        if (config.PURE_POWER) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.DAMAGE_BOOST, 240, 0)
            );
            ON_SEND.putIfAbsent("purepower", cons);
            ON_SPAWN.putIfAbsent("purepower", cons);
        }
        if (config.ROUGH_SKIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new DamageRevengeGoal(pokemonEntity, config.rough_skin_damage)
            );
            ON_SEND.putIfAbsent("roughskin", cons);
            ON_SPAWN.putIfAbsent("roughskin", cons);
        }
        if (config.SAND_SPIT) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.BLINDNESS, config.sand_spit_duration, config.sand_spit_trigger_chance)
            );
            ON_SEND.putIfAbsent("sandspit", cons);
            ON_SPAWN.putIfAbsent("sandspit", cons);
        }
        if (config.SEED_SOWER) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new CropGrowthGoal(pokemonEntity, config.seed_sower_radius)
            );
            ON_SEND.putIfAbsent("seedsower", cons);
            ON_SPAWN.putIfAbsent("seedsower", cons);
        }
        if (config.STATIC) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, EffectRegistry.PARALYSIS, config.static_duration, config.static_trigger_chance)
            );
            ON_SEND.putIfAbsent("static", cons);
            ON_SPAWN.putIfAbsent("static", cons);
        }
        if (config.SLOW_START) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> {
                pokemonEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, config.slow_start_duration, 1));
                pokemonEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, config.slow_start_duration, 1));
            };
            ON_SEND.putIfAbsent("slowstart", cons);
            ON_SPAWN.putIfAbsent("slowstart", cons);
        }
        if (config.SPEED_BOOST) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.MOVEMENT_SPEED, 240, 0)
            );
            ON_SEND.putIfAbsent("speedboost", cons);
            ON_SPAWN.putIfAbsent("speedboost", cons);
        }
        if (config.SWEET_VEIL) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerCleanseGoal(pokemonEntity)
            );
            ON_SEND.putIfAbsent("sweetveil", cons);
        }
        if (config.SWIFT_SWIM) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new SelfEffectGoal(pokemonEntity, MobEffects.DOLPHINS_GRACE, 240, 0)
            );
            ON_SEND.putIfAbsent("swiftswim", cons);
            ON_SPAWN.putIfAbsent("swiftswim", cons);
        }
        if (config.SERENE_GRACE) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerEffectGoal(pokemonEntity, MobEffects.LUCK, config.serene_grace_friendship_threshold)
            );
            ON_SEND.putIfAbsent("serenegrace", cons);
        }
        if (config.SWORD_OF_RUIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) ->
                goalHelper.accept(pokemonEntity, 3, new EntityEffectGoal(pokemonEntity, MobEffects.WEAKNESS, false));
            ON_SEND.putIfAbsent("swordofruin", cons);
            ON_SPAWN.putIfAbsent("swordofruin", cons);
        }
        if (config.TABLET_OF_RUIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) ->
                goalHelper.accept(pokemonEntity, 3, new EntityEffectGoal(pokemonEntity, MobEffects.WEAKNESS, false));
            ON_SEND.putIfAbsent("tabletofruin", cons);
            ON_SPAWN.putIfAbsent("tabletofruin", cons);
        }
        if (config.TANGLING_HAIR) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.MOVEMENT_SLOWDOWN, config.tangling_hair_duration, config.tangling_hair_trigger_chance)
            );
            ON_SEND.putIfAbsent("tanglinghair", cons);
            ON_SPAWN.putIfAbsent("tanglinghair", cons);
        }
        if (config.TOXIC_DEBRIS) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new EffectRevengeGoal(pokemonEntity, MobEffects.POISON, config.toxic_debris_duration, config.toxic_debris_trigger_chance)
            );
            ON_SEND.putIfAbsent("toxicdebris", cons);
            ON_SPAWN.putIfAbsent("toxicdebris", cons);
        }
        if (config.VESSEL_OF_RUIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) ->
                goalHelper.accept(pokemonEntity, 3, new EntityEffectGoal(pokemonEntity, MobEffects.WEAKNESS, false));
            ON_SEND.putIfAbsent("vesselofruin", cons);
            ON_SPAWN.putIfAbsent("vesselofruin", cons);
        }
        if (config.VICTORY_STAR) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new OwnerEffectGoal(pokemonEntity, MobEffects.DIG_SPEED, config.victory_star_friendship_threshold)
            );
            ON_SEND.putIfAbsent("victorystar", cons);
        }
        if (config.WONDER_GUARD) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> {
                if (pokemonEntity.isDeadOrDying()) return;
                AttributeInstance attribute = pokemonEntity.getAttribute(Attributes.MAX_HEALTH);
                if (attribute == null) return;
                attribute.setBaseValue(1f);
                pokemonEntity.setHealth(1f);
            };
            ON_SEND.putIfAbsent("wonderguard", cons);
            ON_SPAWN.putIfAbsent("wonderguard", cons);
        }

        if (config.ABRA) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new RandomTeleportGoal(pokemonEntity)
            );
            ON_SPAWN.putIfAbsent("Abra", cons);
        }
        if (config.KECLEON) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new KecleonGoal(pokemonEntity, config.kecleon_duration)
            );
            ON_SEND.putIfAbsent("Kecleon", cons);
            ON_SPAWN.putIfAbsent("Kecleon", cons);
        }
        if (config.VOLTORB) {
            Consumer<PokemonEntity> voltorb_cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new VoltorbExplosionGoal(pokemonEntity, config.voltorb_affects_environment, config.voltorb_explosion_radius)
            );
            Consumer<PokemonEntity> electrode_cons = (pokemonEntity) -> goalHelper.accept(pokemonEntity, 3,
                new VoltorbExplosionGoal(pokemonEntity, config.voltorb_affects_environment, config.electrode_explosion_radius)
            );
            ON_SPAWN.putIfAbsent("Voltorb", voltorb_cons);
            ON_SPAWN.putIfAbsent("Electrode", electrode_cons);
        }

        if (config.AVOID_RAIN) {
            Consumer<PokemonEntity> cons = (pokemonEntity) -> {
                goalHelper.accept(pokemonEntity, 3, new FleeRainGoal(pokemonEntity, pokemonEntity.getSpeed()));
                goalHelper.accept(pokemonEntity, 2, new RestrictRainGoal(pokemonEntity));
            };
            ON_SPAWN.putIfAbsent("fire", cons);
            ON_SPAWN.putIfAbsent("Sudowoodo", cons);
            ON_SPAWN.putIfAbsent("Bonsly", cons);
        }
    }

    private static void registerEvents() {
        CobblemonEvents.POKEMON_SENT_POST.subscribe(Priority.NORMAL, event -> {
            try { onSend(event.getPokemon()); }
            catch (NullPointerException ignored) {}
            return Unit.INSTANCE;
        });
        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(Priority.NORMAL, event -> {
            try { onSpawn(event.getEntity()); }
            catch (NullPointerException ignored) {}
            return Unit.INSTANCE;
        });
        CobblemonEvents.POKEMON_ENTITY_LOAD.subscribe(Priority.NORMAL, event -> {
            try {
                if (event.getPokemonEntity().getPokemon().getOwnerEntity() == null) onSpawn(event.getPokemonEntity());
                else onSend(event.getPokemonEntity());
            }
            catch (NullPointerException ignored) {}
            return Unit.INSTANCE;
        });
        EventRegistry.PASTURE_SENT.subscribe(Priority.NORMAL, event -> {
            try { onSend(event.pokemon); }
            catch (NullPointerException ignored) {}
            return Unit.INSTANCE;
        });

        if (LivelierPokemon.getAbilityConfig().BALL_FETCH) {
            CobblemonEvents.POKE_BALL_CAPTURE_CALCULATED.subscribe(Priority.NORMAL, event -> {
                BallFetchEvent.onPokeballCaptureCalculated(event);
                return Unit.INSTANCE;
            });
        }
    }

    public static boolean hasAbility(Pokemon pokemon, String ability) {
        return pokemon.getAbility().getName().equals(ability);
    }

    public static boolean hasAbility(PokemonEntity pokemonEntity, String ability) {
        return SpawnHelper.hasAbility(pokemonEntity.getPokemon(), ability);
    }

    public static boolean isSpecies(Pokemon pokemon, String species) {
        return pokemon.getSpecies().getName().equals(species);
    }

    public static boolean isSpecies(PokemonEntity pokemonEntity, String species) {
        return SpawnHelper.isSpecies(pokemonEntity.getPokemon(), species);
    }

    public static boolean isType(Pokemon pokemon, String type) {
        for (ElementalType elementalType : pokemon.getTypes()) {
            if (elementalType.getName().equals(type)) return true;
        }
        return false;
    }

    public static boolean isType(PokemonEntity pokemonEntity, String type) {
        return SpawnHelper.isType(pokemonEntity.getPokemon(), type);
    }

    public static List<String> getAttributes(Pokemon pokemon) {
        List<String> attributes = new ArrayList<>();
        attributes.add(pokemon.getSpecies().getName());
        attributes.add(pokemon.getAbility().getName());
        pokemon.getTypes().forEach((type) -> attributes.add(type.getName()));
        return attributes;
    }

    public static void onSpawn(PokemonEntity pokemonEntity) {
        List<String> attributes = getAttributes(pokemonEntity.getPokemon());
        // LivelierPokemon.LOGGER.info("onSpawn {}", attributes);
        for (String attribute : attributes) {
            Consumer<PokemonEntity> addGoal = ON_SPAWN.getOrDefault(attribute, null);
            if (addGoal != null) addGoal.accept(pokemonEntity);
        }
    }

    public static void onSend(PokemonEntity pokemonEntity) {
        List<String> attributes = getAttributes(pokemonEntity.getPokemon());
        // LivelierPokemon.LOGGER.info("onSend {}", attributes);
        for (String attribute : attributes) {
            Consumer<PokemonEntity> addGoal = ON_SEND.getOrDefault(attribute, null);
            if (addGoal != null) addGoal.accept(pokemonEntity);
        }
    }

    public static void onSend(Pokemon pokemon) {
        List<String> attributes = getAttributes(pokemon);
        // LivelierPokemon.LOGGER.info("onSend {}", attributes);
        for (String attribute : attributes) {
            Consumer<PokemonEntity> addGoal = ON_SEND.getOrDefault(attribute, null);
            if (addGoal != null) addGoal.accept(pokemon.getEntity());
        }
    }
}
