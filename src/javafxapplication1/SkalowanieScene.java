
package javafxapplication1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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


public class SkalowanieScene extends Scene {
    VBox vBox;
    int x=-1,y=-1;
    int rgb;
    BufferedImage img = null;
    ImageView iV = new ImageView();
    Label pixelColor = new Label("pointed pixel color");
    TextField red = new TextField("red");
    TextField green = new TextField("green");
    TextField blue = new TextField("blue");
    ChoiceBox<String> savingFormat = new ChoiceBox<String>();
    Button openButton = new Button("open image");
    Button backToMain = new Button("back to main window");
    Button saveButton = new Button("save image");
    Button scale1X = new Button("scale 0.5");
    Button scale2X = new Button("scale 2 x");
    Button setLastPixel = new Button("set last pointed pixel to selected values");
    
    public SkalowanieScene(VBox vBox,int w, int h) {
        super(vBox, w, h);
        this.vBox = vBox;
        MainWindow.mainStage.setTitle("Zadanie 1");
        
        vBox.getChildren().addAll(red,green,blue,setLastPixel,scale1X,scale2X,pixelColor,openButton,
                        savingFormat,saveButton,backToMain,iV);            
                savingFormat.getItems().addAll("jpg", "png", "bmp", "gif");
                savingFormat.setValue("jpg");
        setListeners();
                
    }
    private void reloadImage() {
            Image image = SwingFXUtils.toFXImage(img, null);
            iV.setImage(image);
        }
    private void saveFile() {
           try{
               File outputfile = new File("savedimage."+savingFormat.getValue());
               ImageIO.write(img, savingFormat.getValue(), outputfile);
           }
           catch(IOException ex) {

           }
        }
        
    private void setListeners() {
        backToMain.setOnAction(e -> MainWindow.returnToMainWindow());
        iV.setOnMouseClicked(e -> setxy(e)); 
        scale1X.setOnAction(e -> scale(0.5));
        scale2X.setOnAction(e -> scale(2));
        openButton.setOnAction(e -> openFile(e));
        saveButton.setOnAction(e -> saveFile());

        setLastPixel.setOnAction(e -> setRGB());
    }

    private void openFile(ActionEvent e) {
        final JFileChooser fc = new JFileChooser("C:\\Users\\blysband\\Downloads\\biometria2\\JavaFXApplication1");
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            try {
                img = ImageIO.read(new File(file.getName()));
                System.out.println("FlileLoaded");
                reloadImage();
            } catch (IOException ex) {
                System.out.println("FlileNotLoaded");
            }
        } else {
                System.out.println("FlileNotLoaded");
        }
    }

    private void scale(double scale) {
        int w2 = (int) (img.getWidth() * scale);
        int h2 = (int) (img.getHeight() * scale);
        BufferedImage after = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
        AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp scaleOp  = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_BILINEAR);         
        Graphics2D g2 = (Graphics2D) after.getGraphics();
        g2.drawImage(img, scaleOp, 0, 0);
        g2.dispose();
        img = after;            
        reloadImage();
    }
    
    private void setxy (MouseEvent e) {        
        this.x = (int)e.getX(); 
        this.y = (int)e.getY();
        showRGB();
    }
    
    private void showRGB() {
        Color c = new Color(this.img.getRGB(this.x, this.y));
//        this.rgb=this.img.getRGB(this.x, this.y);
        this.pixelColor.setText(String.valueOf(c.getRed())+" "+String.valueOf(c.getGreen())+" "+String.valueOf(c.getBlue()));
    }
    
    private void setRGB() {
        int r,g,b;
        try {
            r = Integer.parseInt(red.getText());
            g = Integer.parseInt(green.getText());
            b = Integer.parseInt(blue.getText());
        }
        catch(Exception e) {
            AlertBox.display("error", "Podaj prawidlowe wartosci");
            return;
        }
        Color c = new Color(r,g,b);
        img.setRGB(this.x,this.y,c.getRGB());
        reloadImage();
    }

    
}
