package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level.ExplosionInteraction;

import java.util.Collection;

public class VoltorbExplosionGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final boolean affectsEnvironment;
    private final double radius;
    private LivingEntity target;
    private final VoltorbAttackGoal goal1;
    private final LookAtPlayerGoal goal2;
    private final NearestAttackableTargetGoal<Player> goal3;
    private int tick;
    private boolean ignited;

    public VoltorbExplosionGoal(PokemonEntity pokemonEntity, boolean affectsEnvironment, double radius) {
        this.pokemonEntity = pokemonEntity;
        this.affectsEnvironment = affectsEnvironment;
        this.radius = radius;
        this.goal1 = new VoltorbAttackGoal(pokemonEntity, 1.0, false);
        this.goal2 = new LookAtPlayerGoal(pokemonEntity, Player.class, 8.0f);
        this.goal3 = new NearestAttackableTargetGoal<>(pokemonEntity, Player.class, true);
        this.target = null;
        this.tick = 0;
        this.ignited = false;
    }

    @Override
    public boolean canUse() {
        if (this.pokemonEntity.getPokemon().getShiny()) return false;
        else if (this.pokemonEntity.level().getDifficulty() == Difficulty.PEACEFUL) return false;
        else if (this.pokemonEntity.getOwner() != null) return false;
        else return !this.pokemonEntity.isBattling() && !this.pokemonEntity.isBusy();
    }

    @Override
    public void start() {
        this.pokemonEntity.goalSelector.addGoal(4, this.goal1);
        this.pokemonEntity.goalSelector.addGoal(6, this.goal2);
        this.pokemonEntity.targetSelector.addGoal(1, this.goal3);
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.pokemonEntity.getOwner() != null) return false;
        else if (!this.pokemonEntity.isAlive()) return false;
        else return this.canUse();
    }

    @Override
    public void stop() {
        this.pokemonEntity.goalSelector.removeGoal(this.goal1);
        this.pokemonEntity.goalSelector.removeGoal(this.goal2);
        this.pokemonEntity.goalSelector.removeGoal(this.goal3);
    }

    @Override
    public void tick() {
        if (!this.pokemonEntity.isAlive()) return;
        else if (this.pokemonEntity.getTarget() == null) return;
        this.target = this.pokemonEntity.getTarget();
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if ((!config.DAMP || TargetHelper.getNearbyPokemon(this.pokemonEntity, config.damp_radius, entity ->
            SpawnHelper.hasAbility(entity, "damp")).isEmpty()) && this.pokemonEntity.distanceTo(this.target) <= 3
        ) {
            if (this.tick++ >= 30) this.explode();
            else if (!this.ignited && this.pokemonEntity.distanceTo(this.target) <= 1.5) {
                this.ignited = true;
                this.pokemonEntity.playSound(SoundEvents.CREEPER_PRIMED, 1.0f, 0.5f);
            }
        } else {
            this.ignited = false;
            this.tick = 0;
        }
        super.tick();
    }

    public void explode() {
        if (!this.pokemonEntity.level().isClientSide()) {
            pokemonEntity.dead = true;
            pokemonEntity.level().explode(pokemonEntity, pokemonEntity.getX(), pokemonEntity.getY(), pokemonEntity.getZ(),
                (float) this.radius, this.affectsEnvironment ? ExplosionInteraction.MOB : ExplosionInteraction.NONE);
            this.spawnLingeringCloud();
            this.pokemonEntity.triggerOnDeathMobEffects(Entity.RemovalReason.KILLED);
            this.pokemonEntity.discard();
        }
    }

    public void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = pokemonEntity.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(
                this.pokemonEntity.level(), this.pokemonEntity.getX(), this.pokemonEntity.getY(), this.pokemonEntity.getZ()
            );
            areaEffectCloud.setRadius(2.5f);
            areaEffectCloud.setRadiusOnUse(-0.5f);
            areaEffectCloud.setWaitTime(10);
            areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
            areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
            for (MobEffectInstance mobEffectInstance : collection) {
                areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
            }
            this.pokemonEntity.level().addFreshEntity(areaEffectCloud);
        }
    }
}
