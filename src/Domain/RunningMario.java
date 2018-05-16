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
public class RunningMario extends Character {

    SynchronizedBuffer sharedLocation;

    public RunningMario(int x, int y, int imgNum, SynchronizedBuffer sharedLocation) throws FileNotFoundException {
        super(x, y, imgNum);
        setSprite();
        this.sharedLocation = sharedLocation;
    }

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 2; i < 19; i++) {
            sprite.add(new Image(new FileInputStream("src/Assets/RUN" + i + ".png")));
        }
        super.setSprite(sprite);
    }
    //metodo que hace que la imagen se mueva de derecha a izquierda y viceversa

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        super.setImage(sprite.get(0));
        int num = 0;
        int value = 0;

        while (true) {

            try {

                super.setX(400);

                super.setY(475);

                super.setImage(sprite.get(1));

                for (int i = 150; i < 350; i = i + 25) {

                    super.setImage(sprite.get(num));

                    num++;

                    super.setX(i);

                    Thread.sleep((int) (Math.random() * 100));

                    //           super.setX(700);
                    //           Thread.sleep(800);
                }

                sharedLocation.set(value);
//                super.setImage(spriteBall.get(ball));
//                ball++;
                //System.out.print(ball);
                //  value++;
                for (int j = 325; j > 150; j = j - 25) {

                    super.setImage(sprite.get(num));

                    if (num == 14) {

                        num = 0;

                    } else {

                        num++;

                    }

                    super.setX(j);

                    Thread.sleep((int) (Math.random() * 100));
                }

            } catch (InterruptedException ex) {

            }
        }//while
    }//run
}//clase
