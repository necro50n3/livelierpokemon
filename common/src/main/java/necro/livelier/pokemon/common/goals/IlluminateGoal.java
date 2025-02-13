package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class IlluminateGoal extends EntityEffectGoal {
    private final int radius;

    public IlluminateGoal(PokemonEntity pokemonEntity, int radius) {
        super(pokemonEntity, MobEffects.GLOWING);
        this.radius = radius;
    }

    @Override
    public List<LivingEntity> getEntities() {
        return TargetHelper.getNearbyEntities(this.pokemonEntity, this.radius, false, (entity) -> !entity.is(this.pokemonEntity.getOwner()));
    }

    public MobEffectInstance getEffect(Holder<MobEffect> mobEffect, int duration, int amplifier) {
        return new MobEffectInstance(mobEffect, duration, amplifier, false, false, false);
    }
}
