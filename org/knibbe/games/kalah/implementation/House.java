package org.knibbe.games.kalah.implementation;

/**
 * Created by roelof on 5/16/17.
 */
public class House
{
	final Players owner;
	final HouseType typeHouse;
	public Integer stonesCount;

	House(final Players owner, final HouseType type, final Integer stonesCount) {
		this.owner = owner;
		this.typeHouse = type;
		this.stonesCount = stonesCount;
	}
}
