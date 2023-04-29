package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.fabric.behaviors.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class LivelierPokemonManager {
    private PokemonEntity pokemonEntity;
    private String behaviourType;
    private String behaviourClass;
    private String parameter;

    public LivelierPokemonManager(PokemonEntity pokemonEntity, Map<String, String> mapValues)
    {
        this.pokemonEntity = pokemonEntity;
        behaviourType = mapValues.get("type");
        behaviourClass = mapValues.get("class");
        parameter = mapValues.get("parameter");
    }

    public void behaviourManager()
    {
        LivelierPokemon.LOGGER.info("Adding " + behaviourClass + " to " + pokemonEntity.getPokemon().getDisplayName().getString());
        switch (behaviourType)
        {
            case "goal":
                this.triggerAddGoal();
                break;
            case "effect":
                this.triggerAddEffect();
                break;
            case "revenge":
                this.triggerAddRevenge();
                break;
            case "rain":
                this.triggerAddRainGoal();
                break;
            default:
        }
        
        
    }

    public void triggerAddGoal()
    {
        if (behaviourClass != null)
        {
            try {
                @SuppressWarnings("unchecked")
                Class<Goal> cls = (Class<Goal>) Class.forName("necro.livelier.pokemon.fabric.behaviors." + behaviourClass);
                Object obj;
                try {
                    obj = cls.getDeclaredConstructor(PokemonEntity.class, String.class).newInstance(new Object[] {pokemonEntity, parameter});
                    pokemonEntity.goalSelector.add(3, (Goal) obj);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
    }

    public void triggerAddRevenge()
    {
        pokemonEntity.goalSelector.add(4, new PokemonRevengeGoal(pokemonEntity, parameter));
    }

    public void triggerAddEffect()
    {
        PokemonEffect.addEffect(pokemonEntity, parameter);
    }

    public void triggerAddRainGoal()
    {
        pokemonEntity.goalSelector.add(3, new EscapeRainGoal(pokemonEntity, parameter));
        pokemonEntity.goalSelector.add(2, new AvoidRainGoal(pokemonEntity));
    }

    public static StatusEffectInstance getStatusEffect(String parameter, int duration, int amplifier)
    {
        StatusEffectInstance effect;
        switch (parameter)
        {
            case "speed":
                effect = new StatusEffectInstance(StatusEffects.SPEED, duration, amplifier, false, false, true);
                break;
            case "slowness":
                effect = new StatusEffectInstance(StatusEffects.SLOWNESS, duration, amplifier, false, false, true);
                break;
            case "haste":
                effect = new StatusEffectInstance(StatusEffects.HASTE, duration, amplifier, false, false, true);
                break;
            case "mining_fatigue":
                effect = new StatusEffectInstance(StatusEffects.MINING_FATIGUE, duration, amplifier, false, false, true);
                break;
            case "strength":
                effect = new StatusEffectInstance(StatusEffects.STRENGTH, duration, amplifier, false, false, true);
                break;
            case "jump_boost":
                effect = new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, amplifier, false, false, true);
                break;
            case "nausea":
                effect = new StatusEffectInstance(StatusEffects.NAUSEA, duration, amplifier, false, false, true);
                break;
            case "regeneration":
                effect = new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier, false, false, true);
                break;
            case "resistance":
                effect = new StatusEffectInstance(StatusEffects.RESISTANCE, duration, amplifier, false, false, true);
                break;
            case "fire_resistance":
                effect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, amplifier, false, false, true);
                break;
            case "water_breathing":
                effect = new StatusEffectInstance(StatusEffects.WATER_BREATHING, duration, amplifier, false, false, true);
                break;
            case "invisibility":
                effect = new StatusEffectInstance(StatusEffects.INVISIBILITY, duration, amplifier, false, false, true);
                break;
            case "blindness":
                effect = new StatusEffectInstance(StatusEffects.BLINDNESS, duration, amplifier, false, false, true);
                break;
            case "night_vision":
                effect = new StatusEffectInstance(StatusEffects.NIGHT_VISION, duration, amplifier, false, false, true);
                break;
            case "hunger":
                effect = new StatusEffectInstance(StatusEffects.HUNGER, duration, amplifier, false, false, true);
                break;
            case "weakness":
                effect = new StatusEffectInstance(StatusEffects.WEAKNESS, duration, amplifier, false, false, true);
                break;
            case "poison":
                effect = new StatusEffectInstance(StatusEffects.POISON, duration, amplifier, false, false, true);
                break;
            case "wither":
                effect = new StatusEffectInstance(StatusEffects.WITHER, duration, amplifier, false, false, true);
                break;
            case "absorption":
                effect = new StatusEffectInstance(StatusEffects.ABSORPTION, duration, amplifier, false, false, true);
                break;
            case "saturation":
                effect = new StatusEffectInstance(StatusEffects.SATURATION, duration, amplifier, false, false, true);
                break;
            case "glowing":
                effect = new StatusEffectInstance(StatusEffects.GLOWING, duration, amplifier, false, false, true);
                break;
            case "levitation":
                effect = new StatusEffectInstance(StatusEffects.LEVITATION, duration, amplifier, false, false, true);
                break;
            case "luck":
                effect = new StatusEffectInstance(StatusEffects.LUCK, duration, amplifier, false, false, true);
                break;
            case "bad_luck":
                effect = new StatusEffectInstance(StatusEffects.UNLUCK, duration, amplifier, false, false, true);
                break;
            case "slow_falling":
                effect = new StatusEffectInstance(StatusEffects.SLOW_FALLING, duration, amplifier, false, false, true);
                break;
            case "dolphins_grace":
                effect = new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, duration, amplifier, false, false, true);
                break;
            case "darkness":
                effect = new StatusEffectInstance(StatusEffects.DARKNESS, duration, amplifier, false, false, true);
                break;
            default:
                effect = null;
        }
        return effect;
    }
}
