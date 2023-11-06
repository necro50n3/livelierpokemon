package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class MagnetPullGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private Player owner;
    private List<ItemEntity> entityList;

    public MagnetPullGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
    }

    public void pullItem(ItemEntity entity)
    {
        entity.playerTouch(owner);
    }

    @Override
    public boolean canUse() {
        if (owner == null)
        {
            owner = (Player) this.pokemonEntity.getOwner();
            return false;
        }
        entityList = this.getItemEntities();
        return !entityList.isEmpty();
    }

    @Override
    public void start()
    {
        if(this.canUse())
        {
            for (ItemEntity entity : entityList)
            {
                this.pullItem(entity);
            }
        }
    }

    public List<ItemEntity> getItemEntities()
    {
        AABB box = new AABB(owner.getX()-6,owner.getY()-6,owner.getZ()-6,owner.getX()+6,owner.getY()+6,owner.getZ()+6);
        return owner.level().getEntitiesOfClass(ItemEntity.class, box);
    }

    @Override
    public void tick() {
        this.start();
    }
}
