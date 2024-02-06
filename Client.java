import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
public class Client{
    public static void main(String [] args) {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {

            socket = new Socket ("localhost",1234);

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner (System.in);

            System.out.println("devam etmek istiyosan kullanıcı adını yaz: ");
            String gidecekmesaj = scanner.nextLine();
            bufferedWriter.write(gidecekmesaj);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while(true) {
                System.out.println("bana hangi komutu nasıl uygulamak istediğini söyle: ");
                String gidecekmesajuuu = scanner.nextLine();
                bufferedWriter.write(gidecekmesajuuu);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                if(gidecekmesaj.equalsIgnoreCase("logout")) {
                    socket.close();
                    inputStreamReader.close();
                    outputStreamWriter.close();
                    bufferedReader.close();
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {

                if(socket!=null)
                    socket.close();
                if(inputStreamReader!=null)
                    inputStreamReader.close();
                if(outputStreamWriter!=null)
                    outputStreamWriter.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(bufferedWriter!=null)
                    bufferedReader.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

