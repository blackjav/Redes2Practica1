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
    private FileInputStream entradaFile;
    private static final int PUERTO = 5000;
    
    public ServiceServ()
    {
        super("servidor");
    }
    
    @Override
    public void run()
    {
        String texto="test";
       try {
           this.server = new ServerSocket(PUERTO);
           System.out.println("Todo Funcionando...!");
           this.socket = server.accept();
           this.entradaSocket = new InputStreamReader(socket.getInputStream());
           this.salidaText = new PrintWriter(socket.getOutputStream(),true);
           this.entradaText = new BufferedReader(entradaSocket);
           JOptionPane.showMessageDialog(null, "La ip : '"+socket.getInetAddress().getHostName()+"' ha entrado a la session ", "Conexión entrante!!!", JOptionPane.INFORMATION_MESSAGE);
//           System.err.println("Cliente conectado..." );
           
            while(true)
            {
                 texto= this.entradaText.readLine();
                 System.out.println("Entro "+texto);
                 VentanaServidor.txtAreaText.setText(VentanaServidor.txtAreaText.getText() + "\n" +texto);
//                 Hay que detectar cuando el cliente se vaya 
            }
        } catch (IOException ex) {
                Logger.getLogger(ServiceServ.class.getName()).log(Level.SEVERE, null, ex);
           }
        
    }
    public void enviarMsj(String mensaje)
    {
        try {
            this.salidaText = new PrintWriter(socket.getOutputStream(),true);
            System.out.println("Se va a enviar "+ mensaje);
            salidaText.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ServiceServ.class.getName()).log(Level.SEVERE, null, ex);
        }
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
