package qu.quEnchantments.util.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import qu.quEnchantments.QuEnchantments;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        QuEnchantments.LOGGER.info("Registered ModConfig Screen for " + QuEnchantments.MOD_ID);
        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }
}
