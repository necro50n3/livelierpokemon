package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.fabric.behaviors.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

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
            case "wander":
                this.triggerAddWander();
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
                    pokemonEntity.goalSelector.addGoal(3, (Goal) obj);
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
        pokemonEntity.goalSelector.addGoal(4, new PokemonRevengeGoal(pokemonEntity, parameter));
    }

    public void triggerAddWander()
    {
        pokemonEntity.goalSelector.addGoal(6, new TeleportAroundGoal(pokemonEntity));
    }

    public void triggerAddEffect()
    {
        PokemonEffect.addEffect(pokemonEntity, parameter);
    }

    public void triggerAddRainGoal()
    {
        pokemonEntity.goalSelector.addGoal(3, new EscapeRainGoal(pokemonEntity, parameter));
        pokemonEntity.goalSelector.addGoal(2, new AvoidRainGoal(pokemonEntity));
    }

    public static MobEffectInstance getStatusEffect(String parameter, int duration, int amplifier)
    {
        MobEffectInstance effect;
        switch (parameter)
        {
            case "speed":
                effect = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration, amplifier, false, false, true);
                break;
            case "slowness":
                effect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier, false, false, true);
                break;
            case "haste":
                effect = new MobEffectInstance(MobEffects.DIG_SPEED, duration, amplifier, false, false, true);
                break;
            case "mining_fatigue":
                effect = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, amplifier, false, false, true);
                break;
            case "strength":
                effect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, false, false, true);
                break;
            case "jump_boost":
                effect = new MobEffectInstance(MobEffects.JUMP, duration, amplifier, false, false, true);
                break;
            case "nausea":
                effect = new MobEffectInstance(MobEffects.CONFUSION, duration, amplifier, false, false, true);
                break;
            case "regeneration":
                effect = new MobEffectInstance(MobEffects.REGENERATION, duration, amplifier, false, false, true);
                break;
            case "resistance":
                effect = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, amplifier, false, false, true);
                break;
            case "fire_resistance":
                effect = new MobEffectInstance(MobEffects.FIRE_RESISTANCE, duration, amplifier, false, false, true);
                break;
            case "water_breathing":
                effect = new MobEffectInstance(MobEffects.WATER_BREATHING, duration, amplifier, false, false, true);
                break;
            case "invisibility":
                effect = new MobEffectInstance(MobEffects.INVISIBILITY, duration, amplifier, false, false, true);
                break;
            case "blindness":
                effect = new MobEffectInstance(MobEffects.BLINDNESS, duration, amplifier, false, false, true);
                break;
            case "night_vision":
                effect = new MobEffectInstance(MobEffects.NIGHT_VISION, duration, amplifier, false, false, true);
                break;
            case "hunger":
                effect = new MobEffectInstance(MobEffects.HUNGER, duration, amplifier, false, false, true);
                break;
            case "weakness":
                effect = new MobEffectInstance(MobEffects.WEAKNESS, duration, amplifier, false, false, true);
                break;
            case "poison":
                effect = new MobEffectInstance(MobEffects.POISON, duration, amplifier, false, false, true);
                break;
            case "wither":
                effect = new MobEffectInstance(MobEffects.WITHER, duration, amplifier, false, false, true);
                break;
            case "absorption":
                effect = new MobEffectInstance(MobEffects.ABSORPTION, duration, amplifier, false, false, true);
                break;
            case "saturation":
                effect = new MobEffectInstance(MobEffects.SATURATION, duration, amplifier, false, false, true);
                break;
            case "glowing":
                effect = new MobEffectInstance(MobEffects.GLOWING, duration, amplifier, false, false, true);
                break;
            case "levitation":
                effect = new MobEffectInstance(MobEffects.LEVITATION, duration, amplifier, false, false, true);
                break;
            case "luck":
                effect = new MobEffectInstance(MobEffects.LUCK, duration, amplifier, false, false, true);
                break;
            case "bad_luck":
                effect = new MobEffectInstance(MobEffects.UNLUCK, duration, amplifier, false, false, true);
                break;
            case "slow_falling":
                effect = new MobEffectInstance(MobEffects.SLOW_FALLING, duration, amplifier, false, false, true);
                break;
            case "dolphins_grace":
                effect = new MobEffectInstance(MobEffects.DOLPHINS_GRACE, duration, amplifier, false, false, true);
                break;
            case "darkness":
                effect = new MobEffectInstance(MobEffects.DARKNESS, duration, amplifier, false, false, true);
                break;
            default:
                effect = null;
                break;
        }
        return effect;
    }
}
