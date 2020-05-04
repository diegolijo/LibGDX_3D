package com.sistema_solar.game;

import com.badlogic.gdx.Game;
import com.sistema_solar.game.Objetos.Pantalla;

public class principal_Game extends Game {

    private Pantalla pantalla;


    @Override
    public void create() {
        pantalla = new Pantalla();

        //	setScreen(pantallaCargaModelosInicial);
        //	setScreen(pantallaMoverModelos);
        setScreen(pantalla);

    }

    @Override
    public void dispose() {
        super.dispose();
        pantalla.dispose();
    }
}