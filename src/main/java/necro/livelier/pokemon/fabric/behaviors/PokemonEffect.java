package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.entity.effect.StatusEffectInstance;

public abstract class PokemonEffect {
    public static void addEffect(PokemonEntity pokemonEntity, String parameter)
    {
        switch (parameter)
        {
            case "absorption":
                pokemonEntity.setAbsorptionAmount(4);
            break;
            case "slowstart":
                pokemonEntity.addStatusEffect(LivelierPokemonManager.getStatusEffect("slowness", 200, 1));
                pokemonEntity.addStatusEffect(LivelierPokemonManager.getStatusEffect("weakness", 200, 1));
            break;
            default:
                StatusEffectInstance effect = LivelierPokemonManager.getStatusEffect(parameter, 200, 0);
                if (effect != null)
                {
                    if (parameter != "invisibility")
                        effect.setPermanent(true);
                    pokemonEntity.addStatusEffect(effect);
                } 
        }
        
    }
}
