package ganymedes01.aobd;

import ganymedes01.aobd.configuration.ConfigHandler;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.ModulesHandler;
import ganymedes01.aobd.recipes.modules.MekanismModule;
import ganymedes01.aobd.recipes.modules.UltraTechModule;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
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

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class AOBD {

	@Instance(Reference.MOD_ID)
	public static AOBD instance;

	public static Set<CompatType> enabledTypes = new HashSet<CompatType>();
	public static String userDefinedItems = "";
	public static String userDefinedGases = "";

	public static CreativeTabs tab = new CreativeTabs(Reference.MOD_ID) {

		@Override
		public Item getTabIconItem() {
			return Items.glowstone_dust;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.INSTANCE.preInit(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(ConfigHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void configType(boolean enabled, CompatType type) {
		if (enabled)
			enabledTypes.add(type);
		else
			enabledTypes.remove(type);
	}

	public static boolean isCompatEnabled(CompatType type) {
		return enabledTypes.contains(type);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		for (CompatType type : CompatType.values())
			configType(Loader.isModLoaded(type.modID()), type);

		if (AOBD.isCompatEnabled(CompatType.ULTRA_TECH))
			UltraTechModule.registerOres();

		// Find ores
		OreFinder.preInit();

		// Create configs for each ore
		ConfigHandler.INSTANCE.initOreConfigs();

		// Add items (dusts, crushed, cluster, etc)
		OreFinder.init();

		// Add recipes
		ModulesHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Add the rest of the recipes
		ModulesHandler.postInit();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPost(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 1) {
			// Calculate the ores colours
			OreFinder.initColours();

			// Create colour configs
			ConfigHandler.INSTANCE.initColourConfigs();
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPre(TextureStitchEvent.Pre event) {
		// Register icons for Mekanism's gases
		if (Loader.isModLoaded("Mekanism") && event.map.getTextureType() == 0)
			MekanismModule.registerIcons(event.map);
	}
}