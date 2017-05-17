package org.knibbe.games.kalah;

import java.io.IOException;

import org.knibbe.games.kalah.implementation.KalahImpl;

public class Main {

    public static void main(final String[] args) {
        final Kalah kalah = new KalahImpl();
        try {
            kalah.play();
        } catch (final IOException e) {
            System.out.println(e.getMessage());

        }
    }
}
