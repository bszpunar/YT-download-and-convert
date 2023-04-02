package org.example;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.UnmarshalException;
import java.sql.SQLOutput;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.Thread.sleep;

public class GUI{


    Image logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage();
    Image hendrix = new ImageIcon(getClass().getClassLoader().getResource("jimiHH.png")).getImage();
    private String user = "user";
    private String password = "password";
    private int port;
    private String serverAddress;
    private FTPClient ftpClient = new FTPClient();

    private String userPath = System.getProperty("user.home");

    public GUI(){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("YouTube MP3");
        jFrame.setIconImage(logo);
        jFrame.setResizable(false);
        jFrame.setSize(800, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());
        jFrame.getContentPane().setBackground(Color.BLACK);



        JPanel jPanelURL = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelURL.setPreferredSize(new Dimension(jFrame.getWidth(),50));
        jPanelURL.setBackground(Color.BLACK);

        JPanel jPanelINFO = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelINFO.setPreferredSize(new Dimension(jFrame.getWidth(),180));
        jPanelINFO.setBackground(Color.BLACK);

        JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelButton.setPreferredSize(new Dimension(jFrame.getWidth(),50));
        jPanelButton.setBackground(Color.BLACK);

        JPanel jPanelFTP = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        jPanelFTP.setPreferredSize(new Dimension(jFrame.getWidth()-50,44));
        jPanelFTP.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jPanelFTP.setBackground(Color.BLACK);

        JLabel jLabelServerADDRESS = new JLabel("ADDRESS: ");
        jLabelServerADDRESS.setBackground(Color.BLACK);
        jLabelServerADDRESS.setForeground(Color.WHITE);

        JTextArea jTextAreaServerADDRESS = new JTextArea("192.168.1.102");
        jTextAreaServerADDRESS.setPreferredSize(new Dimension(150,20));
        jTextAreaServerADDRESS.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jTextAreaServerADDRESS.setBackground(Color.BLACK);
        jTextAreaServerADDRESS.setForeground(Color.WHITE);
        jTextAreaServerADDRESS.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jTextAreaServerADDRESS.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                jTextAreaServerADDRESS.setBorder(BorderFactory.createLineBorder(Color.WHITE));

            }
        });

        JLabel jLabelPORT = new JLabel("PORT: ");
        jLabelPORT.setBackground(Color.BLACK);
        jLabelPORT.setForeground(Color.WHITE);

        JTextArea jTextAreaPORT = new JTextArea("2121");
        jTextAreaPORT.setPreferredSize(new Dimension(100,20));
        jTextAreaPORT.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jTextAreaPORT.setBackground(Color.BLACK);
        jTextAreaPORT.setForeground(Color.WHITE);
        jTextAreaPORT.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jTextAreaPORT.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                jTextAreaPORT.setBorder(BorderFactory.createLineBorder(Color.WHITE));

            }
        });


        JTextArea jTextAreaURL = new JTextArea();
        jTextAreaURL.setPreferredSize(new Dimension(500,20));
        jTextAreaURL.setBackground(Color.BLACK);
        jTextAreaURL.setForeground(Color.WHITE);
        jTextAreaURL.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jTextAreaURL.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jTextAreaURL.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jTextAreaURL.setBorder(BorderFactory.createLineBorder(Color.WHITE));

            }
        });

        JTextArea jTextAreaInfoActually = new JTextArea(8,70);
        jTextAreaInfoActually.setLineWrap(true);
        jTextAreaInfoActually.setWrapStyleWord(true);
        jTextAreaInfoActually.setBackground(Color.BLACK);
        jTextAreaInfoActually.setForeground(Color.WHITE);
        jTextAreaInfoActually.setBorder(BorderFactory.createLineBorder(Color.WHITE));


        JScrollPane jScrollPane = new JScrollPane(jTextAreaInfoActually);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JLabel jLabelURL = new JLabel("URL: ");
        jLabelURL.setForeground(Color.WHITE);

        JLabel img = new JLabel(new ImageIcon("resources/jimiH.png"));
        img.setVisible(false);



        JLabel info = new JLabel("");
        info.setBackground(Color.BLACK);
        info.setForeground(Color.GREEN);

        JButton moveButton = new JButton("MOVE");
        moveButton.setBackground(Color.BLACK);
        moveButton.setForeground(Color.WHITE);
        moveButton.setPreferredSize(new Dimension(150,20));
        moveButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        moveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                moveButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moveButton.setForeground(Color.WHITE);
            }
        });

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread move = new Thread(() -> {
                    Set<String> listFilesInFormatted = listFilesUsingJavaIO(userPath+"/Desktop/Music/Formatted/");
                    for(String x : listFilesInFormatted){
                        String urlToDownload;
                        ProcessBuilder processBuilder;

                        urlToDownload = "move \""+x+"\" "+userPath+"/Desktop/Music/Formatted/\"Already Sent Files\"";
                        processBuilder = new ProcessBuilder("cmd.exe", "/c",urlToDownload);
                        processBuilder.directory(new File(userPath+"/Desktop/Music/Formatted/"));
                        processBuilder.redirectErrorStream(true);
                        Process process = null;
                        try {
                            process = processBuilder.start();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));

                        while (true){
                            try {

                                jTextAreaInfoActually.append(r.readLine());
                                jTextAreaInfoActually.append(System.lineSeparator());
                                jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                                if (r.readLine() == null){
                                    jTextAreaInfoActually.append("File "+x+" moved Successfully !");
                                    jTextAreaInfoActually.append(System.lineSeparator());
                                    jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                                    break;
                                }
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    }
                });

                move.start();

                /*new Thread(() -> {

                    jTextAreaInfoActually.append("You have to wait 1 minute before move all files !");
                    jTextAreaInfoActually.append(System.lineSeparator());
                    jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                    for (int i=60; i>=0; i--){
                        if(i == 0){
                            jTextAreaInfoActually.append("Moving ...");
                            jTextAreaInfoActually.append(System.lineSeparator());
                            jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                            move.start();
                            break;
                        }
                        if(i/2 == 30){
                            jTextAreaInfoActually.append(i+"seconds !");
                            jTextAreaInfoActually.append(System.lineSeparator());
                            jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                        }else if(i/2 + 15 == 45){
                            jTextAreaInfoActually.append(i+"seconds !");
                            jTextAreaInfoActually.append(System.lineSeparator());
                            jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                        } else if (i/2 - 15 == 15) {
                            jTextAreaInfoActually.append(i+"seconds !");
                            jTextAreaInfoActually.append(System.lineSeparator());
                            jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                        }

                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                    }

                }).start();*/




            }
        });


        JButton sendButton = new JButton("SEND");
        sendButton.setBackground(Color.BLACK);
        sendButton.setForeground(Color.WHITE);
        sendButton.setPreferredSize(new Dimension(150,20));
        sendButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        sendButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setForeground(Color.WHITE);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File dirAlreadySent = new File(userPath+"/Desktop/Music/Formatted/Already Sent Files");
                if(!dirAlreadySent.exists()) dirAlreadySent.mkdir();


                Set<String> listFilesInFormatted = listFilesUsingJavaIO(userPath+"/Desktop/Music/Formatted/");

                Thread thread = new Thread(() -> {
                    jTextAreaInfoActually.setText("");

                    for (String x : listFilesInFormatted){

                        if(!jTextAreaServerADDRESS.getText().equals("") && !jTextAreaPORT.getText().equals("")){
                            try {
                                ftpClient.connect(jTextAreaServerADDRESS.getText(), Integer.parseInt(jTextAreaPORT.getText()));
                                ftpClient.login(user,password);
                                ftpClient.enterLocalPassiveMode();
                                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                                File fileX = new File(userPath+"/Desktop/Music/Formatted/"+x);

                                //change string for char arr
                                char[] arrChar = x.toCharArray();
                                //exchange incopatibile chars

                                for(int i=0; i<arrChar.length; i++){
                                    switch (arrChar[i]){
                                        case 'ą': arrChar[i] = ' ';
                                        case 'ć': arrChar[i] = ' ';
                                        case 'ę': arrChar[i] = ' ';
                                        case 'ł': arrChar[i] = ' ';
                                        case 'ń': arrChar[i] = ' ';
                                        case 'ó': arrChar[i] = ' ';
                                        case 'ś': arrChar[i] = ' ';
                                        case 'ź': arrChar[i] = ' ';
                                        case 'ż': arrChar[i] = ' ';
                                        case 'Ą': arrChar[i] = ' ';
                                        case 'Ć': arrChar[i] = ' ';
                                        case 'Ę': arrChar[i] = ' ';
                                        case 'Ł': arrChar[i] = ' ';
                                        case 'Ń': arrChar[i] = ' ';
                                        case 'Ó': arrChar[i] = ' ';
                                        case 'Ś': arrChar[i] = ' ';
                                        case 'Ź': arrChar[i] = ' ';
                                        case 'Ż': arrChar[i] = ' ';
                                        case '\"': arrChar[i] = ' ';
                                        case '-': arrChar[i] = ' ';
                                        case '_': arrChar[i] = ' ';
                                        case '`': arrChar[i] = ' ';
                                        case ',': arrChar[i] = ' ';
                                        case '\'': arrChar[i] = ' ';
                                    }
                                }

                                //make string
                                String str = "";
                                for(char j : arrChar) str += j;

                                FileInputStream fileInputStream = new FileInputStream(fileX);
                                boolean isSent = ftpClient.storeFile(str, fileInputStream);

                                if(isSent){

                                    jTextAreaInfoActually.append("File "+x+" sent Successfully !");
                                    jTextAreaInfoActually.append(System.lineSeparator());
                                    jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);

                                }else{
                                    jTextAreaInfoActually.append("Error occured during sending File "+x+" !");
                                    jTextAreaInfoActually.append(System.lineSeparator());
                                    jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);
                                }


                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }else {
                            info.setForeground(Color.GREEN);
                            info.setText("Field ADDRESS and PORT is required !");
                            try {
                                sleep(3000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            info.setText("");
                        }

                    }



                });

                thread.start();

            }
        });





        JButton downloadButton = new JButton("Download");
        downloadButton.setPreferredSize(new Dimension(150,20));
        downloadButton.setBackground(Color.BLACK);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        downloadButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                downloadButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                downloadButton.setForeground(Color.WHITE);

            }
        });
        downloadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                    String urlToDownload;
                    ProcessBuilder processBuilder;
                    File fileMusic = new File(userPath+"\\Desktop\\Music");

                    if(!fileMusic.exists()) fileMusic.mkdirs();

                    System.out.println("Enter YouTube URL: ");
                    urlToDownload = "yt-dlp -f mp4 -o "+userPath+"\\Desktop\\Music\\%(title)s.%(ext)s "+jTextAreaURL.getText();
                    processBuilder = new ProcessBuilder("cmd.exe", "/c",urlToDownload);
                    processBuilder.redirectErrorStream(true);
                    Process process = null;
                    try {
                        process = processBuilder.start();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));


                    Thread threadContainer = new Thread(() -> {
                    String line = "";
                    jTextAreaInfoActually.setText("");
                    while (true) {
                        try {
                            line = r.readLine();

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (line == null) {
                            info.setForeground(Color.GREEN);
                            info.setText("Downloading Finished!");
                            try {
                                sleep(10000);
                                info.setText("");
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }

                            break;
                        }
                        System.out.println(line);
                        jTextAreaInfoActually.append(line);
                        jTextAreaInfoActually.append(System.lineSeparator());
                        jTextAreaInfoActually.getCaret().setDot(Integer.MAX_VALUE);

                    }
                });

                threadContainer.start();


            }
        });

        JButton formatAllButton = new JButton("Format MP3");
        formatAllButton.setPreferredSize(new Dimension(150,20));
        formatAllButton.setBackground(Color.BLACK);
        formatAllButton.setForeground(Color.WHITE);
        formatAllButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        formatAllButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                formatAllButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                formatAllButton.setForeground(Color.WHITE);
            }
        });
        formatAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread threadContainer = new Thread(() -> {
                    String urlToDownload;
                    ProcessBuilder processBuilder;


                    File fileFormatted = new File(userPath+"\\Desktop\\Music\\Formatted");


                    String fileName;

                    if(!fileFormatted.exists()) fileFormatted.mkdirs();

                    Set<String> listFilesMusic = listFilesUsingJavaIO(userPath+"/Desktop/Music");
                    if(listFilesMusic.isEmpty()){
                        info.setText("Directory is Empty !");

                        Thread thread = new Thread(() -> {
                            try {
                                sleep(10000);
                                info.setText("");
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        thread.start();

                    }
                    for (String x : listFilesMusic){
                        if(getExtensionByApacheCommonLib(x).equals("mp4")){

                            fileName = x;

                            char[] fileNameCharArr = fileName.toCharArray();

                            String newFileName = "";

                            for(int i=0; i<fileNameCharArr.length-4; i++){
                                newFileName += fileNameCharArr[i];
                            }

                            urlToDownload = "ffmpeg -y -i \""+fileName+"\" Formatted/\""+newFileName+"\".mp3";

                            processBuilder = new ProcessBuilder("cmd.exe", "/c", urlToDownload);
                            //ciekawe
                            processBuilder.directory(new File(userPath+"/Desktop/Music/"));

                            processBuilder.redirectErrorStream(true);
                            Process process = null;
                            try {
                                process = processBuilder.start();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));

                            String line = "";
                            jTextAreaInfoActually.setText("");
                            while (true) {
                                try {
                                    line = r.readLine();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (line == null) {
                                    info.setForeground(Color.GREEN);
                                    info.setText("Formatting "+x+" Finished!");
                                    try {
                                        sleep(2000);
                                        info.setText("");
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                    File file = new File(userPath+"/Desktop/Music/"+fileName);
                                    if(file.delete()) {
                                        System.out.println("File Deleted!");
                                        jTextAreaInfoActually.append("File deleted after Formatted !");
                                    }

                                    break;
                                }
                                System.out.println(line);


                                jTextAreaInfoActually.append(line);
                                jTextAreaInfoActually.append(System.lineSeparator());
                                jTextAreaInfoActually.getCaret().setDot( Integer.MAX_VALUE );

                            }




                        }
                    }
                });

                threadContainer.start();

            }
        });

        JButton instructionButton = new JButton("HELP !");
        instructionButton.setPreferredSize(new Dimension(150,20));
        instructionButton.setBackground(Color.BLACK);
        instructionButton.setForeground(Color.WHITE);
        instructionButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        instructionButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                instructionButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                instructionButton.setForeground(Color.WHITE);

            }
        });
        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextAreaInfoActually.setText("");
                //img.setVisible(!img.isVisible());
                jTextAreaInfoActually.append("Hello there!");
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append("My name is Bartek I'll help you configure you PC :D");
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append("Firstly you have to add 'yt-dlp' to Environment Variables (you don't know how ? - my advice Google it!)");
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append("Secondly you must add 'ffmpeg' to Environment Variables too. And IT'S ALL ! ENJOY !");
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append("Just paste youtube's link to URL and click Download !");
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append(System.lineSeparator());
                jTextAreaInfoActually.append("Destination PATH downloaded files:    /Desktop/Music/");
            }
        });



        jPanelURL.add(jLabelURL);
        jPanelURL.add(jTextAreaURL);

        jPanelINFO.add(jScrollPane);
        jPanelINFO.add(info);

        jPanelButton.add(downloadButton);
        jPanelButton.add(formatAllButton);
        jPanelButton.add(instructionButton);

        jPanelFTP.add(jLabelServerADDRESS);
        jPanelFTP.add(jTextAreaServerADDRESS);
        jPanelFTP.add(jLabelPORT);
        jPanelFTP.add(jTextAreaPORT);
        jPanelFTP.add(sendButton);
        jPanelFTP.add(moveButton);



        jFrame.add(jPanelURL);
        jFrame.add(jPanelINFO);
        jFrame.add(jPanelButton);
        jFrame.add(jPanelFTP);
        jFrame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI();
    }

    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

}
