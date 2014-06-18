package ganymedes01.aobd;

import ganymedes01.aobd.configuration.ConfigurationHandler;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.RecipesHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
public class AOBD {

	@Instance(Reference.MOD_ID)
	public static AOBD instance;

	public static boolean enableIC2 = true;
	public static boolean enableRailcraft = true;
	public static boolean enableMekanism = true;
	public static boolean enableTE3 = true;
	public static boolean enableEnderIO = true;
	public static boolean enableThaumcraft = true;
	public static String userDefinedItems = "";

	public static CreativeTabs tab = new CreativeTabs(Reference.MOD_ID) {

		@Override
		public Item getTabIconItem() {
			return Items.glowstone_dust;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.preInit(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(this);
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
		if (!Loader.isModLoaded("ThermalExpansion"))
			enableTE3 = false;

		// Find ores
		OreFinder.preInit();

		// Create configs for each ore
		ConfigurationHandler.initOreConfigs();

		// Add items (dusts, crushed, cluster, etc)
		OreFinder.init();

		// Add user defined metals
		ConfigurationHandler.initCustomMetals();

		// Add recipes
		RecipesHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Add the rest of the recipes
		RecipesHandler.postInit();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 1) {
			// Calculate the ores colours
			OreFinder.initColours();

			//Create colour configs
			ConfigurationHandler.initColourConfigs();
		}
	}
}