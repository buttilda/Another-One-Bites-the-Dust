package ganymedes01.aobd.ore;

import ganymedes01.aobd.lib.CompatType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ore {

	public static final ArrayList<Ore> ores = new ArrayList<Ore>();

	private final String name;
	private String extra;
	private double energy;
	private final Set<CompatType> enabledTypes = new HashSet<CompatType>();
	private boolean disabled = false;
	private Color colour = Color.WHITE;

	public static Ore newOre(String name) {
		if (name.equals("Cobalt"))
			return new Ore(name, "Iron", 3);
		else if (name.equals("Ardite"))
			return new Ore(name, "Gold", 3);
		else if (name.equals("Aluminium"))
			return new Ore(name, "Iron");
		else if (name.equals("Copper"))
			return new Ore(name, "Iron");
		else if (name.equals("Tin"))
			return new Ore(name, "Iron");
		else if (name.equals("Lead"))
			return new Ore(name, "Gold");
		else if (name.equals("Iron"))
			return new Ore(name, "Gold");
		else if (name.equals("Gold"))
			return new Ore(name, "Iron");
		else
			return new Ore(name);
	}

	private Ore(String name) {
		this(name, name, 1);
	}

	private Ore(String name, double energy) {
		this(name, name, energy);
	}

	private Ore(String name, String extra) {
		this(name, extra, 1);
	}

	private Ore(String name, String extra, double energy) {
		this.name = name;
		this.extra = extra;
		this.energy = energy;
		ores.add(this);

		enabledTypes.addAll(Arrays.asList(CompatType.values()));
	}

	public String name() {
		return name;
	}

	public String extra() {
		return extra;
	}

	public double energy(double e) {
		return e * energy;
	}

	public void setEnergy(double e) {
		energy = e;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public boolean isCompatEnabled(CompatType type) {
		return isEnabled() && enabledTypes.contains(type);
	}

	public boolean isEnabled() {
		return !disabled;
	}

	public void configType(boolean enable, CompatType type) {
		if (enable)
			enabledTypes.add(type);
		else
			enabledTypes.remove(type);
	}

	public void setDisabled(boolean flag) {
		disabled = flag;
	}

	public int colour() {
		return colour.getRGB() & 0x00FFFFFF;
	}

	public void setColour(Color colour) {
		if (colour != null)
			this.colour = colour;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Ore && name.equals(((Ore) obj).name);
	}

	@Override
	public String toString() {
		return name();
	}
}