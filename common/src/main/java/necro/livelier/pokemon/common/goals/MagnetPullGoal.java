package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MagnetPullGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final LivingEntity owner;
    private final int range;
    private int tick;

    public MagnetPullGoal(PokemonEntity pokemonEntity, int range) {
        this.pokemonEntity = pokemonEntity;
        this.owner = pokemonEntity.getOwner();
        this.range = range;
        this.tick = 0;
    }

    @Override
    public boolean canUse() {
        if (this.owner == null) return false;
        return this.pokemonEntity.distanceTo(this.owner) < 16;
    }

    @Override
    public void tick() {
        if (--this.tick > 0) return;

        this.tick = this.adjustedTickDelay(60);

        List<ItemEntity> items = this.owner.level().getEntitiesOfClass(
            ItemEntity.class, this.owner.getBoundingBox().inflate(this.range, 4, this.range), (item) ->
                item.isAlive() && (!this.owner.level().isClientSide() || item.tickCount > 1) &&
                !this.owner.is(item.getOwner()) && !item.getItem().isEmpty()
        );
        for (ItemEntity item : items) {
            item.playerTouch((Player) this.owner);
        }
    }
}
