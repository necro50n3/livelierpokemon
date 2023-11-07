package necro.livelier.pokemon.fabric.behaviors;

import java.util.Collection;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;

public class VoltorbExplosionGoal extends FollowOwnerGoal
{
    private PokemonEntity pokemonEntity;
    private LookAtPlayerGoal lookGoal;
    private NearestAttackableTargetGoal<Player> nearGoal;
    private int explosionTicks;
    private boolean triggered;

    public VoltorbExplosionGoal(PokemonEntity pokemonEntity, String parameter)
    {
        super(pokemonEntity, 1.0, 8, 0, false);
        this.pokemonEntity = pokemonEntity;
        explosionTicks = 0;
        triggered = false;
        
        lookGoal = new LookAtPlayerGoal(pokemonEntity, Player.class, 8.0f);
        nearGoal = new NearestAttackableTargetGoal<Player>(pokemonEntity, Player.class, true);
        pokemonEntity.goalSelector.addGoal(6, lookGoal);
        pokemonEntity.targetSelector.addGoal(1, nearGoal);
    }

    @Override
    public boolean canUse()
    {
        if (pokemonEntity.getPokemon().getShiny())
        {
            pokemonEntity.goalSelector.removeGoal(this);
            pokemonEntity.goalSelector.removeGoal(lookGoal);
            pokemonEntity.targetSelector.removeGoal(nearGoal);
            return false;
        }
        else if (nearGoal.target == null)
        {
            return false;
        }
        this.owner = nearGoal.target;
        return true;
    }

    @Override
    public void start()
    {
        if (explosionTicks >= 40)
            this.explosion();

        super.start();
    }

    @Override
    public void tick()
    {
        if (pokemonEntity.distanceTo(this.owner) <= 3)
        {
            explosionTicks++;
            if (!triggered && pokemonEntity.distanceTo(this.owner) <= 1.5)
            {
                triggered = true;
                pokemonEntity.playSound(SoundEvents.CREEPER_PRIMED, 1.0f, 0.5f);
            }
        }
        else
        {
            triggered = false;
            explosionTicks = 0;
        }
        super.tick();
    }

    private void explosion() {
        if (!pokemonEntity.level.isClientSide) {
            pokemonEntity.dead = true;
            pokemonEntity.level.explode(pokemonEntity, pokemonEntity.getX(), pokemonEntity.getY(), pokemonEntity.getZ(), 2.0F, Level.ExplosionInteraction.NONE);
            pokemonEntity.discard();
            this.spawnLingeringCloud();
        }
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = pokemonEntity.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(pokemonEntity.level, pokemonEntity.getX(), pokemonEntity.getY(), pokemonEntity.getZ());
            areaEffectCloud.setRadius(2.5f);
            areaEffectCloud.setRadiusOnUse(-0.5f);
            areaEffectCloud.setWaitTime(10);
            areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
            areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
            for (MobEffectInstance mobEffectInstance : collection) {
                areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
            }
            pokemonEntity.level.addFreshEntity(areaEffectCloud);
        }
    }

    @Override
    protected void teleportToOwner()
    {

    }
}
