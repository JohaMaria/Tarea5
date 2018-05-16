/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author monge55
 */
public class RunningL extends Character {

    SynchronizedBuffer sharedLocation;

    public RunningL(int x, int y, int imgNum, SynchronizedBuffer sharedLocation) throws FileNotFoundException {
        super(x, y, imgNum);
        setSprite();
        this.sharedLocation = sharedLocation;
    }

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 2; i < 19; i++) {
            sprite.add(new Image(new FileInputStream("src/Assets/RUNL" + i + ".png")));
        }
        super.setSprite(sprite);
    }

    //metodo que hace que la imagen se mueva de derecha a izquierda y viceversa
    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        super.setImage(sprite.get(0));
        int num = 8;
        int sum = 0;
        while (true) {

            try {
                super.setX(400);
                super.setY(475);
                super.setImage(sprite.get(8));
                for (int i = 600; i > 400; i = i - 25) {

                    super.setImage(sprite.get(num));

                    num++;

                    super.setX(i);
                    Thread.sleep((int) (Math.random() * 200));
                    //           super.setX(700);
                    //           Thread.sleep(800);
                }
                // super.setImage(sprite.get(9));

                sum += sharedLocation.get();

                //  System.out.print("NP");
                num = 0;
                for (int j = 400; j < 600; j = j + 25) {

                    super.setImage(sprite.get(num));
                    if (num == 8) {
                        num = 8;
                    } else {
                        num++;
                    }
                    super.setX(j);
                    Thread.sleep((int) (Math.random() * 200));
                }
            } catch (InterruptedException ex) {
            }
        }//while
    }//run
}//clase
