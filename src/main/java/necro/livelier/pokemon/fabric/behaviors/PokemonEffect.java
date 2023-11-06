package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.world.effect.MobEffectInstance;

public abstract class PokemonEffect {
    public static void addEffect(PokemonEntity pokemonEntity, String parameter)
    {
        switch (parameter)
        {
            case "absorption":
                pokemonEntity.setAbsorptionAmount(4);
                break;
            case "slowstart":
                pokemonEntity.addEffect(LivelierPokemonManager.getStatusEffect("slowness", 200, 1));
                pokemonEntity.addEffect(LivelierPokemonManager.getStatusEffect("weakness", 200, 1));
                break;
            default:
                MobEffectInstance effect = LivelierPokemonManager.getStatusEffect(parameter, 200, 0);
                if (effect != null)
                {
                    if (parameter != "invisibility")
                        effect.duration = -1;
                    pokemonEntity.addEffect(effect);
                } 
                break;
        }
        
    }
}
