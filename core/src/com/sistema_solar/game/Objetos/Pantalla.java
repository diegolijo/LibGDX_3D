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
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;


public class Pantalla implements Screen {

    private PerspectiveCamera camara3d;
    private ModelBatch modelBatch;
    private Environment environment;
    private Environment environmentEscenario;
    private inController camController;
    private ModelInstance[] model = new ModelInstance[10];
    private ModelInstance modelEspacio;
    private AssetManager assets;
    private float vel = 0;
    private float radioGiro[] = new float[10];
    private float velocidad = 6;        
    private float escala = 2;
    //movimiento de camara
    private float z = 5;
    private float y = 20;
    private float x = 50;

    btDiscreteDynamicsWorld dynamicsWorld;


    public Pantalla() {


        // eje 23,4f
        // 366,26


        //---------------------------------------------------------------
        camara3d = new PerspectiveCamera();
        camController = new inController(camara3d);
        Gdx.input.setInputProcessor(camController);

        modelBatch = new ModelBatch();


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, 1f, 1f, 1f));


        environmentEscenario = new Environment();
        environmentEscenario.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
        // environmentEscenario.add(new DirectionalLight().set(1f, 1f, 1f, 0f, 0f, 1f));

        //Carga de modelos
        assets = new AssetManager();
        assets.load("sol.obj", Model.class);
        assets.load("satelite_tierra.obj", Model.class);
        assets.load("escenario_espacio.obj", Model.class);
        assets.load("marte.obj", Model.class);
        assets.load("luna.obj", Model.class);
        assets.load("dibujo_tierra.obj", Model.class);
        assets.load("mercurio.obj", Model.class);
        assets.finishLoading();

        modelEspacio = new ModelInstance(assets.get("escenario_espacio.obj", Model.class));
        model[1] = new ModelInstance(assets.get("sol.obj", Model.class));
        model[2] = new ModelInstance(assets.get("satelite_tierra.obj", Model.class));
        model[3] = new ModelInstance(assets.get("marte.obj", Model.class));
        model[4] = new ModelInstance(assets.get("luna.obj", Model.class));
        model[5] = new ModelInstance(assets.get("dibujo_tierra.obj", Model.class));
        model[6] = new ModelInstance(assets.get("mercurio.obj", Model.class));


        modelEspacio.transform.setTranslation(0, 0, 0);

        model[1].transform.setToScaling(0.20f * escala, 0.20f * escala, 0.20f * escala);
        model[2].transform.setToScaling(0.15f * escala, 0.15f * escala, 0.15f * escala);
        model[3].transform.setToScaling(0.1f * escala, 0.1f * escala, 0.1f * escala);
        model[4].transform.setToScaling(0.01f * escala, 0.01f * escala, 0.01f * escala);
        model[5].transform.setToScaling(0.13f * escala, 0.13f * escala, 0.13f * escala);
        model[6].transform.setToScaling(0.2f * escala, 0.2f * escala, 0.2f * escala);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        modelEspacio.transform.rotate(0, 1, 0, 0.005f);

        model[1].transform.setTranslation(0, 0, 0);

        radioGiro[1] = 16f;
        radioGiro[3] = 13f;
        radioGiro[5] = 9f;
        radioGiro[6] = 7f;

        radioGiro[4] = 0.2f * escala;
        vel += delta / velocidad;
        float añoTierra = 365.26f;


        model[2].transform.rotate(0, 1, 0, 20);


        float xTierra = (float) Math.sin(vel) * radioGiro[1];
        float zTierra = (float) Math.cos(vel) * radioGiro[1] * 0.90f;
        model[2].transform.setTranslation(xTierra, 0, zTierra);


        //girar y posicionar marte
        float xMarte = (float) Math.sin(vel * 1.6f) * radioGiro[3];
        float zMarte = (float) Math.cos(vel * 1.6f) * radioGiro[3] * 0.90f;
        model[3].transform.rotate(0, 1, 0, vel);
        model[3].transform.setTranslation(xMarte, 0, zMarte);

        //luna
        model[4].transform.rotate(0, 1, 0, 4.28f);
        model[4].transform.setTranslation(xTierra + ((float) Math.sin(vel * 12.85f) * radioGiro[4]), 0, zTierra + ((float) Math.cos(vel * 12.85f) * radioGiro[4]));


        //girar y posicionar marte


        float xVenus = (float) Math.sin(vel * 1.3f) * radioGiro[5];
        float zVenus = (float) Math.cos(vel * 1.3f) * radioGiro[5] * 0.90f;
        model[5].transform.rotate(0, 1, 0, vel);
        model[5].transform.setTranslation(xVenus, 0, zVenus);

        //girar y posicionar marte
        float xMercurio = (float) Math.sin(vel * 2.5f) * radioGiro[6];
        float zMercurio = (float) Math.cos(vel * 2.5f) * radioGiro[6] * 0.90f;
        model[6].transform.rotate(0, 1, 0, vel / 225);
        model[6].transform.setTranslation(xMercurio, 0, zMercurio);


        modelBatch.begin(camara3d);
        modelBatch.render(modelEspacio, environmentEscenario);
        modelBatch.render(model[1], environment);
        modelBatch.render(model[2], environment);
        modelBatch.render(model[3], environment);
        modelBatch.render(model[4], environment);
        modelBatch.render(model[5], environment);
        modelBatch.render(model[6], environment);
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
