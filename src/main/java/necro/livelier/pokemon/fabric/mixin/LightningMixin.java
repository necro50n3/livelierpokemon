package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import java.util.List;
import java.util.function.Supplier;
import java.util.Optional;

@Mixin(ServerLevel.class)
public abstract class LightningMixin extends Level
{
    protected LightningMixin(WritableLevelData properties, ResourceKey<Level> registryRef, RegistryAccess registryAccess,
            Holder<DimensionType> dimension, Supplier<ProfilerFiller> profiler, boolean isClient, boolean debugWorld,
            long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryAccess, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    private Optional<BlockPos> findLightningRod(BlockPos pos)
    {
        throw new AbstractMethodError("Shadow");
    }

    @Inject(method = "findLightningTargetAround(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", at = @At("RETURN"))
    public BlockPos findLightningTargetAroundInject(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        pos = this.getHeightmapPos(Types.MOTION_BLOCKING, pos);
        AABB pokemonBox = new AABB(pos.getX() - 64, pos.getY() - 64, pos.getZ() - 64, pos.getX() + 64, pos.getY() + 64, pos.getZ() + 64);
        List<PokemonEntity> pokemonEntityList = getEntitiesOfClass(PokemonEntity.class, pokemonBox);
        if (!pokemonEntityList.isEmpty())
        {
            for (PokemonEntity pokemonEntityInstance : pokemonEntityList)
            {
                if (pokemonEntityInstance.getPokemon().getAbility().getName().equals("lightningrod"))
                {
                    BlockPos pokemonPos = pokemonEntityInstance.getOnPos();
                    LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
                    lightningEntity.moveTo(Vec3.atBottomCenterOf(pokemonPos));
                    lightningEntity.setVisualOnly(true);
                    this.addFreshEntity(lightningEntity);
                    return pokemonPos.below(16);
                }   
            }
        }
        BlockPos blockPos = this.getHeightmapPos(Types.MOTION_BLOCKING, pos);
        Optional<BlockPos> optional = this.findLightningRod(blockPos);
        if (optional.isPresent()) {
            return optional.get();
        }
        AABB box = new AABB(blockPos, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ())).expandTowards(3.0, 3.0, 3.0);
        List<LivingEntity> list = this.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != null && entity.isAlive() && this.canSeeSky(entity.getOnPos()));
        if (!list.isEmpty()) {
            return list.get(this.random.nextInt(list.size())).getOnPos();
        }
        if (blockPos.getY() == blockPos.getY() - 1) {
            blockPos = blockPos.above(2);
        }
        return blockPos;
    }
}
