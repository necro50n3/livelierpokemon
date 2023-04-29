package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

@Mixin(CarvedPumpkinBlock.class)
public abstract class GolurkMixin extends HorizontalFacingBlock
{
    @Nullable
    private BlockPattern golurkBlockPattern;
    @Nullable
    private BlockPattern golurkDispenserPattern;
    @Nullable
    private PokemonEntity golurkEntity;
    private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null && (state.isOf(Blocks.CARVED_PUMPKIN) || state.isOf(Blocks.JACK_O_LANTERN));

    protected GolurkMixin(Settings settings)
    {
        super(settings);
    }
    
    @Inject(method = "trySpawnEntity(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"))
    public void trySpawnEntityInject(World world, BlockPos pos, CallbackInfo info)
    {
        block10:
        {
            BlockPattern.Result golurkResult;
            golurkResult = this.getGolurkPattern().searchAround(world, pos);
            if (golurkResult == null) break block10;
            for (int i = 0; i < this.getGolurkPattern().getWidth(); ++i)
            {
                for (int k = 0; k < this.getGolurkPattern().getHeight(); ++k)
                {
                    CachedBlockPosition cachedBlockPosition3 = golurkResult.translate(i, k, 0);
                    world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
                }
            }
            BlockPos blockPos2 = golurkResult.translate(1, 2, 0).getBlockPos();
            setGolurkEntity(world);
            golurkEntity.refreshPositionAndAngles((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.05, (double)blockPos2.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(golurkEntity);
            for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, golurkEntity.getBoundingBox().expand(5.0)))
            {
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, golurkEntity);
            }
            for (int j = 0; j < this.getGolurkPattern().getWidth(); ++j)
            {
                for (int l = 0; l < this.getGolurkPattern().getHeight(); ++l)
                {
                    CachedBlockPosition cachedBlockPosition4 = golurkResult.translate(j, l, 0);
                    world.updateNeighbors(cachedBlockPosition4.getBlockPos(), Blocks.AIR);
                }
            }
            break block10;
        }
    }

    @Inject(method = "canDispense(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    public boolean canDispenseInject(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (this.getGolurkDispenserPattern().searchAround(world, pos) != null)
        {
            cir.setReturnValue(true);
        }
        return false;
    }

    private BlockPattern getGolurkDispenserPattern()
    {
        if (this.golurkDispenserPattern == null)
        {
            this.golurkDispenserPattern = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.OXIDIZED_COPPER))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return this.golurkDispenserPattern;
    }

    private BlockPattern getGolurkPattern()
    {
        if (this.golurkBlockPattern == null)
        {
            this.golurkBlockPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.OXIDIZED_COPPER))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
        }
        return this.golurkBlockPattern;
    }

    private void setGolurkEntity(World world)
    {
        EntityType<PokemonEntity> golurkEntityType =  CobblemonEntities.POKEMON.get();
        this.golurkEntity = golurkEntityType.create(world);
        Pokemon golurkPokemon = golurkEntity.getPokemon();
        golurkPokemon.setSpecies(PokemonSpecies.INSTANCE.getByName("golurk"));
        golurkPokemon.setLevel((int) (Math.random() * Math.min(30, Cobblemon.config.getMaxPokemonLevel()) + 1));
        golurkPokemon.initializeMoveset(true);
        golurkEntity.setPokemon(golurkPokemon);
        golurkEntity.setPersistent();
        golurkEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(100.0);
        golurkEntity.setHealth(100);
    }
}