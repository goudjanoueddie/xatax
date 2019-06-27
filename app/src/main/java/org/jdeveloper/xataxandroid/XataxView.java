package org.jdeveloper.xataxandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class XataxView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Thread gameThread = null;

    //Game objects
    private PlayerShip playerShip;
    public EnemyShip enemyShip1;
    public EnemyShip enemyShip2;
    public EnemyShip enemyShip3;



    //Drawing purpose
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //Create random space dust
    public ArrayList<SpaceDust> dustList=new ArrayList<SpaceDust>();

    public XataxView(Context context,int x,int y) {
        super(context);

        //Initialize drawing objects
        ourHolder=getHolder();
        paint=new Paint();

        //Initialize  player ship
        playerShip= new PlayerShip(context,x,y);
        enemyShip1 = new EnemyShip(context,x,y);
        enemyShip2 = new EnemyShip(context,x,y);
        enemyShip3 = new EnemyShip(context,x,y);

        int numSpecs = 40;

        for(int i=0;i<numSpecs;i++){

            //Position of the dust spawn
            SpaceDust spec =new SpaceDust(x,y);
            dustList.add(spec);
        }

    }

    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();
        }
    }

    private void update(){

        //collision detection
        if(Rect.intersects(playerShip.getHitbox(),enemyShip1.getHitbox())){
            enemyShip1.setX(-100);
        }

        if(Rect.intersects(playerShip.getHitbox(),enemyShip2.getHitbox())){
            enemyShip2.setX(-100);
        }

        if(Rect.intersects(playerShip.getHitbox(),enemyShip3.getHitbox())){
            enemyShip3.setX(-100);
        }

        playerShip.update();
        enemyShip1.update(playerShip.getSpeed());
        enemyShip2.update(playerShip.getSpeed());
        enemyShip3.update(playerShip.getSpeed());

        //call the update method of SpaceDust class
        for(SpaceDust sd:dustList){
            sd.update(playerShip.getSpeed());
        }
    }

    private void draw(){

        if(ourHolder.getSurface().isValid()){
            //lock the area of memory
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255,0,0,0));

            //White specs of dust
            paint.setColor(Color.argb(255,255,255,255));

            for(SpaceDust sd: dustList){
                canvas.drawPoint(sd.getX(),sd.getY(),paint);
            }


            //Draw the player
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX(),playerShip.getY(),paint);

            canvas.drawBitmap(enemyShip1.getBitmap(),enemyShip1.getX(),enemyShip1.getY(),paint);

            canvas.drawBitmap(enemyShip2.getBitmap(),enemyShip2.getX(),enemyShip2.getY(),paint);

            canvas.drawBitmap(enemyShip3.getBitmap(),enemyShip3.getX(),enemyShip3.getY(),paint);

            //unlock an drawthe scene
            ourHolder.unlockCanvasAndPost(canvas);
        }


        //canvas.drawBitmap(playerShip.getBitmap(),playerShip.getX(),playerShip.getY(),paint);



    }

    private void control(){
        try {
            gameThread.sleep(17);
        }catch (InterruptedException e){

        }


    }

    //called  when the player quit or when the game is interrupted
    public void pause(){
        playing = false;

        try{
            gameThread.join();
        }catch(InterruptedException e){


        }
    }

    //build a new thread and start it
    public void resume(){

        playing =true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){

            //Le joueur a-t-il levé le doigt?
            case MotionEvent.ACTION_UP:
                playerShip.stopBoosting();
                break;

            //Le joueur a-t-il touché l'écran?
            case MotionEvent.ACTION_DOWN:
                playerShip.setBoosting();
                break;
        }

        return true;

    }

}
