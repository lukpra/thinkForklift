/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import java.util.HashSet;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.Random;

class Position {
    public int posX = 0;
    public int posY = 0;
    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
}

class Package {
    int posX, posY;
    Image img;
    GraphicsContext gc;
    float tileWidth;
    float tileHeight;
    
    public Package(GraphicsContext gc, String imageSrc, float tileWidth, float tileHeight, int initX, int initY) {
        this.gc = gc;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.loadImg(imageSrc, tileWidth, tileHeight);
        this.setPosition(initX, initY);
    }

    public void render() {
        gc.drawImage(img, posX * tileWidth, posY * tileHeight);
    }

    private void loadImg(String src, float width, float height) {
        this.img = new Image(getClass().getResourceAsStream(src), width, height, false, false);
    }

    private void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public int getXPos() {
        return posX;
    }
    
    public int getYPos() {
        return posY;
    }
}

class Shelf {

    int posX = 0;
    int posY = 0;
    Image img;
    GraphicsContext gc;
    float tileWidth;
    float tileHeight;

    public Shelf(GraphicsContext gc, String imageSrc, float tileWidth, float tileHeight, int initX, int initY) {
        this.gc = gc;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.loadImg(imageSrc, tileWidth, tileHeight);
        this.setPosition(initX, initY);
    }

    public void render() {
        gc.drawImage(img, posX * tileWidth, posY * tileHeight);
    }

    private void loadImg(String src, float width, float height) {
        this.img = new Image(getClass().getResourceAsStream(src), width, height, false, false);
    }

    private void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public int getXPos() {
        return posX;
    }
    
    public int getYPos() {
        return posY;
    }
}

class ForkLiftObj {

    int posX = 0;
    int posY = 0;
    Image img;
    GraphicsContext gc;
    float tileWidth;
    float tileHeight;

    public ForkLiftObj(GraphicsContext gc, String imageSrc, float tileWidth, float tileHeight, int initX, int initY) {
        this.gc = gc;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.loadImg(imageSrc, tileWidth, tileHeight);
        this.setPosition(initX, initY);
    }

    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }

    public void moveUp() {
        if (posY > 0) {
            setPosition(posX, posY - 1);
        }
    }

    public void moveDown() {
        if (posY < 14) {
            setPosition(posX, posY + 1);
        }
    }

    public void moveLeft() {
        if (posX > 0) {
            setPosition(posX - 1, posY);
        }
    }

    public void moveRight() {
        if (posX < 14) {
            setPosition(posX + 1, posY);
        }
    }

    public void render() {
        gc.drawImage(img, posX * tileWidth, posY * tileHeight);
    }

    private void loadImg(String src, float width, float height) {
        this.img = new Image(getClass().getResourceAsStream(src), width, height, false, false);
    }

    private void invert() {

    }
    
    public int getXPos() {
        return posX;
    }
    
    public int getYPos() {
        return posY;
    }
}

/**
 *
 * @author Tosia
 */
public class JavaFXApplication1 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {

        int x = 15;
        int y = 15;

        int canvasWidth = 600;
        int canvasHeight = 600;

        float tileWidth = canvasWidth / x;
        float tileHeight = canvasHeight / y;
        
        int shelfAmount = 20;
        int packageAmount = 15;

        double screenWidthResolution = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeightResolution = Screen.getPrimary().getVisualBounds().getHeight();

        theStage.setTitle("Replace this text");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        HashSet<String> currentlyActiveKeys = new HashSet<String>();

