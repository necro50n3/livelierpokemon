package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.At;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.command.SpawnPokemon;

import necro.livelier.pokemon.fabric.LivelierPokemon;
import net.minecraft.world.entity.Entity;

@Mixin(SpawnPokemon.class)
public abstract class SpawnPokemonMixin
{
    protected SpawnPokemonMixin()
    {
        super();
    }

    @ModifyArg(
        method = "execute(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/world/phys/Vec3;)I",
        at = @At(value="INVOKE", target="Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z")
    )
    protected Entity executeInject(Entity entity)
    {
        return LivelierPokemon.wildSpawn((PokemonEntity)entity);
    }
}
