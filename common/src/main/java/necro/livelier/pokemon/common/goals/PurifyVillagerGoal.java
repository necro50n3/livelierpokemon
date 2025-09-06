package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.Items;

import java.util.List;

public class PurifyVillagerGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final LivingEntity source;
    private final int radius;
    private int tick;

    public PurifyVillagerGoal(PokemonEntity pokemonEntity, int radius) {
        this.pokemonEntity = pokemonEntity;
        this.source = pokemonEntity.getOwner() == null ? pokemonEntity : pokemonEntity.getOwner();
        this.radius = radius;
        this.tick = 0;
    }

    @Override
    public boolean canUse() {
        return this.pokemonEntity.getPokemon().heldItem().is(Items.GOLDEN_APPLE);
    }

    @Override
    public void tick() {
        if (--this.tick > 0) return;
        this.tick = this.adjustedTickDelay(80);

        List<ZombieVillager> zombieVillagers = this.getZombieVillagers();
        if (zombieVillagers.isEmpty()) return;

        zombieVillagers.forEach(zombieVillager -> {
            if (zombieVillager.isConverting()) return;
            zombieVillager.startConverting(this.source.getUUID(), this.source.getRandom().nextInt(2401) + 3600);
        });
        if (this.pokemonEntity.getPokemon().heldItem().is(Items.GOLDEN_APPLE)) this.pokemonEntity.getPokemon().removeHeldItem();
    }

    public List<ZombieVillager> getZombieVillagers() {
        return this.pokemonEntity.level().getNearbyEntities(
            ZombieVillager.class, TargetingConditions.forNonCombat().ignoreLineOfSight(), this.pokemonEntity,
            this.pokemonEntity.getBoundingBox().inflate(this.radius, this.radius, this.radius)
        );
    }
}
