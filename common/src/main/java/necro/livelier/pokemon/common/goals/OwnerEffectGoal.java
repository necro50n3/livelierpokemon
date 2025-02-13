package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class OwnerEffectGoal extends EntityEffectGoal {
    private final LivingEntity owner;

    public OwnerEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int duration, int amplifier) {
        super(pokemonEntity, mobEffect, duration, amplifier);
        this.owner = this.pokemonEntity.getOwner();
    }

    public OwnerEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int friendshipThreshold) {
        this(pokemonEntity, mobEffect, 240, pokemonEntity.getFriendship() < friendshipThreshold ? 0 : 1);
    }

    @Override
    public boolean canUse() {
        if (this.owner == null) return false;
        return this.pokemonEntity.distanceTo(this.owner) < 16;
    }

    @Override
    public List<LivingEntity> getEntities() {
        return Collections.singletonList(this.owner);
    }
}
