import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class Linear_Regression{
    public static void main(String args[]) {
        // Max_Lenght i 100
        float X[] = new float[100];
        float y[] = new float[100];
        float y_mean=0;
        float x_mean=0;
        float x_square=0;
        float x_y_sum=0;
        float m;
        float c;
        int n=0;
        Path csvFile = Paths.get("C:/Users/BHANU/Programs/Classwork_sem_III/Java/Salary_Data_train.csv");
        try{
            BufferedReader csvReader = Files.newBufferedReader(csvFile);
            String row = csvReader.readLine();
            while ( row != null) {
                String[] data = row.split(",");
                //System.out.println(data);
                X[n]=Float.parseFloat(data[0]);
                y[n]=Float.parseFloat(data[1]);
                y_mean=y_mean + y[n];
                x_mean=x_mean + X[n];
                x_y_sum=x_y_sum + (X[n] * y[n]);
                x_square=x_square + (X[n] * X[n]);
                row = csvReader.readLine();
                n=n+1;
            }
            
            float y_pred[] = new float[n];

            x_mean=x_mean/n;
            y_mean=y_mean/n;
            x_y_sum=x_y_sum/(n);
            x_square=x_square/(n);

            m = (x_y_sum - (x_mean*y_mean))/(x_square-(x_mean*x_mean));
            c = ((y_mean * x_square) - (x_mean * x_y_sum))/(x_square - (x_mean * x_mean));

            System.out.println("X\ty\ty_pred");
            for(int i=0;i<n;i++){
                y_pred[i]= (m * X[i]) + c;
                System.out.print(String.valueOf(X[i]) + ' ' + String.valueOf(y[i]) + ' ' + String.valueOf(y_pred[i]));
                System.out.println(' ');
            }
            
            System.out.println("X_mean: " + String.valueOf(x_mean) + "\t y_mean: "+ String.valueOf(y_mean));
            System.out.println("X_y_sum: " + String.valueOf(x_y_sum) + "\t X_square: "+ String.valueOf(x_square));
            System.out.println("M: " + String.valueOf(m) + "\t c: " + String.valueOf(c)); 
            csvReader.close();

            // Plotting Output
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Plotter plot = new Plotter();
            plot.set_X(X);
            plot.set_y(y);
            plot.set_y_pred(y_pred);
            f.add(plot);
            f.setSize(400,400);
            f.setLocation(200,200);
            f.setVisible(true);

        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }      
    }
}

class Plotter extends JPanel {
    
    float X[] = new float[100];
    float y[] = new float[100];
    float y_pred[] = new float[100];

    final int PAD = 20;

    public void set_X(float x[]){
        X=x;
    }
    public void set_y(float Y[]){
        y=Y;
    }

    public void set_y_pred(float ypred[]){
        y_pred = ypred;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        int y_ma = getMax(y);
        int y_mi = getMin(y);
        if(getMax(y_pred)>getMax(y)){
            y_ma = getMax(y_pred);
        }
        if(getMin(y_pred)<getMin(y)){
            y_mi = getMin(y_pred);
        }
        double x_scale = (double)(w - 2*PAD)/getMax(X);
        double y_scale = (double)(h - 2*PAD)/(y_ma-y_mi);
        // Mark original points
        g2.setPaint(Color.red);
        for(int i = 0; i < y.length; i++) {
            double x_coord = PAD + x_scale*X[i];
            double y_coord = h - PAD - y_scale*y[i];
            g2.fill(new Ellipse2D.Double(x_coord-2, y_coord-2, 4, 4));
        }
        // Getting points for line
        int max_X,max_y,min_X,min_y;
        max_X = Integer.MIN_VALUE;
        max_y = Integer.MIN_VALUE;
        min_X = Integer.MAX_VALUE;
        min_y = Integer.MAX_VALUE;
        for(int i = 0; i < y_pred.length; i++) {
            double x_coord = PAD + x_scale*X[i];
            double y_coord = h - PAD - y_scale*y_pred[i];
            if((int)x_coord <= min_X){
                min_X = (int)x_coord;
            }else if((int)x_coord >= max_X){
                max_X = (int)x_coord;
            }
            if((int)y_coord <= min_y){
                min_y = (int)y_coord;
            }else if((int)y_coord >= max_y){
                max_y = (int)y_coord;
            }
        }
        // Mark Line
        g2.setPaint(Color.BLUE);
        System.out.println(String.valueOf(min_X)+" "+String.valueOf(min_y)+" "+String.valueOf(max_X)+" "+String.valueOf(max_y)+" ");
        g2.drawLine(min_X, max_y,max_X, min_y);
    }
 
    private int getMax(float t[]) {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < t.length; i++) {
            if(t[i] > max)
                max = 1 + (int) t[i];
        }
        return max;
    }

    private int getMin(float t[]) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < t.length; i++) {
            if(t[i] < min)
                min =  (int)t[i] -1;
        }
        return min;
    }
}