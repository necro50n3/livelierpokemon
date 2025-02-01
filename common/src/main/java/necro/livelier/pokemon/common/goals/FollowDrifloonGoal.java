package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;

public class FollowDrifloonGoal extends FollowMobGoal {

    private final Villager villagerEntity;
    private final int radius;
    private final int convertTime;
    private PokemonEntity drifloon;
    private int tick;
    private int conversion;

    public FollowDrifloonGoal(AbstractVillager villagerEntity, int radius, int convertTime) {
        super(villagerEntity, 0.5, 0, 16);
        this.villagerEntity = (Villager) villagerEntity;
        this.radius = radius;
        this.convertTime = convertTime;
        this.drifloon = null;
        this.tick = 0;
        this.conversion = 0;
    }

    @Override
    public boolean canUse() {
        return this.villagerEntity.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.villagerEntity.isBaby()) return false;
        if (this.drifloon == null) return true;
        if (this.villagerEntity.distanceTo(this.drifloon) <= 4) this.conversion++;
        if (this.conversion  >= this.convertTime) {
            new Zombie(villagerEntity.level()).killedEntity((ServerLevel) villagerEntity.level(),  villagerEntity);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (this.drifloon != null)
            this.villagerEntity.getLookControl().setLookAt(this.drifloon, 10.0F, (float)this.villagerEntity.getMaxHeadXRot());
        if (--this.tick > 0) return;

        this.tick = this.adjustedTickDelay(10);
        if (this.villagerEntity.level().getEntitiesOfClass(Villager.class, this.villagerEntity.getBoundingBox().inflate(16)).size() > 1) return;
        this.drifloon = TargetHelper.getNearestPokemon(this.villagerEntity, this.radius,
            (pokemonEntity) -> SpawnHelper.isSpecies(pokemonEntity, "Drifloon")
        );
        if (this.drifloon == null) return;
        this.villagerEntity.getNavigation().moveTo(this.drifloon, 0.5);
    }
}