        StackPane holder = new StackPane();
        holder.setPrefSize(screenWidthResolution, screenHeightResolution);

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);

        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        holder.setStyle("-fx-background-color: black");

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image tileImg = new Image(getClass().getResourceAsStream("podloga2.png"), tileWidth, tileHeight, false, false);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gc.drawImage(tileImg, i * tileWidth, j * tileHeight);
            }
        }

        ForkLiftObj forkLift = new ForkLiftObj(gc, "forklift.png", tileWidth, tileHeight, 0, 0);
        
        ArrayList<Shelf> shelfList = new ArrayList();
        
        for(int k=0; k<15; k=k+3) {
            for(int j=5; j<15; j++) {
                shelfList.add(new Shelf(gc, "polkidwie.png", tileWidth, tileHeight, j, k));
            }
        }
        
        ArrayList<Package> packageList = new ArrayList();
        
        for(int k=0; k<packageAmount; k++) {
            int posX = 0;
            int posY = 0;
            
            Boolean test;
            
            do {
                test = false;
                Random r = new Random();
                int low = 0;
                int high = 15;
                posX = r.nextInt(15);
                posY = r.nextInt(15);
                
                for(int i=0; i < shelfList.size(); i++) {
                    if((shelfList.get(i).getXPos() == posX) && (shelfList.get(i).getYPos() == posY)){
                        test = true;
                        break;
                    }
                }
            } while (test);
           
            packageList.add(new Package(gc, "paczka12.png", tileWidth, tileHeight, posX, posY));
        }
        
        for(int k=0; k<packageAmount; k++) {
            int posX = 0;
            int posY = 0;
            
            Boolean test;
            
            do {
                test = false;
                Random r = new Random();
                int low = 0;
                int high = 15;
                posX = r.nextInt(15);
                posY = r.nextInt(15);
                
                for(int i=0; i < shelfList.size(); i++) {
                    if((shelfList.get(i).getXPos() == posX) && (shelfList.get(i).getYPos() == posY)){
                        test = true;
                        break;
                    }
                }
            } while (test);
           
            packageList.add(new Package(gc, "paczka22.png", tileWidth, tileHeight, posX, posY));
        }
        
        for(int k=0; k<packageAmount; k++) {
            int posX = 0;
            int posY = 0;
            
            Boolean test;
            
            do {
                test = false;
                Random r = new Random();
                int low = 0;
                int high = 15;
                posX = r.nextInt(15);
                posY = r.nextInt(15);
                
                for(int i=0; i < shelfList.size(); i++) {
                    if((shelfList.get(i).getXPos() == posX) && (shelfList.get(i).getYPos() == posY)){
                        test = true;
                        break;
                    }
                }
            } while (test);
           
            packageList.add(new Package(gc, "paczka32.png", tileWidth, tileHeight, posX, posY));
        }
       
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });

        theStage.setWidth(screenWidthResolution);
        theStage.setHeight(screenHeightResolution);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, screenWidthResolution, screenHeightResolution);
                
                int nextXpos = 0;
                int nextYpos = 0;

                if (currentlyActiveKeys.contains("LEFT")) {
                    nextXpos = -1;
                    //forkLift.moveLeft()
                } else if (currentlyActiveKeys.contains("RIGHT")) {
                    nextXpos = 1;
                    //forkLift.moveRight();
                } else if (currentlyActiveKeys.contains("UP")) {
                    nextYpos = -1;
                    //forkLift.moveUp();
                } else if (currentlyActiveKeys.contains("DOWN")) {
                    nextYpos = 1;
                    //forkLift.moveDown();
                }
                
                int nextXPos = forkLift.getXPos() + nextXpos;
                int nextYPos = forkLift.getYPos() + nextYpos;
                
                if(!checkIfCollision(shelfList, nextXPos, nextYPos)) {
                    forkLift.setPosition(nextXPos, nextYPos);
                } 
               
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        gc.drawImage(tileImg, i * tileWidth, j * tileHeight);
                    }
                }
                
                shelfList.forEach((shelf -> shelf.render()));
                
                packageList.forEach((packageOb -> packageOb.render()));

                forkLift.render();
                
                currentlyActiveKeys.clear();
            }
        }.start();

        //theStage.setFullScreen(true);
        theStage.show();
    }
    
    public static boolean checkIfCollision(ArrayList<Shelf> shelfList, int nextXPos,int nextYPos) {
       for(int i=0; i < shelfList.size(); i++) {
            if((shelfList.get(i).getXPos() == nextXPos) && (shelfList.get(i).getYPos() == nextYPos) || (nextXPos < 0) || (nextXPos > 14) || (nextYPos < 0) || (nextYPos > 14)){
                return true;
            }
       }
       return false;
    }
}
