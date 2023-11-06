package necro.livelier.pokemon.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import necro.livelier.pokemon.fabric.behaviors.VillagerFollowDrifloonGoal;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager
{
    protected VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world)
    {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    protected void initInject(EntityType<? extends AbstractVillager> entityType, Level world, CallbackInfo info)
    {
        this.goalSelector.addGoal(2, new VillagerFollowDrifloonGoal(this));
    }
}
