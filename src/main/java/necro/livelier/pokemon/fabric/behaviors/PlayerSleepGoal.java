package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class PlayerSleepGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private List<Player> playerList;
    private Player player;

    public PlayerSleepGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canUse() {
        playerList = this.getLivingEntities();
        if (playerList.size() > 0)
        {
            for (Player playerInstance : playerList)
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
        if(this.canUse())
        {
            this.triggerWakeUp();
            player = null;
        }
    }

    public List<Player> getLivingEntities()
    {
        AABB box = new AABB(pokemonEntity.getX()-16,pokemonEntity.getY()-16,pokemonEntity.getZ()-16,pokemonEntity.getX()+16,pokemonEntity.getY()+16,pokemonEntity.getZ()+16);
        return pokemonEntity.level().getEntitiesOfClass(Player.class, box);
    }

    public void triggerWakeUp()
    {
        player.hurt(pokemonEntity.damageSources().magic(), 6);
        player.stopSleeping();
        player.sendSystemMessage(Component.literal("You wake up from a terrifying nightmare."));
    }
    
}
