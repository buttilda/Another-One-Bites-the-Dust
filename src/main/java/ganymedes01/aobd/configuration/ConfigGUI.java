package ganymedes01.aobd.configuration;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import ganymedes01.aobd.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class ConfigGUI extends GuiConfig {

	public ConfigGUI(GuiScreen parent) {
		super(parent, getElements(), Reference.MOD_ID, Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.INSTANCE.configFile.toString()));
	}

	@SuppressWarnings({ "rawtypes" })
	private static List<IConfigElement> getElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		for (String category : ConfigHandler.INSTANCE.usedCategories)
			list.add(new ConfigElement(ConfigHandler.INSTANCE.configFile.getCategory(category)));
		return list;
	}
}