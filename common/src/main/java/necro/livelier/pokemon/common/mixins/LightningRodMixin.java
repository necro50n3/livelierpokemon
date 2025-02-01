package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class LightningRodMixin extends Level {
    @Unique
    private boolean livelierpokemon$redirected;

    protected LightningRodMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
        this.livelierpokemon$redirected = false;
    }

    @Inject(method = "findLightningRod(Lnet/minecraft/core/BlockPos;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void findLightningRodInject(BlockPos blockPos, CallbackInfoReturnable<Optional<BlockPos>> cir) {
        if (!LivelierPokemon.getAbilityConfig().LIGHTNING_ROD) {
            this.livelierpokemon$redirected = false;
            return;
        }
        List<PokemonEntity> pokemonEntities = this.getEntitiesOfClass(PokemonEntity.class, new AABB(blockPos).inflate(64),
            (pokemonEntity) -> SpawnHelper.hasAbility(pokemonEntity, "lightningrod")
        );
        if (pokemonEntities.isEmpty()) {
            this.livelierpokemon$redirected = false;
            return;
        }
        PokemonEntity pokemonEntity = pokemonEntities.get(new Random().nextInt(pokemonEntities.size()));
        BlockPos newPos = new BlockPos(
            pokemonEntity.blockPosition().getX(),
            this.getHeight(Heightmap.Types.WORLD_SURFACE, pokemonEntity.blockPosition().getX(), pokemonEntity.blockPosition().getZ()),
            pokemonEntity.blockPosition().getZ()
        );
        this.livelierpokemon$redirected = true;
        cir.setReturnValue(Optional.of(newPos));
    }

    @Redirect(method="tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;setVisualOnly(Z)V"))
    private void tickChunkInject(LightningBolt lightningBolt, boolean isVisual) {
        lightningBolt.setVisualOnly(this.livelierpokemon$redirected || isVisual);
    }
}
