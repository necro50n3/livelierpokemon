package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerPlayer;

public class PokemonRevengeGoal extends HurtByTargetGoal{
    private String parameter;

    public PokemonRevengeGoal(PokemonEntity pokemonEntity, String parameter)
    {
        super(pokemonEntity, new Class[0]);
        this.parameter = parameter;
    }

    @Override
    public boolean canUse()
    {
        int i = this.mob.getLastHurtByMobTimestamp();
        LivingEntity livingEntity = this.mob.getLastAttacker();
        if (i == this.timestamp || livingEntity == null) {
            return false;
        }
        if (livingEntity.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
            return false;
        }
        return true;
    }

    @Override
    public void start()
    {
        this.mob.setTarget(this.mob.getLastAttacker());
        this.targetMob = this.mob.getTarget();
        this.timestamp = this.mob.getLastHurtByMobTimestamp();
        this.damageType(this.targetMob);
    }

    @Override
    public boolean canContinueToUse()
    {
        return false;
    }

    public void damageType(LivingEntity target)
    {
        if (this.mob.getLastDamageSource() != null)
        {
            switch (parameter)
            {
                case "damage":
                    if (!this.mob.getLastDamageSource().is(DamageTypes.MOB_PROJECTILE) && !this.mob.getLastDamageSource().is(DamageTypes.ARROW) && !this.mob.getLastDamageSource().is(DamageTypes.THROWN))
                    {
                        target.hurt(this.mob.level().damageSources().thorns(this.mob), 4.0F);
                    }
                    break;
                case "fire":
                    if (!this.mob.getLastDamageSource().is(DamageTypes.MOB_PROJECTILE) && !this.mob.getLastDamageSource().is(DamageTypes.ARROW) && !this.mob.getLastDamageSource().is(DamageTypes.THROWN))
                    {
                        if (Math.random() < 0.30 && !target.fireImmune())
                            target.setSecondsOnFire(3);
                    }
                    break;
                case "durability":
                    if (this.mob.getLastAttacker() instanceof Player)
                    {
                        Player player = (Player) this.mob.getLastAttacker();
                        ItemStack item = player.getMainHandItem();
                        if (item.isDamageableItem())
                        {
                            item.hurt(3, RandomSource.create(), (ServerPlayer) player);
                        }
                    }
                    break;
                case "hide":
                    MobEffectInstance invisibility = LivelierPokemonManager.getStatusEffect("invisibility", 100, 1);
                    if (invisibility != null)
                    {
                        if (Math.random() < 0.30 && target.canBeAffected(invisibility))
                            this.mob.addEffect(invisibility, this.mob);
                    }
                    break;
                default:
                    if (!mob.getLastDamageSource().is(DamageTypes.MOB_PROJECTILE) || mob.getLastDamageSource().is(DamageTypes.ARROW) || mob.getLastDamageSource().is(DamageTypes.THROWN))
                    {
                        MobEffectInstance effect = LivelierPokemonManager.getStatusEffect(parameter, 100, 1);
                        if (effect != null)
                        {
                            if (Math.random() < 0.30 && target.canBeAffected(effect))
                                target.addEffect(effect, this.mob);
                        }
                    }
                    break;
            }
        }
    }
}
