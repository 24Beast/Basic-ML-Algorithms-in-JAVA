import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }      
    }
}