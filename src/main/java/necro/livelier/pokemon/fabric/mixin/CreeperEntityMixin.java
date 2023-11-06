package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.behaviors.CreeperFleeCatPokemonGoal;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

@Mixin(Creeper.class)
public abstract class CreeperEntityMixin extends Monster
{
    protected CreeperEntityMixin(EntityType<? extends Monster> entityType, Level world)
    {
        super(entityType, world);
    }

    @Inject(method = "registerGoals()V", at = @At("HEAD"))
    protected void initGoalsInject(CallbackInfo info)
    {
        this.goalSelector.addGoal(3, new CreeperFleeCatPokemonGoal<PokemonEntity>(this, PokemonEntity.class, 6.0f, 1.0, 1.2));
    }
}
