package javafxapplication1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class MainWindow extends Application {


        
        static Scene scene;
        VBox vBox = new VBox();
        static Stage mainStage;

        Button goToSkalowanie = new Button("Funkcjonalności z zadania 1");
        Button goToTworzenieHistogramow = new Button("Funkcjonalności z zadania 2");
            
        public static void main(String[] args) {
                launch(args);
        }
        
        
        public void start(Stage primaryStage) {
                mainStage=primaryStage;
                mainStage.setTitle("Biometria");
               
                
                setListeners();

                
                scene = new Scene(vBox,1000,700);
                
                vBox.getChildren().addAll(goToSkalowanie,goToTworzenieHistogramow);            
                mainStage.setScene(scene);
                mainStage.show();	
        }
        

        private void setListeners() {
            goToSkalowanie.setOnAction(e -> goToSkalowanie());
            goToTworzenieHistogramow.setOnAction(e -> goToTworzenieHistogramow());
        }
        
        private void goToSkalowanie() {
            SkalowanieScene  skalowanieScene = new SkalowanieScene(new VBox(),1000,700);
            mainStage.setScene(skalowanieScene);
            
        }
        private void goToTworzenieHistogramow() {
            TworzenieHistogramow  tworzenieHistogramow = new TworzenieHistogramow(new VBox(),1000,700);
            mainStage.setScene(tworzenieHistogramow);
            
        }
        public static void returnToMainWindow() {
            mainStage.setScene(scene);          
        }
}
