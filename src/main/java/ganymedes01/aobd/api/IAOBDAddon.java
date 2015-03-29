package ganymedes01.aobd.api;

import ganymedes01.aobd.ore.Ore;

import java.util.Collection;

public interface IAOBDAddon {

	void receiveOreList(Collection<Ore> ores);
}