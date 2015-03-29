package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

public class BuildCraftAdditions extends RecipesModule {

	public BuildCraftAdditions() {
		// @formatter:off
		super(CompatType.BC_ADDITIONS, 	"iron","gold", "diamond", "emerald", "coal", "charcoal", "obsidian", "enderpearl", "netherquartz",
										"bronze", "manganese", "heptizon", "damascussteel", "angmallen", "steel", "eximite", "meutoite",
										"desichalkos", "prometheum", "deepiron", "infuscolium", "blacksteel", "oureclase", "astralsilver",
										"carmot", "mithril", "rubracium", "quicksilver", "haderoth", "orichalcum", "celenegil", "adamantine",
										"atlarus", "tartarite", "ignatius", "shadowiron", "lemurite", "midasium", "vyroxeres", "ceruclase",
										"alduorite", "kalendrite", "vulcanite", "sanguinite", "shadowsteel", "inolashite", "amordrine", "zinc",
										"brass", "electrum", "aluminum", "ardite", "cobalt", "copper", "lead", "nickel", "platinum", "silver",
										"tin", "apatite", "osmium", "sulfur", "saltpeter", "certusquartz", "aluminumbrass", "alumite", "pigiron",
										"invar", "signalum", "lumium", "enderiumbase", "enderium", "eletricalsteel", "energeticalloy", "phasedgold",
										"redstonealloy", "conductiveiron", "phasediron", "darksteel", "soularium", "fzdarkiron");
		// @formatter:on
	}

	@Override
	public void initOre(Ore ore) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Input", "ore" + ore.name());
		nbt.setTag("Output", getOreStack("dust", ore, 2).writeToNBT(new NBTTagCompound()));

		FMLInterModComms.sendMessage("bcadditions", "addDustingRecipe", nbt);
	}
}
