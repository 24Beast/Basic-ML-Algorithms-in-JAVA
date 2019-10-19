import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Gradient_Descent{
    public static void main(String args[]) {
        // Max_Lenght i 100
        float X[][] = new float[100][100];
        float y[] = new float[100];
        float X_mean[] = new float[100];
        float y_mean = 0;
        float c = 0;
        int num_iter = 50;
        int num_features=0;
        int n=0;
        float a = 0.1f;
        Path csvFile = Paths.get("C:/Users/BHANU/Programs/Classwork_sem_III/Java/Test_Data.csv");
        try{
            BufferedReader csvReader = Files.newBufferedReader(csvFile);
            String row = csvReader.readLine();
            while ( row != null) {
                String[] data = row.split(",");
                //System.out.println(data);
                num_features=data.length - 1;
                for(int i=0;i<num_features;i++){
                    X[i][n]=Float.parseFloat(data[i]);
                    X_mean[i]+=X[i][n];
                }
                y[n]=Float.parseFloat(data[num_features]);
                y_mean+=y[n];
                row = csvReader.readLine();
                n=n+1;
            }
            for(int i = 0;i<num_features;i++){
                X_mean[i]=X_mean[i]/n;
                System.out.println("X_Mean No."+String.valueOf(i)+' '+String.valueOf(X_mean[i]));
            }
            y_mean=y_mean/n;
            System.out.println("y_mean: "+String.valueOf(y_mean));
            float m[] = new float[num_features];
            float x_x[][] = new float[num_features][num_features];
            float y_x[] = new float[num_features];
            
            // Creating Xi*Xj matrix
            for(int i=0;i<num_features;i++){
                for(int j=0;j<num_features;j++){
                    float x_sum=0;
                    float y_sum=0;
                    for(int k=0;k<X[i].length;k++){
                        x_sum+=X[i][k]*X[j][k];
                        y_sum+=X[j][k]*y[k];
                    }
                    x_x[i][j]=x_sum/n;
                    y_x[j]=y_sum/n;
                }
            }
            System.out.println("Matrix of Xi*Xj \t y*X[i]");
            for(int i=0;i<num_features;i++){
                for(int j=0;j<num_features;j++){
                    System.out.print(x_x[i][j]);
                    System.out.print(" ");
                }
                System.out.println(y_x[i]);
                m[i]=0;
            }

            for(int i=0;i<num_iter;i++){
                //Derivative of Cost wrt to c
                float m_x_mean=0;
                for(int j =0;j<num_features;j++){
                    m_x_mean+=m[j]*X_mean[j];
                }
                c = c + a*2*(y_mean-(c+m_x_mean));
                for(int k=0;k<num_features;k++){
                    //Derivative of Cost wrt to m
                    float m_x_x_sum=0;
                    for(int l=0;l<num_features;l++){
                        m_x_x_sum+=m[l]*x_x[k][l];
                    }
                    m[k] = m[k] + a*2*(y_x[k]-((X_mean[k]*c)+m_x_x_sum));
                }
            }

            float y_pred[] = new float[n];
            for(int i=0;i<n;i++){
                y_pred[i]=c;
                for(int j=0;j<num_features;j++){
                    y_pred[i]+= m[j]*X[j][i];
                }
            }
            float acc_score=0;
            for(int i=0;i<num_features;i++){
                System.out.println("M"+String.valueOf(i)+": "+String.valueOf(m[i]));
            }
            System.out.println("C: "+String.valueOf(c));
            System.out.println("X \t y \t y_pred");
            for(int i=0;i<n;i++){
                int s = 1;
                for(int j=0;j<num_features;j++){
                    System.out.print(String.valueOf(X[j][i]) + ' ' );
                }
                System.out.println(String.valueOf(y[i])+' '+(y_pred[i]));
                if(y_pred[i]>y[i]){
                    s=-1;
                }
                acc_score+= s*(y[i]-y_pred[i]);
            }
            System.out.println("Accuracy_Score: "+String.valueOf(acc_score));
            csvReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }      
    }
}