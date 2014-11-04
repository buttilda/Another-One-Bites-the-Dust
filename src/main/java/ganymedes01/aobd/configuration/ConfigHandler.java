package ganymedes01.aobd.configuration;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	public static ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration configFile;
	public Set<String> usedCategories = new HashSet<String>();

	public void preInit(File file) {
		configFile = new Configuration(file, true);

		preInit();
		usedCategories.add("Recipes");
		usedCategories.add("Custom");
	}

	private void preInit() {
		for (CompatType type : CompatType.values())
			AOBD.configType(getBoolean("Recipes", type.modID(), true, AOBD.isCompatEnabled(type)), type);

		AOBD.userDefinedItems = getStringWithComment("Custom", "items", "", "Add prefixes separated by commas.\nTextures will be aobd:prefix and aobd:prefix_overlay.\nExample: dust,cluster");
		AOBD.userDefinedGases = getStringWithComment("Custom", "gases", "", "Add ore names that will be turned into Mekanism gases (First letter must be capitalised). Example: Iron,Gold,Titanium");

		if (configFile.hasChanged())
			configFile.save();
	}

	public void initOreConfigs() {
		for (Ore ore : Ore.ores) {
			String name = ore.name();

			for (CompatType type : CompatType.values())
				ore.configType(getBoolean(name, type.modID(), true, true), type);
			ore.setDisabled(getBoolean(name, "Disable All", true, false));

			ore.setExtra(getString(name, "extra", ore.extra()));
			ore.setEnergy(getDouble(name, "energy", ore.energy(1)));

			usedCategories.add(name);
		}

		if (configFile.hasChanged())
			configFile.save();
	}

	public void initColourConfigs() {
		for (Ore ore : Ore.ores)
			ore.setColour(getColour(ore.name(), "colour", ore.colour()));

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID)) {
			configFile.load();

			preInit();
			initOreConfigs();
			initColourConfigs();
		}
	}

	private Color getColour(String category, String name, int def) {
		return Color.decode(getString(category, name, "0x" + Integer.toHexString(def)));
	}

	private String getString(String category, String name, String def) {
		return configFile.get(category, name, def).getString();
	}

	private String getStringWithComment(String category, String name, String def, String comment) {
		return configFile.get(category, name, def, comment).setRequiresMcRestart(true).getString();
	}

	private boolean getBoolean(String category, String name, boolean requiresRestart, boolean def) {
		return configFile.get(category, name, def).setRequiresMcRestart(requiresRestart).getBoolean(def);
	}

	private double getDouble(String category, String name, double def) {
		return configFile.get(category, name, def).getDouble(def);
	}
}