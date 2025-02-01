package necro.livelier.pokemon.common.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TerrainParticle extends TextureSheetParticle {
    private int maxAge;

    TerrainParticle(ClientLevel clientLevel, SpriteSet spriteSet, double d, double e, double f, double g, double h, double i) {
        super(clientLevel, d, e + 0.075, f, g, h, i);
        this.setSize(0.01f, 0.01f);
        this.pickSprite(spriteSet);
        this.quadSize *= this.random.nextFloat() * 0.6f + 0.2f;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = true;
        this.friction = 1.0f;
        this.gravity = 0.0f;
        this.maxAge = 1;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    protected void fadeOut() {
        this.alpha = (-(1/(float) this.maxAge) * this.age + 1);
    }

    @Environment(EnvType.CLIENT)
    public static class ElectricTerrainProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ElectricTerrainProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            if (clientLevel.getRandom().nextInt(2) == 0) return null;
            TerrainParticle terrainParticle = new TerrainParticle(clientLevel, this.sprite, d, e, f, 0.0, -0.8, 0.0);
            terrainParticle.scale(0.35f);
            terrainParticle.setColor(1f, 1f, 0f);
            terrainParticle.lifetime = 1;
            terrainParticle.gravity = -0.05f;
            return terrainParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class GrassyTerrainProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public GrassyTerrainProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            if (clientLevel.getRandom().nextInt(16) != 0) return null;
            TerrainParticle terrainParticle = new TerrainParticle(clientLevel, this.sprite, d, e, f, 0.0, -0.8, 0.0);
            terrainParticle.scale(0.5f);
            terrainParticle.lifetime = 3 + clientLevel.getRandom().nextInt(5);
            terrainParticle.xd *= 0.02;
            terrainParticle.yd *= 0.02;
            terrainParticle.zd *= 0.02;
            return terrainParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class MistyTerrainProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public MistyTerrainProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            if (clientLevel.getRandom().nextInt(64) != 0) return null;
            TerrainParticle terrainParticle = new TerrainParticle(clientLevel, this.sprite, d, e - 0.2, f, 0.0, -0.8, 0.0) {
                private final float rotSpeed = (clientLevel.getRandom().nextFloat() - 0.5f) * 0.05f;

                @Override
                public void tick() {
                    this.oRoll = this.roll;
                    this.roll += 3.1415927f * this.rotSpeed;
                    if (this.onGround) this.oRoll = this.roll = 0.0f;
                    super.tick();
                    this.fadeOut();
                }
            };
            terrainParticle.scale(1.5f);
            terrainParticle.rCol = Mth.nextFloat(clientLevel.getRandom(), 0.8176471f, 0.9745098f);
            terrainParticle.gCol = Mth.nextFloat(clientLevel.getRandom(), 0.6235294f, 0.7764706f);
            terrainParticle.bCol = terrainParticle.gCol;
            terrainParticle.setAlpha(0.5f);
            terrainParticle.lifetime = clientLevel.getRandom().nextInt(10, 40);
            terrainParticle.maxAge = terrainParticle.lifetime;
            terrainParticle.gravity = 0.02f;
            terrainParticle.xd *= 0.5;
            terrainParticle.yd *= 0.2;
            terrainParticle.zd *= 0.5;
            return terrainParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class PsychicTerrainProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public PsychicTerrainProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            if (clientLevel.getRandom().nextInt(64) != 0) return null;
            TerrainParticle terrainParticle = new TerrainParticle(clientLevel, this.sprite, d, e - 0.2, f, 0.0, -0.8, 0.0) {
                @Override
                public void tick() {
                    super.tick();
                    this.fadeOut();
                }
            };
            terrainParticle.scale(0.8f);
            terrainParticle.lifetime = clientLevel.getRandom().nextInt(40);
            terrainParticle.maxAge = terrainParticle.lifetime;
            terrainParticle.gravity = -0.02f;
            terrainParticle.hasPhysics = false;
            terrainParticle.setParticleSpeed(0, h, 0);
            if (clientLevel.getRandom().nextInt(2) == 0) terrainParticle.setColor(1.0f, 1.0f, 1.0f);
            return terrainParticle;
        }
    }
}
