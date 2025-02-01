package necro.livelier.pokemon.common.damage;

import necro.livelier.pokemon.common.LivelierPokemon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LivelierDamageType {
    public static final TagKey<DamageType> MAKES_CONTACT = TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "makes_contact"));
    public static final TagKey<DamageType> BYPASSES_WONDER_GUARD = TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "bypasses_wonder_guard"));

    public static final ResourceKey<DamageType> BAD_DREAMS = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "bad_dreams"));

    public static DamageSource getDamageSource(ResourceKey<DamageType> damageType, Level level, @Nullable Entity directEntity, @Nullable Entity causingEntity) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), directEntity, causingEntity);
    }
}
