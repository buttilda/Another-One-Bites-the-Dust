package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThaumcraftRecipes extends RecipesModule {

	public static final String[] blacklist = { "iron", "gold", "copper", "tin", "lead", "silver" };

	public static void init() {
		label: for (Ore ore : Ore.ores)
			if (ore.shouldThaumcraft()) {
				for (String bEntry : blacklist)
					if (ore.name().equalsIgnoreCase(bEntry))
						continue label;

				String name = ore.name();
				ItemStack cluster = getOreDictItem("cluster" + name);
				for (ItemStack block : OreDictionary.getOres("ore" + name)) {
					String s1 = Item.getIdFromItem(block.getItem()) + "," + block.getItemDamage();
					String s2 = Item.getIdFromItem(cluster.getItem()) + "," + cluster.getItemDamage();
					FMLInterModComms.sendMessage("Thaumcraft", "nativeCluster", s1 + "," + s2 + "," + ore.chance());
				}
				GameRegistry.addSmelting(cluster, getOreDictItem("ingot" + name, 2), 0.2F);
			}
	}

	public static void postInit() {
		ItemStack cluster = null;
		ArrayList<ResearchPage> pages = new ArrayList<ResearchPage>();
		pages.add(new ResearchPage("tc.research_page.PUREORE.1"));
		for (Ore ore : Ore.ores)
			if (ore.shouldThaumcraft()) {
				String name = ore.name();
				if (!name.equals("Iron") && !name.equals("Gold") && !name.equals("Lead") && !name.equals("Silver") && !name.equals("Tin") && !name.equals("Copper")) {
					cluster = getOreDictItem("cluster" + name).copy();
					cluster.stackSize = 1;

					CrucibleRecipe recipe = ThaumcraftApi.addCrucibleRecipe("PUREORE", cluster, "ore" + name, new AspectList().merge(Aspect.METAL, 1).merge(Aspect.ORDER, 1));
					ConfigResearch.recipes.put("Pure" + name, recipe);
					pages.add(new ResearchPage(recipe));
				}
			}

		if (cluster != null) {
			ResearchCategories.registerCategory("AOBD", new ResourceLocation(Reference.MOD_ID, "textures/items/dust.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
			new ResearchItem("PUREORE", "AOBD", new AspectList().add(Aspect.METAL, 5).add(Aspect.ORDER, 2), 0, 0, 1, cluster.copy()).setPages(pages.toArray(new ResearchPage[0])).setConcealed().setSecondary().setParents("PUREIRON").registerResearchItem();
		}
	}
}