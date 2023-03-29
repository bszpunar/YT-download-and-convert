package org.example;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.Thread.sleep;

public class GUI{


    Image logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage();
    Image hendrix = new ImageIcon(getClass().getClassLoader().getResource("jimiHH.png")).getImage();


    public GUI(){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("YouTube MP3");
        jFrame.setIconImage(logo);
        jFrame.setSize(800, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());
        jFrame.getContentPane().setBackground(Color.BLACK);



        JPanel jPanelURL = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelURL.setPreferredSize(new Dimension(jFrame.getWidth(),50));
        jPanelURL.setBackground(Color.BLACK);

        JPanel jPanelINFO = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelINFO.setPreferredSize(new Dimension(jFrame.getWidth(),200));
        jPanelINFO.setBackground(Color.BLACK);

        JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.CENTER,10,15));
        jPanelButton.setPreferredSize(new Dimension(jFrame.getWidth(),100));
        jPanelButton.setBackground(Color.BLACK);


        JTextArea jTextAreaURL = new JTextArea();
        jTextAreaURL.setPreferredSize(new Dimension(500,20));
        jTextAreaURL.setBackground(Color.BLACK);
        jTextAreaURL.setForeground(Color.WHITE);
        jTextAreaURL.setBorder(BorderFactory.createLineBorder(Color.WHITE));

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




        JButton downloadButton = new JButton("Download");
        downloadButton.setPreferredSize(new Dimension(150,20));
        downloadButton.setBackground(Color.BLACK);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        downloadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                    String urlToDownload;
                    ProcessBuilder processBuilder;
                    String user = System.getProperty("user.home");  // pobiera C:\\Users\bszpu
                    File fileMusic = new File(user+"\\Desktop\\Music");

                    if(!fileMusic.exists()) fileMusic.mkdirs();

                    System.out.println("Enter YouTube URL: ");
                    urlToDownload = "yt-dlp -f mp4 -o "+user+"\\Desktop\\Music\\%(title)s.%(ext)s "+jTextAreaURL.getText();
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

        formatAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread threadContainer = new Thread(() -> {
                    String urlToDownload;
                    ProcessBuilder processBuilder;

                    String user = System.getProperty("user.home");
                    File fileFormatted = new File(user+"\\Desktop\\Music\\Formatted");


                    String fileName;

                    if(!fileFormatted.exists()) fileFormatted.mkdirs();

                    Set<String> listFilesMusic = listFilesUsingJavaIO(user+"/Desktop/Music");
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
                            processBuilder.directory(new File(user+"/Desktop/Music/"));

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

                                    File file = new File(user+"/Desktop/Music/"+fileName);
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




        jFrame.add(jPanelURL);
        jFrame.add(jPanelINFO);
        jFrame.add(jPanelButton);
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
