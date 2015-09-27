/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.VentanaServidor;

/**
 *
 * @author javier
 */
public class ServiceServ extends Thread{
    
    private ServerSocket server;
    private PrintWriter salidaText;
    private BufferedReader entradaText;
    private InputStreamReader entradaSocket;
    private Socket socket;
    private FileOutputStream salidaFile;
    private BufferedOutputStream enviar;
    private FileInputStream entradaFile;
    private BufferedInputStream flujo;
    private static final int PUERTO = 5000;
    
    public ServiceServ()
    {
        super("servidor");
    }
    
    @Override
    public void run()
    {
        String tamaño="";
        String nombre="";
//        String tamaño="";
       try {
           this.server = new ServerSocket(PUERTO);
           System.out.println("Todo Funcionando...!");
           this.socket = server.accept();
           this.entradaSocket = new InputStreamReader(socket.getInputStream());
           this.salidaText = new PrintWriter(socket.getOutputStream(),true);
           this.entradaText = new BufferedReader(entradaSocket); 
           ObjectInputStream out = new ObjectInputStream(socket.getInputStream());
           FileOutputStream file = new FileOutputStream("/home/javier/archivomamalon.jpg");
           byte [] buff ;
           
           
           JOptionPane.showMessageDialog(null, "La ip : '"+socket.getInetAddress().getHostName()+"' ha entrado a la session ", "Conexión entrante!!!", JOptionPane.INFORMATION_MESSAGE);
           
            while(true)
            {
                 tamaño = this.entradaText.readLine();
                 System.out.println("entro"+tamaño);
                 buff = new byte[Integer.parseInt(tamaño)];
                 int len = out.read(buff);
                 if(len == -1) break;
                 file.write(buff,0,len);
                 
            }
        } catch (IOException ex) {
                Logger.getLogger(ServiceServ.class.getName()).log(Level.SEVERE, null, ex);
           }
        
    }
   
       
    public void enviarFIles( File file , int tamaño,String nombre ) throws FileNotFoundException, IOException
    {
//            
    }
    public void desconectar()
    {
        try {
            this.server.close();
            this.socket.close();
            this.entradaText.close();
            this.salidaText.close();
        } catch (IOException ex) {
            Logger.getLogger(ServiceServ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
