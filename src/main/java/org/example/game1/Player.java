package org.example.game1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    private ImageView anija;
    private List<ImageView> raketat = new ArrayList<>();
    private Image rocketImage;

    public Player(Pane root) {
        Image shipImage = new Image(getClass().getResourceAsStream("/ship.png"));
        rocketImage = new Image(getClass().getResourceAsStream("/rocket.png"));

        anija = new ImageView(shipImage);
        anija.setFitWidth(50);
        anija.setFitHeight(50);
        anija.setX(275);
        anija.setY(330);

        root.getChildren().add(anija);
    }

    public ImageView getAnija() {
        return anija;
    }

    public List<ImageView> getRaketat() {
        return raketat;
    }

    public boolean collidesWith(ImageView kometa) {
        return anija.getBoundsInParent().intersects(kometa.getBoundsInParent());
    }


    public void moveLeft() {
        anija.setX(Math.max(0, anija.getX() - 9));
    }

    public void moveRight() {
        anija.setX(Math.min(550, anija.getX() + 9));
    }

    public void shoot(Pane root) {
        ImageView r = new ImageView(rocketImage);
        r.setFitWidth(10);
        r.setFitHeight(25);
        r.setX(anija.getX() + anija.getFitWidth() / 2 - 5);
        r.setY(anija.getY() - 20);

        raketat.add(r);
        root.getChildren().add(r);
    }

    public void moveRockets(Pane root) {
        Iterator<ImageView> it = raketat.iterator();
        while (it.hasNext()) {
            ImageView r = it.next();
            r.setY(r.getY() - 8);
            if (r.getY() < 0) {
                root.getChildren().remove(r);
                it.remove();
            }
        }
    }
}
