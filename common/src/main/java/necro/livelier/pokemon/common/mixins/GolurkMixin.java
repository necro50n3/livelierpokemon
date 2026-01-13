package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonBlocks;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class GolurkMixin extends HorizontalDirectionalBlock {
    @Unique
    @Nullable
    private BlockPattern livelier_golurkBase;

    @Unique
    @Nullable
    private BlockPattern livelier_golurkFull;

    @Unique
    private static final Predicate<BlockState> GOLURK_HEAD_PREDICATE = state -> state != null && (state.is(Blocks.CARVED_PUMPKIN) || state.is(Blocks.JACK_O_LANTERN));

    protected GolurkMixin(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Inject(method = "trySpawnGolem(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"))
    private void trySpawnGolemInject(Level level, BlockPos blockPos, CallbackInfo ci) {
        if (!LivelierPokemon.getAbilityConfig().GOLURK) return;
        BlockPattern.BlockPatternMatch blockPatternMatch = this.livelier_getGolurkFull().find(level, blockPos);
        if (blockPatternMatch != null) {
            PokemonEntity golurk = livelier_setGolurkEntity(level);
            if (golurk != null) {
                livelier_spawnGolurk(level, blockPatternMatch, golurk, blockPatternMatch.getBlock(0, 2, 0).getPos());
            }
        }
    }

    @Unique
    private void livelier_spawnGolurk(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, PokemonEntity golurk, BlockPos blockPos) {
        CarvedPumpkinBlock.clearPatternBlocks(level, blockPatternMatch);
        golurk.moveTo((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.05, (double) blockPos.getZ() + 0.5, 0.0f, 0.0f);
        level.addFreshEntity(golurk);

        for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, golurk.getBoundingBox().inflate(5.0))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golurk);
        }

        CarvedPumpkinBlock.updatePatternBlocks(level, blockPatternMatch);
    }

    @Inject(method = "canSpawnGolem(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private void canSpawnGolemInject(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (this.livelier_getGolurkBase().find(world, pos) != null) cir.setReturnValue(true);
    }

    @Unique
    private BlockPattern livelier_getGolurkBase() {
        if (this.livelier_golurkBase == null) {
            this.livelier_golurkBase = BlockPatternBuilder.start().aisle("~^~", "###", "~#~")
                .where('^', BlockInWorld.hasState(GOLURK_HEAD_PREDICATE))
                .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(CobblemonBlocks.CHISELED_POLISHED_BLACK_TUMBLESTONE)))
                .where('~', (blockInWorld) -> blockInWorld.getState().isAir()).build();
        }
        return this.livelier_golurkBase;
    }

    @Unique
    private BlockPattern livelier_getGolurkFull() {
        if (this.livelier_golurkFull == null) {
            this.livelier_golurkFull = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~")
                .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(CobblemonBlocks.CHISELED_POLISHED_BLACK_TUMBLESTONE)))
                .where('~', blockInWorld -> blockInWorld.getState().isAir()).build();
        }
        return this.livelier_golurkFull;
    }

    @Unique
    private PokemonEntity livelier_setGolurkEntity(Level level) {
        PokemonProperties properties = PokemonProperties.Companion.parse("species=golurk", " ", "=");
        properties.setLevel((int) (Math.random() * Math.min(30, Cobblemon.config.getMaxPokemonLevel()) + 1));
        if (properties.getSpecies() == null) return null;
        properties.setShiny(false);
        PokemonEntity golurk = properties.createEntity(level);
        golurk.setPersistenceRequired();
        AttributeInstance attribute = golurk.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) attribute.setBaseValue(100.0);
        golurk.setHealth(100);

        return golurk;
    }
}