package necro.livelier.pokemon.common.helpers;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class ImmunityHelper {
    public static boolean isImmuneTo(DamageSource damageSource, Pokemon pokemon) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.WONDER_GUARD && SpawnHelper.hasAbility(pokemon, "wonderguard") && !damageSource.is(LivelierDamageType.BYPASSES_WONDER_GUARD)
        ) return true;
        else if (config.LIGHTNING_ROD && SpawnHelper.hasAbility(pokemon, "lightningrod") && damageSource.is(DamageTypes.LIGHTNING_BOLT)
        ) return true;
        else if (config.BULLETPROOF && SpawnHelper.hasAbility(pokemon, "bulletproof") &&
            (damageSource.is(DamageTypeTags.IS_PROJECTILE) || damageSource.is(DamageTypeTags.IS_EXPLOSION))
        ) return true;
        else if (config.ARMOR_TAIL && SpawnHelper.hasAbility(pokemon, "armortail") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) return true;
        else if (config.DAZZLING && SpawnHelper.hasAbility(pokemon, "dazzling") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) return true;
        else if (config.QUEENLY_MAJESTY && SpawnHelper.hasAbility(pokemon, "queenlymajesty") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) return true;
        return false;
    }

    public static boolean isImmuneToFOF(DamageSource damageSource, PokemonEntity pokemonEntity) {
        if ((SpawnHelper.hasAbility(pokemonEntity, "levitate") || SpawnHelper.isType(pokemonEntity, "flying") ||
            pokemonEntity.getPokemon().getSpecies().getBehaviour().getMoving().getFly().getCanFly()) && damageSource.is(DamageTypes.FALL)
        ) return  true;
        else if (pokemonEntity.isBusy()) return true;
        else if (pokemonEntity.getBeamMode() != 0) return true;
        if (!Cobblemon.config.getPlayerDamagePokemon() && damageSource.getEntity() instanceof Player) return true;
        return isImmuneTo(damageSource, pokemonEntity.getPokemon());
    }
}
