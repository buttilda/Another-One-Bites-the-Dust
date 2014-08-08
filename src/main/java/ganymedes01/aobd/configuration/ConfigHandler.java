package ganymedes01.aobd.configuration;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;

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
		AOBD.enableIC2 = getBoolean("Recipes", "IC2", true, AOBD.enableIC2);
		AOBD.enableRailcraft = getBoolean("Recipes", "Railcraft", true, AOBD.enableRailcraft);
		AOBD.enableMekanism = getBoolean("Recipes", "Mekanism", true, AOBD.enableMekanism);
		AOBD.enableEnderIO = getBoolean("Recipes", "EnderIO", true, AOBD.enableEnderIO);
		AOBD.enableFactorization = getBoolean("Recipes", "Factorization", true, AOBD.enableFactorization);

		AOBD.userDefinedItems = getStringWithComment("Custom", "items", "", "Add prefixes separated by commas.\nTextures will be aobd:prefix and aobd:prefix_overlay.\nExample: dust,cluster");
		AOBD.userDefinedGases = getStringWithComment("Custom", "gases", "", "Add ore names that will be turned into Mekanism gases (First letter must be capitalised). Example: Iron,Gold,Titanium");

		if (configFile.hasChanged())
			configFile.save();
	}

	public void initOreConfigs() {
		for (Ore ore : Ore.ores) {
			String name = ore.name();

			ore.setIC2(getBoolean(name, "IC2", true, ore.shouldIC2()));
			ore.setRC(getBoolean(name, "Railcraft", true, ore.shouldRC()));
			ore.setEnderIO(getBoolean(name, "EnderIO", true, ore.shouldEnderIO()));
			ore.setMekanism(getBoolean(name, "Mekanism", true, ore.shouldMekanism()));
			ore.setFactorization(getBoolean(name, "Factorization", true, ore.shouldFactorization()));

			ore.setExtra(getString(name, "extra", ore.extra()));
			ore.setEnergy(getDouble(name, "energy", ore.energy(1)));
			ore.setChance(getDouble(name, "chance", ore.chance()));

			usedCategories.add(name);
		}

		if (configFile.hasChanged())
			configFile.save();
	}

	public void initCustomMetals() {
		for (String custom : getStringWithComment("Custom", "custom", "", "Add custom metals.\n Example: Platinum-0x5cc9e8-dustTiny").split(";")) {
			String[] data = custom.trim().split("-");
			if (data.length == 3)
				OreFinder.addCustomMetal(data[0].trim(), Color.decode(data[1].trim()), data[2].trim().split(","));
		}

		if (configFile.hasChanged())
			configFile.save();
	}

	public void initColourConfigs() {
		for (Ore ore : Ore.ores) {
			String name = ore.name();

			OreFinder.oreColourMap.put(name, getColour(name, "colour", OreFinder.oreColourMap.get(name)));
		}

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID)) {
			configFile.load();

			preInit();
			initOreConfigs();
			initCustomMetals();
			initColourConfigs();
		}
	}

	private Color getColour(String category, String name, Color def) {
		String hex = String.format("0x%02x%02x%02x", def.getRed(), def.getGreen(), def.getBlue());
		return Color.decode(getString(category, name, hex));
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