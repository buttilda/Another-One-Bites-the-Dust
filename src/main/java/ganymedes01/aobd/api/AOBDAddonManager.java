package ganymedes01.aobd.api;

import ganymedes01.aobd.ore.Ore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AOBDAddonManager {

	private static List<IAOBDAddon> addons = new ArrayList<IAOBDAddon>();

	public static void registerAddon(IAOBDAddon addon) {
		addons.add(addon);
	}

	public static void passOreListToAddons(Collection<Ore> ores) {
		for (IAOBDAddon addon : addons)
			addon.receiveOreList(ores);
	}
}