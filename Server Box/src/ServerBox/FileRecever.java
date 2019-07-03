package ServerBox;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author deven
 */
public class FileRecever extends Thread {

    private final Socket fs;   
    private DataInputStream din = null;
    private FileOutputStream fout = null;
    private javax.swing.JTextArea output;

    File f = null;
    int length;
    boolean isSent;
    long size;
    String name;

    public FileRecever(Socket fs, javax.swing.JTextArea output) {
        this.fs = fs;
        this.output  = output;
    }
    @Override
    public void run() {
        try {
            System.out.println("File Receiver Connected");
            din = new DataInputStream(fs.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(FileRecever.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                name = din.readUTF();
                size = din.readLong();
                String path = System.getProperty("user.home");
                f = new File(path +"/"+ name);
                fout = new FileOutputStream(f);
                byte[] b = new byte[1024 * 1024];
                while ((length = din.read(b)) > 0) {
                    fout.write(b, 0, length);
                    if (f.length() == size) {
                        break;
                    }
                }
                output.append(name + " is received");
            } catch (Exception ex) {
                Logger.getLogger(FileRecever.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}