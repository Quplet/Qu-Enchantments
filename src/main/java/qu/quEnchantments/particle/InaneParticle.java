package qu.quEnchantments.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class InaneParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public InaneParticle(ClientWorld clientWorld, double x, double y, double z, double dx, double dy, double dz, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, dx, dy, dz);
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = dx;
        this.velocityY = dy;
        this.velocityZ = dz;
        this.scale = 0.1f * (this.random.nextFloat() * 0.2f + 0.5f);
        this.maxAge = (int)(Math.random() * 10.0) + 40;
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.alpha = 1.0f - ((float)this.age / this.maxAge);
        this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            InaneParticle inaneParticle = new InaneParticle(clientWorld, d, e, f, g, h, i, spriteProvider);
            inaneParticle.setSprite(this.spriteProvider);
            return inaneParticle;
        }
    }
}
