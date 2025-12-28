package org.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CometManager {

    private List<ImageView> kometat = new ArrayList<>();
    private Image cometImage;
    private Random random = new Random();

    public CometManager() {
        cometImage = new Image(getClass().getResourceAsStream("/comet.png"));
    }

    public List<ImageView> getKometat() {
        return kometat;
    }

    public void spawn(Pane root) {
        ImageView k = new ImageView(cometImage);
        k.setFitWidth(40);
        k.setFitHeight(40);
        k.setX(random.nextDouble() * 550);
        k.setY(0);

        kometat.add(k);
        root.getChildren().add(k);
    }

    public void move(Pane root) {
        Iterator<ImageView> it = kometat.iterator();
        while (it.hasNext()) {
            ImageView k = it.next();
            k.setY(k.getY() + 4);
            if (k.getY() > 400) {
                root.getChildren().remove(k);
                it.remove();
            }
        }
    }
}
