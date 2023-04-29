package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PokemonRevengeGoal extends RevengeGoal{
    private String parameter;

    public PokemonRevengeGoal(PokemonEntity pokemonEntity, String parameter)
    {
        super(pokemonEntity, new Class[0]);
        this.parameter = parameter;
    }

    @Override
    public boolean canStart()
    {
        int i = this.mob.getLastAttackedTime();
        LivingEntity livingEntity = this.mob.getAttacker();
        if (i == this.lastAttackedTime || livingEntity == null) {
            return false;
        }
        if (livingEntity.getType() == EntityType.PLAYER && this.mob.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            return false;
        }
        return true;
    }

    @Override
    public void start()
    {
        this.mob.setTarget(this.mob.getAttacker());
        this.target = this.mob.getTarget();
        this.lastAttackedTime = this.mob.getLastAttackedTime();
        this.damageType(target);
    }

    @Override
    public boolean shouldContinue()
    {
        return false;
    }

    public void damageType(LivingEntity target)
    {
        if (this.mob.getRecentDamageSource() != null)
        {
            switch (parameter)
            {
                case "damage":
                    if (!mob.getRecentDamageSource().isProjectile())
                        target.damage(DamageSource.thorns(mob).setBypassesArmor(), 4);
                    break;
                case "poison":
                case "slowness":
                case "mining_fatigue":
                    if (!mob.getRecentDamageSource().isProjectile())
                    {
                        StatusEffectInstance effect = LivelierPokemonManager.getStatusEffect(parameter, 100, 1);
                        if (effect != null)
                        {
                            if (Math.random() < 0.30 && target.canHaveStatusEffect(effect))
                                target.addStatusEffect(effect, this.mob);
                        }
                    }
                    break;
                case "fire":
                    if (!mob.getRecentDamageSource().isProjectile())
                    {
                        if (Math.random() < 0.30 && !target.isFireImmune())
                            target.setOnFireFor(3);
                    }
                    break;
                case "durability":
                    if (this.mob.getRecentDamageSource().getAttacker() instanceof PlayerEntity)
                    {
                        PlayerEntity player = (PlayerEntity) this.mob.getRecentDamageSource().getAttacker();
                        ItemStack item = player.getMainHandStack();
                        if (item.isDamageable())
                        {
                            item.damage(3, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                        }
                    }
                    break;
                case "hide":
                    StatusEffectInstance invisibility = LivelierPokemonManager.getStatusEffect("invisibility", 100, 1);
                    if (invisibility != null)
                    {
                        if (Math.random() < 0.30 && target.canHaveStatusEffect(invisibility))
                            this.mob.addStatusEffect(invisibility, this.mob);
                    }
                    break;
                default:
            }
        }
    }
}
