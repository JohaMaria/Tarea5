/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Domain.RunningL;
import Domain.RunningMario;
import Domain.SynchronizedBuffer;
import Utility.Variables;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.BufferOverflowException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author monge55
 */
public class Window extends Application implements Runnable {

    private Thread thread;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Image image, imagen2;

    private RunningMario rc;
    private RunningL rL;
    private SynchronizedBuffer sharedLocation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Graphics and Threads");
        initComponents(primaryStage);
        primaryStage.setOnCloseRequest(exit);
        primaryStage.show();
    }

    @Override
    public void run() {

        long start;
        long elapsed;
        long wait;
        int fps = 30;
        long time = 1000 / fps;

        while (true) {
            try {
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                Thread.sleep(wait);
                GraphicsContext gc = this.canvas.getGraphicsContext2D();
                draw(gc);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void initComponents(Stage primaryStage) {
        try {
            this.pane = new Pane();
            this.scene = new Scene(this.pane, Variables.WIDTH, Variables.HEIGHT);
            this.canvas = new Canvas(Variables.WIDTH, Variables.HEIGHT);
            this.image = new Image(new FileInputStream("src/Assets/fondo1.jpg"));

            this.imagen2 = new Image(new FileInputStream("src/Assets/estrella.png"));
            this.sharedLocation = new SynchronizedBuffer();
            this.pane.getChildren().add(this.canvas);

            primaryStage.setScene(this.scene);

            this.rc = new RunningMario(-50, 500, 0, sharedLocation);
            this.rc.start();
            this.rL = new RunningL(-45, 500, 0, sharedLocation);
            this.rL.start();

            this.thread = new Thread(this);
            this.thread.start();
        } catch (FileNotFoundException ex) {
        } catch (BufferOverflowException ex) {
        }
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, Variables.WIDTH, Variables.HEIGHT);
        gc.drawImage(this.image, 0, 0);
        gc.drawImage(this.rc.getImage(), this.rc.getX(), this.rc.getY());
        gc.drawImage(this.rL.getImage(), this.rL.getX(), this.rL.getY());
        if (sharedLocation.estrellita() == 1) {
            gc.drawImage(this.imagen2, 375, 475);
        }
    }

    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };
}
