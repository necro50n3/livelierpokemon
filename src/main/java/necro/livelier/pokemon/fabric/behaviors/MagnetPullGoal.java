package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;

public class MagnetPullGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private PlayerEntity owner;
    private List<ItemEntity> entityList;

    public MagnetPullGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
    }

    public void pullItem(ItemEntity entity)
    {
        entity.onPlayerCollision(owner);
    }

    @Override
    public boolean canStart() {
        if (owner == null)
        {
            owner = (PlayerEntity) this.pokemonEntity.getOwner();
            return false;
        }
        entityList = this.getItemEntities();
        return !entityList.isEmpty();
    }

    @Override
    public void start()
    {
        if(this.canStart())
        {
            for (ItemEntity entity : entityList)
            {
                this.pullItem(entity);
            }
        }
    }

    public List<ItemEntity> getItemEntities()
    {
        Box box = new Box(owner.getX()-6,owner.getY()-6,owner.getZ()-6,owner.getX()+6,owner.getY()+6,owner.getZ()+6);
        return owner.world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), box, EntityPredicates.VALID_ENTITY);
    }

    @Override
    public void tick() {
        this.start();
    }
}
