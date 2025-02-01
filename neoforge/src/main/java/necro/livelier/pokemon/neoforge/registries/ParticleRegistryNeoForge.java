package necro.livelier.pokemon.neoforge.registries;

import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleRegistryNeoForge extends ParticleRegistry {
    public static void register() {
        ELECTRIC_TERRAIN = new SimpleParticleType(false);
        GRASSY_TERRAIN = new SimpleParticleType(false);
        MISTY_TERRAIN = new SimpleParticleType(false);
        PSYCHIC_TERRAIN = new SimpleParticleType(false);
    }
}
