package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {

    private static final int KEYBOARD_MOVEMENT_DELTA = 20;
    private static Scene scene;
    public static MediaPlayer player;
    public static Media media;
    boolean notKeyPressed = false;

    //Thread threadGame;
    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(menuScreen(), 500, 500);
        playSound();

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(500);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Pane menuScreen() {

        Pane vBox = new Pane(); // separation between buttons
        Button play = new Button("Play Game");
        play.setFont(Font.font("Impact", FontWeight.BOLD, 31.5));
        Button instructions = new Button("Instructions");
        instructions.setFont(Font.font("Impact", FontWeight.BOLD, 31.5));
        Button difficulty = new Button("Difficulty");
        difficulty.setFont(Font.font("Impact", FontWeight.BOLD, 31.5));
        Button exit = new Button("Exit");
        exit.setFont(Font.font("Impact", FontWeight.BOLD, 31.5));

        play.setLayoutX(160);
        play.setLayoutY(50);
        play.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                player.stop();
                try {
                    Media media1 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player1 = new MediaPlayer(media1);
                    player1.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                play.getScene().setRoot(game());
            }
        });

        instructions.setLayoutX(150);
        instructions.setLayoutY(125);
        instructions.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0
            ) {
                try {
                    Media media2 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player2 = new MediaPlayer(media2);
                    player2.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                instructions.getScene().setRoot(rules());
            }
        });

        difficulty.setLayoutX(165);
        difficulty.setLayoutY(200);
        difficulty.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    Media media3 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player3 = new MediaPlayer(media3);
                    player3.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                difficulty.getScene().setRoot(difficulty());
            }
        });

        exit.setLayoutX(200);
        exit.setLayoutY(275);
        Media media4 = new Media(new File("musicFile\\EndGame.wav").toURI().toString());
        MediaPlayer player4 = new MediaPlayer(media4);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    player4.play();
                    player.stop();
                    player4.setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    });
                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
            }
        });

        vBox.getChildren().addAll(background(), play, instructions, difficulty, exit);
        return vBox;
    }

    public static void playSound() {
        try {
            media = new Media(new File("musicFile\\MainMenu.wav").toURI().toString());
            player = new MediaPlayer(media);
            player.setOnEndOfMedia(new Runnable() {
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
            player.play();

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public Pane rules() {
        StackPane pane = new StackPane();
        final Button goBack = new Button("Main Menu");

        Label text = new Label("Instructions:\n");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextFill(Color.BLUE);
        text.setFont(new Font("Cambria", 50));

        goBack.setLayoutX(225);
        goBack.setLayoutY(400);
        goBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    Media media1 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player1 = new MediaPlayer(media1);
                    player1.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                goBack.getScene().setRoot(menuScreen());
            }
        });
        pane.setAlignment(text, Pos.TOP_CENTER);
        pane.setAlignment(goBack, Pos.BOTTOM_CENTER);
        pane.getChildren().addAll(background(), text, goBack);
        return pane;
    }

    public Pane game() {
        Pane pane = new Pane();

        Group girl = character();
        moveShapeOnKeyPress(girl);

        Image image = new Image("folder/background.jpg",
                500.0, 500.0, false, true, false);

        ImageView galaxyBackground = new ImageView(image);


        pane.getChildren().addAll(galaxyBackground,girl);
        return pane;
    }

    public Group character()
    {
        EventHandler<KeyEvent> keyListener;

        String[] goingFront = {"folder/eliFront.png", "folder/Picture1.png", "folder/Picture2.png"};
        String[] goingBack  = {"folder/eliBack.png", "folder/Picture7.png", "folder/Picture8.png"};
        String[] goingLeft  = {"folder/eliLeft.png", "folder/Picture5.png", "folder/Picture6.png"};
        String[] goingRight = {"folder/eliRight.png", "folder/Picture3.png", "folder/Picture4.png"};
        // String[] walking = new String[3];
        double[] time = {2000, 1000, 2000};
        ImageView eliMoving = new ImageView(new Image(goingRight[0], 40, 50, false, true, false));
        Group girlMoving = new Group(eliMoving);
        girlMoving.setLayoutX(70);
        girlMoving.setLayoutY(350);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        for (int index = 0; index < goingLeft.length; index++) {
            final int sheIndex = index;
            EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    girlMoving.getChildren().setAll(new ImageView(new Image(goingRight[sheIndex], 40, 50, false, true, false)));
                    girlMoving.setOnKeyPressed(e -> {
//            if (e.getCode().equals(KeyCode.DOWN)) {
//                eliMoving.setImage(new Image (goingFront[0], 40, 50, false, true, false));
//                timeline.play();
//            }
                    });
                }

            };
            Duration duration = Duration.millis(time[index]);
            KeyFrame kf = new KeyFrame(duration, onFinished, null, null);
            timeline.getKeyFrames().add(kf);
        }

        timeline.play();

        return girlMoving;
    }


    public VBox difficulty() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        vBox.setBackground(new Background(new BackgroundImage(new Image("folder/castle.jpg", 500.0,
                500.0, false, true, false), BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        final Button Easy = new Button("Easy");
        final Button Intermediate = new Button("Intermediate");
        final Button Expert = new Button("Expert");
        final Button Master = new Button("Master");
        final Button goBack = new Button("Main Menu");

        Label text = new Label("Difficulty Settings:");
        text.setTextFill(Color.RED);
        text.setFont(new Font("Cambria", 25));

        Easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    Media media1 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player1 = new MediaPlayer(media1);
                    player1.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                Easy.getScene().setRoot(menuScreen());
            }
        });

        Intermediate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    Media media2 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player2 = new MediaPlayer(media2);
                    player2.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                Intermediate.getScene().setRoot(menuScreen());
            }
        });

        Expert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    Media media3 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player3 = new MediaPlayer(media3);
                    player3.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                Expert.getScene().setRoot(menuScreen());
            }
        });

        Master.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    Media media4 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player4 = new MediaPlayer(media4);
                    player4.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                Master.getScene().setRoot(menuScreen());
            }
        });

        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg) {
                try {
                    Media media5 = new Media(new File("musicFile\\ButtonClick.wav").toURI().toString());
                    MediaPlayer player5 = new MediaPlayer(media5);
                    player5.play();

                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                goBack.getScene().setRoot(menuScreen());
            }
        });

        vBox.getChildren().addAll(text, Easy, Intermediate, Expert, Master, goBack);
        return vBox;
    }

    public ImageView background() {
        Image image = new Image("folder/castle.jpg",
                500.0, 500.0, false, true, false);

        ImageView galaxyBackground = new ImageView(image);

        return galaxyBackground;
    }

    public void moveShapeOnKeyPress(Group circle) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        circle.setLayoutY(circle.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                        break;
                    case DOWN:
                        circle.setLayoutY(circle.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);
                        break;
                    case RIGHT:
                        circle.setLayoutX(circle.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                        break;
                    case LEFT:
                        circle.setLayoutX(circle.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                        break;
                }
                //checkShapeIntersection(circle);
            }
        });
    }

//    private void checkShapeIntersection(Group block)
//    {
//        boolean collisionDetected = false;
//
//        while (!collisionDetected)
//        {
//            Group intersect = block.intersects(localBounds);
//
//            if (intersect.getBoundsInLocal().getWidth() != -1)
//            {
//              collisionDetected = true;
//            }
//          }
//        }
//
//        if(collisionDetected)
//        {
//
//        }
//
//    }

    //public void animation()
//    class Timer implements Runnable {
//
//        Button exit;
//
//        public Timer(Button exit) {
//            this.exit = exit;
//        }
//
//        @Override
//        public void run() {
//            try
//            {
//                    Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                return;
//            }
//        }
//
//    }
}
