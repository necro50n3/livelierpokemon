package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class RandomTeleportGoal extends RandomStrollGoal
{
    private final PokemonEntity pokemonEntity;
    private double xo;
    private double yo;
    private double zo;

    public RandomTeleportGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity, pokemonEntity.getSpeed());
        this.pokemonEntity = pokemonEntity;
        this.xo = pokemonEntity.getX();
        this.yo = pokemonEntity.getY();
        this.zo = pokemonEntity.getZ();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.canMove() && !this.pokemonEntity.isBusy() && this.pokemonEntity.getOwner() == null;
    }

    public boolean canMove() {
        return this.pokemonEntity.getBehaviour().getMoving().getWalk().getCanWalk() ||
            this.pokemonEntity.getBehaviour().getMoving().getFly().getCanFly();
    }

    @Override
    public void start() {
        this.xo = this.pokemonEntity.getX();
        this.yo = this.pokemonEntity.getY();
        this.zo = this.pokemonEntity.getZ();
        if (this.pokemonEntity.randomTeleport(this.wantedX, this.wantedY, this.wantedZ, true))
        {
            if (!this.pokemonEntity.isSilent())
            {
                this.pokemonEntity.level().playSound(null, this.xo, this.yo, this.zo,
                    SoundEvents.ENDERMAN_TELEPORT, this.mob.getSoundSource(), 1.0F, 1.0F);
                this.pokemonEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}