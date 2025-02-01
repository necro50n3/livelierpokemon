package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.activestate.SentOutState;
import necro.livelier.pokemon.common.events.PastureSentEvent;
import necro.livelier.pokemon.common.registries.EventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PokemonPastureBlockEntity.class)
public abstract class PastureBlockMixin extends BlockEntity {
    public PastureBlockMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "tether(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/core/Direction;)Z", at = @At("RETURN"))
    private void tetherInject(ServerPlayer player, Pokemon pokemon, Direction directionToBehind, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) EventRegistry.PASTURE_SENT.emit(new PastureSentEvent(pokemon));
    }

    @Redirect(method = "tether(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean addFreshEntityInject(Level level, Entity entity) {
        ((PokemonEntity) entity).getPokemon().setState(new SentOutState((PokemonEntity) entity));
        return level.addFreshEntity(entity);
    }
}
