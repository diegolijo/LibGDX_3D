package com.sistema_solar.game.Objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

public class Pantalla implements Screen {

    private PerspectiveCamera camara3d;
    private ModelBatch modelBatch;
    private Environment environment;
    private Environment environmentEscenario;
    private inController camController;
    private ModelInstance[] instance = new ModelInstance[10];
    private ModelInstance instanceEspacio;
    private AssetManager assets;
    private boolean loading;
    private float posicion = 1;
    private float radioGiro[] = new float[10];
    private float velocidad = 5;
    private float escala = 5;
    //movimiento de camara
    private float z = 5;
    private float y = 20;
    private float x = 50;


    public Pantalla() {


        // eje 23,4f

        // 366,26

        camara3d = new PerspectiveCamera();
        camController = new inController(camara3d);
        Gdx.input.setInputProcessor(camController);

        modelBatch = new ModelBatch();


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, 1f, 1f, 1f));


        environmentEscenario = new Environment();
        environmentEscenario.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
  //     environmentEscenario.add(new DirectionalLight().set(1f, 1f, 1f, 0f, 0f, 1f));


        //Carga de modelos mediante AssetManager
        assets = new AssetManager();
        assets.load("sol.obj", Model.class);
        assets.load("satelite_tierra.obj", Model.class);
        assets.load("escenario_espacio.obj", Model.class);
        assets.load("huevo.obj", Model.class);
        assets.load("luna.obj", Model.class);
        assets.finishLoading();

        instanceEspacio = new ModelInstance(assets.get("escenario_espacio.obj", Model.class));
        instance[1] = new ModelInstance(assets.get("sol.obj", Model.class));
        instance[2] = new ModelInstance(assets.get("satelite_tierra.obj", Model.class));
        instance[3] = new ModelInstance(assets.get("huevo.obj", Model.class));
        instance[4] = new ModelInstance(assets.get("luna.obj", Model.class));

        instanceEspacio.transform.setTranslation(0, 0, 0);

        instance[1].transform.setToScaling(0.205f*escala, 0.20f*escala, 0.20f*escala);
        instance[2].transform.setToScaling(0.15f*escala, 0.15f*escala, 0.15f*escala);
        instance[3].transform.setToScaling(0.10f*escala, 0.10f*escala, 0.10f*escala);
        instance[4].transform.setToScaling(0.01f*escala, 0.01f*escala, 0.01f*escala);
        instance[3].transform.setTranslation(10, 0, 0);



//        instanceEspacio.transform.setToScaling(5f, 5f, 5f);
//        instanceEspacio.transform.setToRotation(0, 0, 0, 0);
//
//
//        instance[1].transform.setToScaling(1f, 1f, 1f);
//        instance[1].transform.setToRotation(0, 0, 0, 0);
//
//
//
//        instance[2].transform.setToScaling(2f, 2f, 2f);
//        instance[2].transform.setToRotation(0, 0, 0, 0);
//        instance[2].transform.setTranslation(2, 0, 6);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());





        instanceEspacio.transform.rotate(0, 1, 0, 0.005f);

        instance[1].transform.setTranslation(0, 0, 0);

        radioGiro[1] = 14.95f;
        radioGiro[3] = 10.2f;
        radioGiro[4] = 0.2f *escala;
        posicion += delta / velocidad;
        float añoTierra = 365.26f;


        instance[2].transform.rotate(0, 1, 0, 20);


        float xTierra = (float) Math.sin(posicion) * radioGiro[1];
        float zTierra = (float) Math.cos(posicion) * radioGiro[1] * 0.90f;






        instance[2].transform.setTranslation(xTierra, 0, zTierra);


        //girar y posicionar
        float xGuevo = (float) Math.sin(posicion *1.6f) * radioGiro[3];
        float zGuevo = (float) Math.cos(posicion *1.6f) * radioGiro[3] * 0.90f;
        instance[3].transform.rotate(0, 1, 0, posicion/225);
        instance[3].transform.setTranslation(xGuevo, 0, zGuevo);


        //luna
        instance[4].transform.rotate(0, 1, 0, 4.28f);
        instance[4].transform.setTranslation(xTierra + ((float) Math.sin(posicion * 12.85f) * radioGiro[4]), 0, zTierra + ((float) Math.cos(posicion * 12.85f) * radioGiro[4]));





        modelBatch.begin(camara3d);
        modelBatch.render(instanceEspacio, environmentEscenario);
        modelBatch.render(instance[1], environment);
        modelBatch.render(instance[2], environment);
        modelBatch.render(instance[3], environment);
        modelBatch.render(instance[4], environment);
        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {

        camara3d.fieldOfView = 70;
        camara3d.viewportHeight = height;
        camara3d.viewportWidth = width;


        camara3d.position.set(0, 10, 25);
        camara3d.lookAt(0, 0, 0);
        camara3d.near = 1;
        camara3d.far = 200f;
        camara3d.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


        modelBatch.dispose();

        assets.dispose();
    }
}
