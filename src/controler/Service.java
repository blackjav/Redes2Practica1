/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javier
 */
public class Service extends Thread{
    
    private ServerSocket server;
    private PrintWriter salidaText;
    private BufferedReader entradaText;
    private Socket socket;
    private FileOutputStream salidaFile;
    private FileInputStream entradaFile;
    private static final int PUERTO = 5000;
    
    public Service()
    {
        super("servidor");
            try
            {
                this.server = new ServerSocket(PUERTO);
                this.socket = server.accept();
                this.salidaText = new PrintWriter(socket.getOutputStream());
                this.entradaText = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Todo funcionando !!!!");
            }catch(IOException e){
                    e.printStackTrace();
            }
//            run();
        
    }
    
    @Override
    public void run()
    {
       try {
            while(true)
            {
                String tipo = this.entradaText.readLine();
            }
        } catch (IOException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
           }
        
    }
    public void enviarMsj(String mensaje)
    {
        salidaText.println(mensaje);
    }
    public void desconectar()
    {
        try {
            this.server.close();
            this.socket.close();
            this.entradaText.close();
            this.salidaText.close();
        } catch (IOException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
