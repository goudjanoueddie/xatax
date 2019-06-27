package org.jdeveloper.xataxandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerShip {

    private Bitmap bitmap;
    private int x,y;
    private int speed = 0;
    private boolean boosting;

    private final int GRAVITY = -12;

    //keep the ship inside the screen
    private int maxY;
    private int minY;

    //interval of speed
    private final int MIN_SPEED=1;
    private final int MAX_SPEED=20;

    //detecting collision
    private Rect hitbox;



    public PlayerShip(Context context,int screenX,int screenY){
        x = 50;
        y = 50;
        speed =1;
        boosting = false;
        //bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.mirage);
        //bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.playership);
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.avion);

        maxY =screenY - bitmap.getHeight();
        minY=0;

        //Initialize the hit box
        hitbox= new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());

    }

    public void update(){

        x++;

        //Are we boosting
        if(boosting){

            //speed up
            speed +=2;

        }else{
            //ralentir
            speed -=5;
        }

        if(speed >MAX_SPEED){
            speed = MAX_SPEED;
        }

        if(speed < MIN_SPEED){
            speed =MIN_SPEED;
        }

        //move the ship up or down
        y -= speed + GRAVITY;

        //But don't let ship stray off screen
        if(y <minY){
            y=minY;
        }

        if(y >maxY){
            y = maxY;
        }

        //Refresh hit box location
        hitbox.left = x;
        hitbox.top =y;
        hitbox.right=x + bitmap.getWidth();
        hitbox.bottom=y+bitmap.getHeight();


    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed(){
        return speed;
    }

    public void setBoosting(){
        boosting = true;
    }

    public void stopBoosting(){
        boosting = false;
    }

    public Rect getHitbox() {
        return hitbox;
    }






}
