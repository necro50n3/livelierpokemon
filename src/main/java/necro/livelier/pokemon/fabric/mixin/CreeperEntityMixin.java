package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.behaviors.CreeperFleeCatPokemonGoal;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity
{
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "initGoals()V", at = @At("HEAD"))
    protected void initGoalsInject(CallbackInfo info)
    {
        this.goalSelector.add(3, new CreeperFleeCatPokemonGoal<PokemonEntity>(this, PokemonEntity.class, 6.0f, 1.0, 1.2));
    }
}
