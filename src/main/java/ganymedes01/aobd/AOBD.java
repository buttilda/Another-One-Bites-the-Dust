package ganymedes01.aobd;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.aobd.api.AOBDAddonManager;
import ganymedes01.aobd.client.AOBDBlockRenderer;
import ganymedes01.aobd.client.ItemGlassBottleRenderer;
import ganymedes01.aobd.configuration.ConfigHandler;
import ganymedes01.aobd.items.AOBDGlassBottle;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.ModulesHandler;
import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.aobd.recipes.modules.Mekanism;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class AOBD {

	@Instance(Reference.MOD_ID)
	public static AOBD instance;

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

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Find ores
		OreFinder.preInit();

		// Create configs for each ore
		ConfigHandler.INSTANCE.initOreConfigs();

		// Creates the necessary support modules
		ModulesHandler.createModules();

		// Add items (dusts, crushed, cluster, etc)
		OreFinder.init();

		// Add recipes
		ModulesHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Add the rest of the recipes
		ModulesHandler.postInit();

		// Register special item renderers
		if (Side.CLIENT == event.getSide())
			registerRenderers();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPost(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 1) {
			// Calculate the ores colours
			OreFinder.initColours();

			// Create colour configs
			ConfigHandler.INSTANCE.initColourConfigs();

			// Tell add-ons to create their colours
			AOBDAddonManager.notifyColourCreation();
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPre(TextureStitchEvent.Pre event) {
		// Register icons for Mekanism's gases
		if (CompatType.MEKANISM.isEnabled() && event.map.getTextureType() == 0)
			Mekanism.registerIcons(event.map);

		// Register icons for molten metals
		if (event.map.getTextureType() == 0)
			if (CompatType.TINKERS_CONSTRUCT.isEnabled() || CompatType.MARICULTURE.isEnabled())
				RecipesModule.registerMoltenMetalIcons(event.map);
	}

	@SideOnly(Side.CLIENT)
	private void registerRenderers() {
		for (Item item : OreFinder.itemMap.values())
			if (item instanceof AOBDItem) {
				IItemRenderer renderer = ((AOBDItem) item).getSpecialRenderer();
				if (renderer != null)
					MinecraftForgeClient.registerItemRenderer(item, renderer);
			}

		for (Item item : AOBDGlassBottle.bottles)
			MinecraftForgeClient.registerItemRenderer(item, ItemGlassBottleRenderer.INSTANCE);

		RenderingRegistry.registerBlockHandler(new AOBDBlockRenderer());
	}
}