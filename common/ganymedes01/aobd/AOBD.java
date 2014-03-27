package ganymedes01.aobd;

import ganymedes01.aobd.configuration.ConfigurationHandler;
import ganymedes01.aobd.items.DustsItem;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.recipes.RecipesHandler;

import java.io.File;

import net.minecraft.item.Item;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class AOBD {

	@Instance(Reference.MOD_ID)
	public static AOBD instance;

	public static boolean enableIC2 = true;
	public static boolean enableRailcraft = true;
	public static boolean enableMekanism = true;
	public static boolean enableEnderIO = true;
	public static double energyMultiplier = 3.0D;
	public static Item dusts;
	public static int dustsID;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + ".cfg"));
		dusts = new DustsItem();
		GameRegistry.registerItem(dusts, dusts.getUnlocalizedName());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		if (!Loader.isModLoaded("IC2"))
			enableIC2 = false;
		if (!Loader.isModLoaded("Railcraft"))
			enableRailcraft = false;
		if (!Loader.isModLoaded("Mekanism"))
			enableMekanism = false;
		if (!Loader.isModLoaded("EnderIO"))
			enableEnderIO = false;

		RecipesHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}