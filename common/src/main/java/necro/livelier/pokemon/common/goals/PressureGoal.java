package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PressureGoal extends PokemonRevengeGoal {
    private final int damage;

    public PressureGoal(PokemonEntity pokemonEntity, int damage) {
        super(pokemonEntity);
        this.damage = damage;
    }

    @Override
    public void doRevenge() {
        if (!(this.targetMob instanceof Player)) return;
        ItemStack item = this.targetMob.getMainHandItem();
        if (item.isDamageableItem()) {
            item.hurtAndBreak(this.damage, this.targetMob, EquipmentSlot.MAINHAND);
        }
    }
}
