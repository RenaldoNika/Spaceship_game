package org.example.game1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Loja extends Application {

    private boolean gameOver = false;
    private int jete = 100;
    private int score = 0;

    private Text jeteText;
    private Text scoreText;
    private Text gameOverText;

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 400);

        // 🔹 Background
        Image bgImage = new Image(getClass().getResourceAsStream("/background.png"));
        ImageView background = new ImageView(bgImage);
        background.setFitWidth(600);
        background.setFitHeight(400);
        root.getChildren().add(background);

        // 🔹 Objekte lojë
        Player player = new Player(root);
        CometManager comets = new CometManager();
        SoundManager sounds = new SoundManager();

        // 🔹 Tekste
        jeteText = new Text("Jete: " + jete);
        jeteText.setFont(Font.font(20));
        jeteText.setFill(Color.WHITE);
        jeteText.setX(10);
        jeteText.setY(30);

        scoreText = new Text("Score: " + score);
        scoreText.setFont(Font.font(20));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(480);
        scoreText.setY(30);

        gameOverText = new Text("GAME OVER!");
        gameOverText.setFont(Font.font(50));
        gameOverText.setFill(Color.RED);
        gameOverText.setX(150);
        gameOverText.setY(200);
        gameOverText.setVisible(false);

        root.getChildren().addAll(jeteText, scoreText, gameOverText);

        // 🔹 Start engine sound
        sounds.playEngine();

        // 🔹 Kontrollet e tastierës
        scene.setOnKeyPressed(e -> {
            if (!gameOver) {
                if (e.getCode() == KeyCode.LEFT) player.moveLeft();
                if (e.getCode() == KeyCode.RIGHT) player.moveRight();
                if (e.getCode() == KeyCode.SPACE) player.shoot(root);
            }
        });

        // 🔹 Game loop
        new AnimationTimer() {
            long lastSpawn = 0;

            @Override
            public void handle(long now) {

                if (gameOver) return;

                // Spawn kometa çdo 0.5 sekonda
                if (now - lastSpawn > 500_000_000) {
                    comets.spawn(root);
                    lastSpawn = now;
                }

                // Lëviz raketat dhe kometat
                player.moveRockets(root);
                comets.move(root);

                // collision raketë - kometë
                player.getRaketat().removeIf(r ->
                        comets.getKometat().removeIf(k -> {
                            if (r.getBoundsInParent().intersects(k.getBoundsInParent())) {
                                root.getChildren().removeAll(r, k);
                                score++;
                                scoreText.setText("Score: " + score);
                                sounds.playExplosion();
                                return true;
                            }
                            return false;
                        })
                );

                // collision anije - kometë
                comets.getKometat().removeIf(k -> {
                    if (player.getAnija().getBoundsInParent().intersects(k.getBoundsInParent())) {
                        root.getChildren().remove(k);

                        jete -= 20;
                        jeteText.setText("Jete: " + Math.max(jete, 0));

                        sounds.playExplosion();

                        if (jete <= 0) {
                            gameOver = true;
                            gameOverText.setVisible(true);
                            sounds.stopEngine();
                        }
                        return true;
                    }
                    return false;
                });
            }
        }.start();

        stage.setScene(scene);
        stage.setTitle("Loja");
        stage.show();
    }

//    public static void main(String[] args) {
//        launch();
//    }
}
