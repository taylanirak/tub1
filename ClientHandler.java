import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final List<String> inboxTaylan;
    private final List<String> sentTaylan;
    private final List<String> inboxCevher;
    private final List<String> sentCevher;
    private final List<Object> subjectlist;

    public ClientHandler(Socket socket,List<String> inboxTaylan,List<String> inboxCevher,List<String> sentTaylan, List<String> sentCevher, List<Object> subjectlist) {

        this.socket = socket;
        this.inboxTaylan = inboxTaylan;
        this.sentTaylan = sentTaylan;
        this.inboxCevher = inboxCevher;
        this.sentCevher = sentCevher;
        this.subjectlist = subjectlist;
    }
    public void run() {


        String user = null;
        String passw = null;
        String mailReceiver=null;
        String mailInboxRec=null;
        String subject=null;
        String message=null;
        String sirasi= null;
        String kisimi1= null;
        String kisimi2= null;
        String kisimi3= null;
        String kisimi4= null;
        /** List<Object> subjectlist= new ArrayList<>();
         List<String> inboxTaylan = new ArrayList<>();
         List<String> inboxCevher = new ArrayList<>();
         List<String> sentTaylan = new ArrayList<>();
         List<String> sentCevher = new ArrayList<>();**/

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        int counter = 0;

        while (counter<1000000) {String hersey = null;
            if (counter == 0) {
                System.out.println("GİRİŞ ZAMANI");
                try {
                    hersey = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] parts = hersey.split(":");
                String komut = parts[0];
                String komut1 = parts[2];
                if (counter == 0) {
                    if (komut.equals("username")&&komut1.equals("password")) {
                        user = parts[1];
                        passw = parts[3];
                        if((user.equals("taylan")&&passw.equals("TAY")|| (user.equals("cevher")&&passw.equals("CEV")))){
                            System.out.println("giriş yapan user: " + user);
                        }
                    }
                    else {
                        System.out.println("giris basarisiz");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            inputStreamReader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            outputStreamWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            bufferedWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    user=parts[1];
                }
            }
            String devam = null;
            try {
                devam = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("istedigin: " + devam);

            String[] bolum = devam.split(":");
            int v = subjectlist.size();
            if (counter > 0) {
                if (bolum[0].equals("send")) {
                    System.out.println("demek gondermek istiyorsun");
                    mailReceiver = bolum[1];
                    subject = bolum[2];
                    message = bolum[3];
                    {
                        HashMap<String, String> b = new HashMap<>();
                        b.put(subject, message);
                        for (String key : b.keySet()) {
                            System.out.println(b.get(key));
                            String string = v + ":" + subject + ":" + b.get(key);
                            String[] split = string.split(":");

                            subjectlist.add(split[2]);

                            if(mailReceiver.equals("taylan")){
                                inboxTaylan.add(split[2]);
                            }
                            if(mailReceiver.equals("cevher")) {
                                inboxCevher.add(split[2]);
                            }
                            if(user.equals("taylan")) {
                                sentTaylan.add(split[2]);
                            }
                            if(user.equals("cevher")) {
                                sentCevher.add(split[2]);
                            }


                            counter++;
                        }
                        counter++;
                    }

                }


            }
            counter++;
            if (counter > 0) {
                if (bolum[0].equals("get")) {
                    System.out.println("demek get komutu. TAMAM!");
                    mailInboxRec = bolum[1];
                    subject = bolum[2];
                    message = bolum[3];
                    {

                        HashMap<String, String> a = new HashMap<>();

                        a.put(subject, message);
                        for (String key : a.keySet()) {

                            System.out.println(a.get(key)); //bunun yerine bi listeye kaydet
                            v = v + 1;
                            String ikincistri = v + ":" + subject + ":" + a.get(key);
                            String[] split = ikincistri.split(":");


                            subjectlist.add(split[2]);

                            if(user.equals("taylan")){
                                inboxTaylan.add(split[2]);
                            }
                            if(user.equals("cevher")) {
                                inboxCevher.add(split[2]);
                            }
                            if(mailInboxRec.equals("taylan")) {
                                sentTaylan.add(split[2]);
                            }
                            if(mailInboxRec.equals("cevher")) {
                                sentCevher.add(split[2]);
                            }

                            counter++;
                        }
                        counter++;
                    }
                }
            }
            counter++;
            if (counter > 0) {
                if (bolum[0].equals("read")) {
                    sirasi = (bolum[1]);

                    int index = Integer.parseInt(sirasi);

                    int x=0;
                    List<List<String>> sonuc = new ArrayList<>();
                    for (int i = 0; i < subjectlist.size(); i += 1) {
                        List<String> grup = new ArrayList<>();
                        for (int j = i; j < i + 1 && j < subjectlist.size(); j++) {
                            grup.add((String) subjectlist.get(j));

                        }

                        sonuc.add(grup);

                        if(index>=0&& index<sonuc.size()&&x<1) {
                            String sonagel = sonuc.get(index).getFirst();

                            System.out.println(sonagel);
                            x++;
                        }


                    }


                }
                counter++;
            }


            if (counter >= 0) {
                if (bolum[0].equals("inboxtaylan")) {
                    kisimi1 = (bolum[1]);

                    int ici1 = Integer.parseInt(kisimi1);
                    System.out.println(inboxTaylan.get(ici1));
                    System.out.println(inboxTaylan);

                }
            }
            if (counter >= 0) {
                if (bolum[0].equals("inboxcevher")) {
                    kisimi2 = (bolum[1]);

                    int ici2 = Integer.parseInt(kisimi2);
                    System.out.println(inboxCevher);
                    System.out.println(inboxCevher.get(ici2));

                }
            }
            if (counter >= 0) {
                if (bolum[0].equals("senttaylan")) {
                    kisimi3 = (bolum[1]);

                    int ici3 = Integer.parseInt(kisimi3);
                    System.out.println(sentTaylan);
                    System.out.println(sentTaylan.get(ici3));

                }
            }
            if (counter >= 0) {
                if (bolum[0].equals("sentcevher")) {
                    kisimi4 = (bolum[1]);

                    int ici4 = Integer.parseInt(kisimi4);
                    System.out.println(sentCevher);
                    System.out.println(sentCevher.get(ici4));

                }
            }

            //bufferedWriter.write(hersey);  SIKINTI ÇIKARIYOURDU NEDEN??
            try {
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            counter++;

        }
    }


}
