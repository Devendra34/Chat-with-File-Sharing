/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientBox;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deven
 */
public class FileReceiver  extends Thread{
    private Socket fs = null;
    private DataInputStream din = null;
    private FileOutputStream fout = null;
    File f = null;
    int length;
    long size;
    String name;
    private javax.swing.JTextArea output;

    public FileReceiver(Socket fs, javax.swing.JTextArea output) {
        this.fs = fs;
        this.output = output;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("File Receiver Connected");
            din = new DataInputStream(fs.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
