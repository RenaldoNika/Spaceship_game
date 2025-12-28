package org.example.game1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage menuStage) {

        Button luajButton = new Button("Luaj");

        luajButton.setOnAction(e -> {
            try {
                Stage gameStage = new Stage();
                Loja loja = new Loja();
                loja.start(gameStage);

                menuStage.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        StackPane root = new StackPane(luajButton);
        Scene scene = new Scene(root, 300, 200);

        menuStage.setTitle("Menu");
        menuStage.setScene(scene);
        menuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
