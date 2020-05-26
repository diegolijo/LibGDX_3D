package com.sistema_solar.game;

import com.badlogic.gdx.Game;
import com.sistema_solar.game.Objetos.Pantalla;

public class game extends Game {

    private Pantalla pantalla;


    @Override
    public void create() {
        pantalla = new Pantalla();


        setScreen(pantalla);

    }

    @Override
    public void dispose() {
        super.dispose();
        pantalla.dispose();
    }
}