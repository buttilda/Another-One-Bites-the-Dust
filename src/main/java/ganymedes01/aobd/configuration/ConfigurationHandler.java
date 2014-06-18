package ganymedes01.aobd.configuration;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;

import java.awt.Color;
import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLLog;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static void preInit(File configFile) {
		configuration = new Configuration(configFile, true);

		try {
			configuration.load();

			AOBD.enableIC2 = getBoolean("Recipes", "IC2", AOBD.enableIC2);
			AOBD.enableRailcraft = getBoolean("Recipes", "Railcraft", AOBD.enableRailcraft);
			AOBD.enableMekanism = getBoolean("Recipes", "Mekanism", AOBD.enableMekanism);
			AOBD.enableEnderIO = getBoolean("Recipes", "EnderIO", AOBD.enableEnderIO);

			AOBD.userDefinedItems = getStringWithComment("Add-your-items-here", "items", "", "Add prefixes separated by commas.\nTextures will be aobd:prefix and aobd:prefix_overlay.\nExample: dust,cluster");

		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}

	public static void initOreConfigs() {
		try {
			configuration.load();

			for (Ore ore : Ore.ores) {
				String name = ore.name();

				ore.setIC2(getBoolean(name, "IC2", ore.shouldIC2()));
				ore.setRC(getBoolean(name, "Railcraft", ore.shouldRC()));
				ore.setEnderIO(getBoolean(name, "EnderIO", ore.shouldEnderIO()));
				ore.setMeka(getBoolean(name, "Mekanism", ore.shouldMeka()));

				ore.setExtra(getString(name, "extra", ore.extra()));
				ore.setEnergy(getDouble(name, "energy", ore.energy(1)));
				ore.setChance(getDouble(name, "chance", ore.chance()));
			}
		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}

	public static void initCustomMetals() {
		try {
			configuration.load();

			//Platinum-0x5cc9e8-dustTiny;
			for (String custom : getString("Custom", "custom", "").split(";")) {
				String[] data = custom.trim().split("-");
				if (data.length == 3)
					OreFinder.addCustomMetal(data[0].trim(), Color.decode(data[1].trim()), data[2].trim().split(","));
			}

		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}

	public static void initColourConfigs() {
		try {
			configuration.load();

			for (Ore ore : Ore.ores) {
				String name = ore.name();

				OreFinder.oreColourMap.put(name, getColour(name, "colour", OreFinder.oreColourMap.get(name)));
			}
		} catch (Exception e) {
			FMLLog.severe(Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}

	private static Color getColour(String category, String name, Color def) {
		String hex = String.format("0x%02x%02x%02x", def.getRed(), def.getGreen(), def.getBlue());
		return Color.decode(getString(category, name, hex));
	}

	private static String getString(String category, String name, String def) {
		return configuration.get(category, name, def).getString();
	}

	private static String getStringWithComment(String category, String name, String def, String comment) {
		return configuration.get(category, name, def, comment).getString();
	}

	private static boolean getBoolean(String category, String name, boolean def) {
		return configuration.get(category, name, def).getBoolean(def);
	}

	private static double getDouble(String category, String name, double def) {
		return configuration.get(category, name, def).getDouble(def);
	}
}