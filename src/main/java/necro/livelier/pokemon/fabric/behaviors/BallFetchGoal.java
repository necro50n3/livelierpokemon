package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.List;

public class BallFetchGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private PlayerEntity owner;
    private ItemStack item;
    private World world;
    private EmptyPokeBallEntity pokeballEntityInstance;
    private boolean pokeballHit;
    private boolean pokemonCaught;

    public BallFetchGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
        this.world = pokemonEntity.getWorld();
        pokeballHit = false;
        pokemonCaught = false;
    }
    

    @Override
    public boolean canStart()
    {
        if (owner == null)
        {
            owner = (PlayerEntity) pokemonEntity.getOwner();
            return false;
        }
        pokeballEntityInstance = this.getThrownPokeBall();
        if (pokeballEntityInstance != null)
        {
            if (item == null)
                item = pokeballEntityInstance.getPokeBall().stack(1);
            this.checkPokeball();
        }
        if (item == null)
            return false;
        if (!pokeballHit || pokemonCaught)
            return false;
        if (!pokemonEntity.getPokemon().heldItem().isEmpty())
            return false;
        if (pokeballEntityInstance != null)
            return false;
        return true;
    }

    @Override
    public void start()
    {
        if (this.canStart())
        {
            pokemonEntity.getPokemon().swapHeldItem(item, false);
            pokeballHit = false;
            pokemonCaught = false;
            item = null;
            owner.sendMessage(Text.of(pokemonEntity.getDisplayName().getString() + " has retrieved your Poké Ball!"));
        }
    }

    public EmptyPokeBallEntity getThrownPokeBall()
    {
        Box box = new Box(pokemonEntity.getX()-10,pokemonEntity.getY()-10,pokemonEntity.getZ()-10,pokemonEntity.getX()+10,pokemonEntity.getY()+10,pokemonEntity.getZ()+10);
        List<EmptyPokeBallEntity> pokeBallList = world.getEntitiesByType(TypeFilter.instanceOf(EmptyPokeBallEntity.class), box, EntityPredicates.VALID_ENTITY);
        for (EmptyPokeBallEntity pokeballEntityInstance : pokeBallList)
        {
            if (pokeballEntityInstance.getOwner().equals(owner))
                return pokeballEntityInstance;
        }
        return null;
    }

    public void checkPokeball()
    {
        if (pokeballEntityInstance.getCaptureState().get().toString().equals("3"))
            pokeballHit = true;
        else if (pokeballEntityInstance.getCaptureState().get().toString().equals("4"))
            pokemonCaught = true;
    }
}
