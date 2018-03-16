
package javafxapplication1;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertBox {
    static Scene scene;
    static Stage window = new Stage();
    public static void display(String title, String message) {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label label = new Label(message);
        
        VBox vBox = new VBox();
        scene = new Scene(vBox,200,50);
        vBox.getChildren().addAll(label);
        window.setScene(scene);
        window.show(); 
    }

    public static void displayChart(String title, BarChart<String,Number> bc) {
        window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        
        scene = new Scene(bc,800,600);
        window.setScene(scene);
        window.show(); 
    }
}
