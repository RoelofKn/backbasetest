package org.knibbe.games.kalah.implementation;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import org.knibbe.games.kalah.Kalah;

/**
 * Created by roelof on 5/16/17.
 */
public class KalahImpl implements Kalah {

	private int nrOfStones = 6;
	private Players currentPlayer = Players.PLAYER1;
	private Board board;

	@Override
	public void play() throws IOException {
		board = new Board(nrOfStones);
		System.out.println("\nPlay Kalah with 2 players. Below is the board with positions:");
		System.out.println(board.intro());
		System.out.println("PLAYER1 may enter positions 1-6");
		System.out.println("PLAYER2 may enter positions 8-13");
		System.out.println("Good luck!\n");
		final Scanner in = new Scanner(System.in);
		while (hasMoves()) {
			System.out.print(String.format("%s please choose a house: ", currentPlayer));
			final int choice = in.nextInt();
			if (nextTurn(choice)) nextPlayer();
			show();
		}
	}

	private boolean hasMoves() {
		return board.hasMoves(currentPlayer);
	}

	private boolean nextTurn(final int choice) {
		return board.turn(currentPlayer, choice - 1);
	}

	private void nextPlayer() {
		if (currentPlayer == Players.PLAYER1) {
			currentPlayer = Players.PLAYER2;
		} else {
			currentPlayer = Players.PLAYER1;
		}
	}

	private void show() {
		System.out.println("*************************************************************************");
		System.out.println(board.toString());
		System.out.println("*************************************************************************");
	}
//
//	@Override
//	public void setNumberOfStones(final int number) {
//		nrOfStones = number;
//	}
}
