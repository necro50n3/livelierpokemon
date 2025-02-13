package necro.livelier.pokemon.common;

import com.mojang.logging.LogUtils;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.config.CategoryCache;
import necro.livelier.pokemon.common.config.CategoryConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import org.slf4j.Logger;

public class LivelierPokemon {
    public static final String MODID = "livelierpokemon";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static AbilityConfig abilityConfig;
    private static CategoryConfig categoryConfig;

    public static AbilityConfig getAbilityConfig() {
        return abilityConfig;
    }

    public static CategoryConfig getCategoryConfig() {
        return categoryConfig;
    }

    public static void init() {
        LOGGER.info("Initiating {}", MODID);

        AutoConfig.register(AbilityConfig.class, JanksonConfigSerializer::new);
        AutoConfig.register(CategoryConfig.class, JanksonConfigSerializer::new);
        abilityConfig = AutoConfig.getConfigHolder(AbilityConfig.class).getConfig();
        categoryConfig = AutoConfig.getConfigHolder(CategoryConfig.class).getConfig();
        CategoryCache.init();

        SpawnHelper.init();
    }
}
