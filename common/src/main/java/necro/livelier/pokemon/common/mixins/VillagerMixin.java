package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.goals.FollowDrifloonGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    protected VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    protected void initInject(EntityType<? extends AbstractVillager> entityType, Level world, CallbackInfo info) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.DRIFLOON) this.goalSelector.addGoal(2, new FollowDrifloonGoal(
            this, config.drifloon_radius, config.drifloon_convert_time
        ));
    }
}
