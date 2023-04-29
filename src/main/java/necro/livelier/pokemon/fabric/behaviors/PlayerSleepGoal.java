package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;

public class PlayerSleepGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private List<PlayerEntity> playerList;
    private PlayerEntity player;

    public PlayerSleepGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canStart() {
        playerList = this.getLivingEntities();
        if (playerList.size() > 0)
        {
            for (PlayerEntity playerInstance : playerList)
            {
                if (playerInstance.getSleepTimer() > 80)
                {
                    player = playerInstance;
                    return true;
                }
                    
            }  
        }
        return false;
    }

    @Override
    public void start() {
        if(this.canStart())
        {
            this.triggerWakeUp();
            player = null;
        }
    }

    public List<PlayerEntity> getLivingEntities()
    {
        Box box = new Box(pokemonEntity.getX()-16,pokemonEntity.getY()-16,pokemonEntity.getZ()-16,pokemonEntity.getX()+16,pokemonEntity.getY()+16,pokemonEntity.getZ()+16);
        return pokemonEntity.world.getEntitiesByType(TypeFilter.instanceOf(PlayerEntity.class), box, EntityPredicates.VALID_ENTITY);
    }

    public void triggerWakeUp()
    {
        player.damage(DamageSource.mob(pokemonEntity).setBypassesArmor(), 6);
        player.wakeUp();
        player.sendMessage(Text.of("You wake up from a terrifying nightmare."));
    }
    
}
