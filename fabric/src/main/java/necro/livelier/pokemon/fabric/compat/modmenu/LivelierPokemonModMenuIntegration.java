package necro.livelier.pokemon.fabric.compat.modmenu;

import necro.livelier.pokemon.common.config.ClientConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class LivelierPokemonModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ClientConfig.class, parent).get();
    }
}
