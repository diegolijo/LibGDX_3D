package com.sistema_solar.game.Objetos;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class inController extends CameraInputController {

    public inController(Camera camera) {
        super(camera);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return super.touchDown(x, y, pointer, button);
    }
}


