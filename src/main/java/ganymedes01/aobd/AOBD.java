package ganymedes01.aobd;

import ganymedes01.aobd.configuration.ConfigurationHandler;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.RecipesHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
public class AOBD {

	@Instance(Reference.MOD_ID)
	public static AOBD instance;

	public static boolean enableIC2 = true;
	public static boolean enableRailcraft = true;
	public static boolean enableMekanism = true;
	public static boolean enableEnderIO = true;
	public static boolean enableThaumcraft = true;

	public static CreativeTabs tab = new CreativeTabs(Reference.MOD_ID) {

		@Override
		public Item getTabIconItem() {
			return Items.glowstone_dust;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.preInit(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (!Loader.isModLoaded("IC2"))
			enableIC2 = false;
		if (!Loader.isModLoaded("Railcraft"))
			enableRailcraft = false;
		if (!Loader.isModLoaded("Mekanism"))
			enableMekanism = false;
		if (!Loader.isModLoaded("EnderIO"))
			enableEnderIO = false;
		if (!Loader.isModLoaded("Thaumcraft"))
			enableThaumcraft = false;

		OreFinder.preInit();
		ConfigurationHandler.initOreConfigs();
		OreFinder.init();
		ConfigurationHandler.initCustomMetals();

		RecipesHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RecipesHandler.postInit();
	}
}