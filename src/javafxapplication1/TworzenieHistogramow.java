
package javafxapplication1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


public class TworzenieHistogramow extends Scene {
    
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    BarChart<String,Number> bc;
    
    VBox vBox;
    int []r = new int[256];
    int []g = new int[256];
    int []b = new int[256];
    int []average = new int[256];
    int rmin=0,rmax=240,gmin=0,gmax=220,bmin=0,bmax=195;
    BufferedImage img = null;
    ImageView iV = new ImageView();
    ChoiceBox<String> savingFormat = new ChoiceBox<String>();
    Button openButton = new Button("open image");
    Button showRedHistogram = new Button("Show red histogram");
    Button showGreenHistogram = new Button("Show green histogram");
    Button showBlueHistogram = new Button("Show blue histogram");
    Button showAverageHistogram = new Button("Show average histogram");
    Button stretchHistogram = new Button("Streatch Histogram");
    Button backToMain = new Button("back to main window");
    Button saveButton = new Button("save image");
    public TworzenieHistogramow(VBox vBox,int w, int h) {
        super(vBox, w, h);
        this.vBox = vBox;
        MainWindow.mainStage.setTitle("Zadanie 1");
        
        vBox.getChildren().addAll(openButton,savingFormat,saveButton,backToMain,
                showRedHistogram,showGreenHistogram,showBlueHistogram,
                showAverageHistogram,stretchHistogram,iV);            
                savingFormat.getItems().addAll("jpg", "png", "bmp", "gif");
                savingFormat.setValue("jpg");
        setListeners();
                
    }
    private void reloadImage() {
            Image image = SwingFXUtils.toFXImage(img, null);
            iV.setImage(image);
            generateHistograms();
//            displayArrays();
            
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
        openButton.setOnAction(e -> openFile(e));
        saveButton.setOnAction(e -> saveFile());
        showRedHistogram.setOnAction(e -> showRedHistogram());
        showGreenHistogram.setOnAction(e -> showGreenHistogram());
        showBlueHistogram.setOnAction(e -> showBlueHistogram());
        showAverageHistogram.setOnAction(e -> showAverageHistogram());
        stretchHistogram.setOnAction(e -> stretchHistogram());
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
    private void displayArrays() {
        for (int i = 0; i<256;++i) {
            System.out.print(r[i]+" ");
        }
    }
    private void generateHistograms(){
        r = new int[256];
        g = new int[256];
        b = new int[256];
        average = new int[256];
        for (int i = 0; i<img.getWidth();++i) {
            for (int j = 0; j<img.getHeight();++j) {
                Color c = new Color(this.img.getRGB(i, j));
                r[c.getRed()]++;
                g[c.getGreen()]++;
                b[c.getBlue()]++;
                average[(c.getRed()+c.getGreen()+c.getBlue())/3]++;        
            }
        }
//        setMinMax();
//        System.out.println(rmin);
//        System.out.println(rmax);
    }
    private void setMinMax(){
        for(int i = 0;i<256;++i){
            if(r[i]!=0&&i<rmin){
                rmin=i;
            }
            if(g[i]!=0&&i<gmin){
                gmin=i;
            }
            if(b[i]!=0&&i<bmin){
                bmin=i;
            }
            if(r[i]!=0&&i>rmax){
                rmax=i;
            }
            if(g[i]!=0&&i>gmax){
                gmax=i;
            }
            if(b[i]!=0&&i>bmax){
                bmax=i;
            }
        }
    }
    private void stretchHistogram() {
        for (int i = 0; i<img.getWidth();++i) {
            for (int j = 0; j<img.getHeight();++j) {
                Color c = new Color(this.img.getRGB(i, j));
                if(c.getRed()<rmax && c.getRed()>rmin){
                    int tempRed=((c.getRed()-rmin)*256)/(rmax-rmin);
                    Color cNew = new Color(tempRed,c.getGreen(),c.getBlue());
                    img.setRGB(i,j,cNew.getRGB());
                }
                c = new Color(this.img.getRGB(i, j));
                if(c.getGreen()<gmax && c.getGreen()>gmin){
                    int tempGreen=((c.getGreen()-gmin)*256)/(gmax-gmin);
                    Color cNew = new Color(c.getRed(),tempGreen,c.getBlue());
                    img.setRGB(i,j,cNew.getRGB());
                }
                c = new Color(this.img.getRGB(i, j));
                if(c.getBlue()<bmax && c.getBlue()>bmin){
                    int tempBlue=((c.getBlue()-bmin)*256)/(bmax-bmin);
                    Color cNew = new Color(c.getRed(),c.getGreen(),tempBlue);
                    img.setRGB(i,j,cNew.getRGB());
                }
            }
        }
        reloadImage(); 
    }
    
    private void showRedHistogram(){
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Red histogram");
        xAxis.setLabel("red value");       
        yAxis.setLabel("count");
        bc.setBarGap(0);
        bc.setCategoryGap(0);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Red");
        for(int i = 0;i<256;++i) {
            series1.getData().add(new XYChart.Data(String.valueOf(i), r[i]));     
        }
        bc.getData().addAll(series1);
        AlertBox.displayChart("wykres", bc);
    } 
    private void showGreenHistogram(){
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Green histogram");
        xAxis.setLabel("green value");       
        yAxis.setLabel("count");
        bc.setBarGap(0);
        bc.setCategoryGap(0);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Green");
        for(int i = 0;i<256;++i) {
            series1.getData().add(new XYChart.Data(String.valueOf(i), g[i]));     
        }
        bc.getData().addAll(series1);
        AlertBox.displayChart("wykres", bc);
    } 
    private void showBlueHistogram(){
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Blue histogram");
        xAxis.setLabel("blue value");       
        yAxis.setLabel("blue count");
        bc.setBarGap(0);
        bc.setCategoryGap(0);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Blue");
        for(int i = 0;i<256;++i) {
            series1.getData().add(new XYChart.Data(String.valueOf(i), b[i]));     
        }
        bc.getData().addAll(series1);
        AlertBox.displayChart("wykres", bc);
    } 
    private void showAverageHistogram(){
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Average histogram");
        xAxis.setLabel("Average value");       
        yAxis.setLabel("Average count");
        bc.setBarGap(0);
        bc.setCategoryGap(0);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Average");
        for(int i = 0;i<256;++i) {
            series1.getData().add(new XYChart.Data(String.valueOf(i), average[i]));     
        }
        bc.getData().addAll(series1);
        AlertBox.displayChart("wykres", bc);
    } 
   
}
