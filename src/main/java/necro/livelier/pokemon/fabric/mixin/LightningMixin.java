package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.Heightmap;
import java.util.List;
import java.util.function.Supplier;
import java.util.Optional;

@Mixin(ServerWorld.class)
public abstract class LightningMixin extends World
{
    protected LightningMixin(MutableWorldProperties properties, RegistryKey<World> registryRef,
            RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld,
            long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    private Optional<BlockPos> getLightningRodPos(BlockPos pos2)
    {
        throw new AbstractMethodError("Shadow");
    }

    @Inject(method = "getLightningPos(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockPos;", at = @At("RETURN"))
    public BlockPos getLightningPosInject(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        pos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
        Box pokemonBox = new Box(pos.getX() - 64, pos.getY() - 64, pos.getZ() - 64, pos.getX() + 64, pos.getY() + 64, pos.getZ() + 64);
        List<PokemonEntity> pokemonEntityList = getEntitiesByType(TypeFilter.instanceOf(PokemonEntity.class), pokemonBox, EntityPredicates.VALID_ENTITY);
        if (!pokemonEntityList.isEmpty())
        {
            for (PokemonEntity pokemonEntityInstance : pokemonEntityList)
            {
                if (pokemonEntityInstance.getPokemon().getAbility().getName().equals("lightningrod"))
                {
                    BlockPos pokemonPos = pokemonEntityInstance.getBlockPos();
                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
                    lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pokemonPos));
                    lightningEntity.setCosmetic(true);
                    this.spawnEntity(lightningEntity);
                    return pokemonPos.down(16);
                }   
            }
        }
        BlockPos blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
        Optional<BlockPos> optional = this.getLightningRodPos(blockPos);
        if (optional.isPresent()) {
            return optional.get();
        }
        Box box = new Box(blockPos, new BlockPos(blockPos.getX(), this.getTopY(), blockPos.getZ())).expand(3.0);
        List<LivingEntity> list = this.getEntitiesByClass(LivingEntity.class, box, entity -> entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos()));
        if (!list.isEmpty()) {
            return list.get(this.random.nextInt(list.size())).getBlockPos();
        }
        if (blockPos.getY() == this.getBottomY() - 1) {
            blockPos = blockPos.up(2);
        }
        return blockPos;
    }
}
