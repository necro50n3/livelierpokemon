package necro.livelier.pokemon.fabric.mixin;

import necro.livelier.pokemon.fabric.LivelierPokemon;

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

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Iterator;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

@Mixin(CarvedPumpkinBlock.class)
public abstract class GolurkMixin extends HorizontalDirectionalBlock
{
    @Nullable
    private BlockPattern golurkBlockPattern;
    @Nullable
    private BlockPattern golurkDispenserPattern;
    @Nullable
    private PokemonEntity golurkEntity;
    private static final Predicate<BlockState> GOLURK_HEAD_PREDICATE = state -> state != null && (state.is(Blocks.CARVED_PUMPKIN) || state.is(Blocks.JACK_O_LANTERN));

    protected GolurkMixin(BlockBehaviour.Properties settings)
    {
        super(settings);
    }
    
    @Inject(method = "trySpawnGolem(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"))
    public void trySpawnGolemInject(Level world, BlockPos pos, CallbackInfo info)
    {
        BlockPattern.BlockPatternMatch blockPatternMatch3 = this.getGolurkPattern().find(world, pos);
        if (blockPatternMatch3 != null)
        {
            PokemonEntity golurk = setGolurkEntity(world);
            if (golurk != null)
            {
                spawnGolurk(world, blockPatternMatch3, golurk, pos);
            }
        }
    }

    private static void spawnGolurk(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, PokemonEntity entity, BlockPos blockPos) {
        clearGolurkBlocks(level, blockPatternMatch);
        entity.moveTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.95, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(entity);
        LivelierPokemon.wildSpawn(entity);
        Iterator<ServerPlayer> var4 = level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0)).iterator();

        while(var4.hasNext()) {
            ServerPlayer serverPlayer = (ServerPlayer)var4.next();
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
        }

        updateGolurkBlocks(level, blockPatternMatch);
    }

    private static void clearGolurkBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
        for(int i = 0; i < blockPatternMatch.getWidth(); ++i) {
            for(int j = 0; j < blockPatternMatch.getHeight(); ++j) {
                BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
                level.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
            }
        }
    }

    private static void updateGolurkBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
        for(int i = 0; i < blockPatternMatch.getWidth(); ++i) {
           for(int j = 0; j < blockPatternMatch.getHeight(); ++j) {
              BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
              level.blockUpdated(blockInWorld.getPos(), Blocks.AIR);
           }
        }
    }

    @Inject(method = "canSpawnGolem(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    public boolean canSpawnGolemInject(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (this.getGolurkDispenserPattern().find(world, pos) != null)
        {
            cir.setReturnValue(true);
        }
        return false;
    }

    private BlockPattern getGolurkDispenserPattern()
    {
        if (this.golurkDispenserPattern == null)
        {
            this.golurkDispenserPattern = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.OXIDIZED_COPPER))).where('~', blockInWorld -> blockInWorld.getState().isAir()).build();
        }
        return this.golurkDispenserPattern;
    }

    private BlockPattern getGolurkPattern()
    {
        if (this.golurkBlockPattern == null)
        {
            this.golurkBlockPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(GOLURK_HEAD_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.OXIDIZED_COPPER))).where('~', (blockInWorld) -> {
                return blockInWorld.getState().isAir();
            }).build();
        }
        return this.golurkBlockPattern;
    }

    private PokemonEntity setGolurkEntity(Level world)
    {
        EntityType<PokemonEntity> golurkEntityType =  CobblemonEntities.POKEMON;
        Pokemon golurkPokemon = new Pokemon();
        golurkPokemon.setSpecies(PokemonSpecies.INSTANCE.getByName("golurk"));
        golurkPokemon.setLevel((int) (Math.random() * Math.min(30, Cobblemon.config.getMaxPokemonLevel()) + 1));
        golurkPokemon.initializeMoveset(true);
        this.golurkEntity = new PokemonEntity(world, golurkPokemon, golurkEntityType);
        golurkEntity.setPokemon(golurkPokemon);
        golurkEntity.setPersistenceRequired();
        golurkEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100.0);
        golurkEntity.setHealth(100);
        return golurkEntity;
    }
}