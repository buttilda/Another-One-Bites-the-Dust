package ganymedes01.aobd.configuration;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

public class ConfigurationHandler {

	public static Configuration configuration;

	public static void init(File configFile) {
		configuration = new Configuration(configFile);

		try {
			configuration.load();

			AOBD.dustsID = configuration.getItem("Dusts item id", 666).getInt(666);

			AOBD.energyMultiplier = configuration.get("How much more expensive it is to process Cobalt/Ardite on TE3 machines", "energyMultiplier", AOBD.energyMultiplier).getDouble(AOBD.energyMultiplier);

			AOBD.enableIC2 = configuration.get("Enable IC2 Recipes", "enableIC2", AOBD.enableIC2).getBoolean(AOBD.enableIC2);
			AOBD.enableRailcraft = configuration.get("Enable Railcraft Recipes", "enableRailcraft", AOBD.enableRailcraft).getBoolean(AOBD.enableRailcraft);
			AOBD.enableMekanism = configuration.get("Enable Mekanism Recipes", "enableMekanism", AOBD.enableMekanism).getBoolean(AOBD.enableMekanism);

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
			throw new RuntimeException(e);
		} finally {
			configuration.save();
		}
	}
}