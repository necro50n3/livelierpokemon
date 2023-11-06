package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class TeleportAroundGoal extends RandomStrollGoal
{
    private PokemonEntity pokemonEntity;
    private double xo;
    private double yo;
    private double zo;

    public TeleportAroundGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity, pokemonEntity.getSpeed());
        this.pokemonEntity = (PokemonEntity) pokemonEntity;
        this.xo = pokemonEntity.getX();
        this.yo = pokemonEntity.getY();
        this.zo = pokemonEntity.getZ();
    }
    
    @Override
    public boolean canUse()
    {
        return super.canUse() && this.canMove() && !pokemonEntity.isBusy() && pokemonEntity.getOwner() == null;
    }

    public boolean canMove()
    {
        return pokemonEntity.getBehaviour().getMoving().getWalk().getCanWalk() || pokemonEntity.getBehaviour().getMoving().getFly().getCanFly();
    }

    @Override
    public boolean canContinueToUse()
    {
        return super.canContinueToUse() && this.canMove() && !pokemonEntity.isBusy();
    }

    @Override
    public void start()
    {
        this.xo = pokemonEntity.getX();
        this.yo = pokemonEntity.getY();
        this.zo = pokemonEntity.getZ();
        if (pokemonEntity.randomTeleport(this.wantedX, this.wantedY, this.wantedZ, true))
        {
            if (!pokemonEntity.isSilent())
            {
                this.mob.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.mob.getSoundSource(), 1.0F, 1.0F);
                this.mob.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
