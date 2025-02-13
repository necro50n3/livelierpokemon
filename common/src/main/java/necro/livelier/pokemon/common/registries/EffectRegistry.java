package necro.livelier.pokemon.common.registries;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public abstract class EffectRegistry {
    public static Holder<MobEffect> PARALYSIS;

    public static Holder<MobEffect> ELECTRIC_TERRAIN;
    public static Holder<MobEffect> GRASSY_TERRAIN;
    public static Holder<MobEffect> MISTY_TERRAIN;
    public static Holder<MobEffect> PSYCHIC_TERRAIN;

    public static void registerServerMode() {
        EffectRegistry.PARALYSIS = MobEffects.MOVEMENT_SLOWDOWN;
    }
}
