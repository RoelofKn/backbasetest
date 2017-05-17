package org.knibbe.games.kalah.implementation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



/*
* Board is represented as an array size 14.
* Each element is a house or a kalah.
*
* */

public class Board {


	//	Constants for declaring which array indexes address houses that belong to player 1 or 2
	private final static Set<Integer> PLAYER1_HOUSES = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
	private final static Set<Integer> PLAYER2_HOUSES = new HashSet<Integer>(Arrays.asList(7, 8, 9, 10, 11, 12));

	//	Constants for declaring which array indexes address kalahs that belong to player 1 or 2
	private final static Integer PLAYER1_KALAH = 6;
	private final static Integer PLAYER2_KALAH = 13;

	private final House[] board;

	Board(final int nrOfStones) {
		board = newBoard(nrOfStones);
	}
	private House[] newBoard(final int nrOfStones) {
		return  new House[] {
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER1, HouseType.KALAH, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.REGULAR, nrOfStones),
			new House(Players.PLAYER2, HouseType.KALAH, nrOfStones)
		};
	}



	public boolean turn(final Players player, final int choice) {

		//	Blunt check on valid input
		if (player == Players.PLAYER1) {
			if (choice < 0 || choice > 5)  throw new RuntimeException("Only houses 1 - 6 are allowed for player 1");
		} else {
			if (choice < 7 || choice > 12)  throw new RuntimeException("Only houses 8 - 13 are allowed for player 2");
		}

		boolean switchTurn = true;
		final int nrRemoved =  board[choice].stonesCount;
		board[choice].stonesCount = 0;
		int boardIndex = choice;

		for (int i=1; i<= nrRemoved; i++) {
			boardIndex += 1;  									// Move one house
			boardIndex += skipOtherKalah(player, boardIndex); 	// Skip one more if Kalah belongs to other player
			board[boardIndex % 14].stonesCount++;  					// increase stonesCount
		}

		//	What if the user ends in one of player's own houses that was empty
		if (player == Players.PLAYER1) {
			if ((PLAYER1_HOUSES.contains(boardIndex)) && board[boardIndex].stonesCount == 1) addToKalah(player, boardIndex);
		} else {
			if ((PLAYER2_HOUSES.contains(boardIndex)) && board[boardIndex].stonesCount == 1) addToKalah(player, boardIndex);
		}


		//	What if the user ends in own kalah
		if (player == Players.PLAYER1) {
			if (PLAYER1_KALAH.compareTo(boardIndex % 14) == 6)
				switchTurn = false;
		} else {
			if (PLAYER2_KALAH.compareTo(boardIndex % 14) == 13)
				switchTurn = false;
		}

		return switchTurn;

	}

	private void addToKalah(final Players player, final int boardIndex) {
		final int indexToAdd = player == Players.PLAYER1 ? 6 : 13;
		int total = 0;
		switch (boardIndex) {
		case 0 : total = board[0].stonesCount + board[12].stonesCount; board[indexToAdd].stonesCount += total; board[0].stonesCount = 0; board[12].stonesCount = 0; break;
		case 1 : total = board[1].stonesCount + board[11].stonesCount; board[indexToAdd].stonesCount += total; board[1].stonesCount = 0; board[11].stonesCount = 0; break;
		case 2 : total = board[2].stonesCount + board[10].stonesCount; board[indexToAdd].stonesCount += total; board[2].stonesCount = 0; board[10].stonesCount = 0; break;
		case 3 : total = board[3].stonesCount + board[9].stonesCount; board[indexToAdd].stonesCount += total; board[3].stonesCount = 0; board[9].stonesCount = 0; break;
		case 4 : total = board[4].stonesCount + board[8].stonesCount; board[indexToAdd].stonesCount += total; board[4].stonesCount = 0; board[8].stonesCount = 0; break;
		case 5 : total = board[5].stonesCount + board[7].stonesCount; board[indexToAdd].stonesCount += total; board[5].stonesCount = 0; board[7].stonesCount = 0; break;

		case 7 : total = board[5].stonesCount + board[7].stonesCount; board[indexToAdd].stonesCount += total; board[5].stonesCount = 0; board[7].stonesCount = 0; break;
		case 8 : total = board[4].stonesCount + board[8].stonesCount; board[indexToAdd].stonesCount += total; board[4].stonesCount = 0; board[8].stonesCount = 0; break;
		case 9 : total = board[3].stonesCount + board[9].stonesCount; board[indexToAdd].stonesCount += total; board[3].stonesCount = 0; board[9].stonesCount = 0; break;
		case 10 : total = board[2].stonesCount + board[10].stonesCount; board[indexToAdd].stonesCount += total; board[2].stonesCount = 0; board[10].stonesCount = 0; break;
		case 11 : total = board[1].stonesCount + board[11].stonesCount; board[indexToAdd].stonesCount += total; board[1].stonesCount = 0; board[11].stonesCount = 0; break;
		case 12 : total = board[0].stonesCount + board[12].stonesCount; board[indexToAdd].stonesCount += total; board[0].stonesCount = 0; board[12].stonesCount = 0; break;
		}
	}


	public boolean hasMoves(final Players player) {
		final Set<Integer> set = player == Players.PLAYER1 ? PLAYER1_HOUSES : PLAYER2_HOUSES;
		int total = 0;
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			total += board[it.next()].stonesCount;
		}
		return total != 0;
	}


	private int skipOtherKalah(final Players player, final int boardIndex) {
		if (player == Players.PLAYER1) {
			if (boardIndex % 14 == 13)
				return 1;
		} else {
			if (boardIndex % 14 == 6)
				return 1;
		}
		return 0;
	}

	public String toString() {
		final String tab = "\t";
		final StringBuilder sb = new StringBuilder("\n").append(tab).append(board[12].stonesCount).append(tab).append(board[11].stonesCount).append(tab)
			.append(board[10].stonesCount).append(tab).append(board[9].stonesCount).append(tab).append(board[8].stonesCount).append(tab).append(board[7].stonesCount)
			.append(tab).append("\n")

			.append(board[13].stonesCount).append(tab).append(tab).append(tab).append(tab).append(tab).append(tab).append(tab).append(board[6].stonesCount)
			.append("\n")

			.append(tab)
			.append(board[0].stonesCount).append(tab)
			.append(board[1].stonesCount).append(tab)
			.append(board[2].stonesCount).append(tab)
			.append(board[3].stonesCount).append(tab)
			.append(board[4].stonesCount).append(tab)
			.append(board[5].stonesCount).append(tab)
			.append("\n");
		return sb.toString();
	}
	public String intro() {
		final String tab = "\t";
		final StringBuilder sb = new StringBuilder("\n").append(tab).append(13).append(tab).append(12).append(tab)
			.append(11).append(tab).append(10).append(tab).append(9).append(tab).append(8)
			.append(tab).append("\n")

			.append(14).append(tab).append(tab).append(tab).append(tab).append(tab).append(tab).append(tab).append(7)
			.append("\n")

			.append(tab)
			.append(1).append(tab)
			.append(2).append(tab)
			.append(3).append(tab)
			.append(4).append(tab)
			.append(6).append(tab)
			.append(6).append(tab)
			.append("\n");
		return sb.toString();
	}

}
