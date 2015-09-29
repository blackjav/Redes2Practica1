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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
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
    private BufferedInputStream flujo;
    private InputStream llegada;
    private FileOutputStream destino;
    private byte[] buffer;
    private JFileChooser selector;
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
        String can="";
       try 
       {
           this.server = new ServerSocket(PUERTO);
           System.out.println("Todo Funcionando...!");
           this.socket = server.accept();
           this.entradaSocket = new InputStreamReader(socket.getInputStream());
           this.salidaText = new PrintWriter(socket.getOutputStream(),true);
           this.entradaText = new BufferedReader(entradaSocket); 
           this.llegada = socket.getInputStream();
           
            
           int len= 0;           
           JOptionPane.showMessageDialog(null, "La ip : '"+socket.getInetAddress().getHostName()+"' ha entrado a la session ", "Conexión entrante!!!", JOptionPane.INFORMATION_MESSAGE);
           
           String entrada =entradaText.readLine();
           System.out.println(entrada);
//            while(true)
//            {
                
                tamaño =entradaText.readLine();
                nombre = entradaText.readLine();
                can = entradaText.readLine();
                int ax = JOptionPane.showConfirmDialog(null, "El '"+socket.getInetAddress().getHostName()+"' Quiere mandarte "+can+" Archivo(s)\n¿Aceptas?");
                if(ax == JOptionPane.YES_NO_OPTION)
                {
                    System.out.println(tamaño);
                    this.selector = new JFileChooser();
                    System.out.println("dosveces"+tamaño);
                    buffer = new byte[1024];

                    selector.setSelectedFile(new File(nombre));// aqio va la variable nombre pero no recibe bien los datos
                    int respuesta = selector.showSaveDialog(null);
                    if (respuesta == JFileChooser.APPROVE_OPTION)
                    {
                        File file = selector.getSelectedFile();
                        destino=new FileOutputStream(file.getAbsolutePath());
                        while((len=llegada.read(buffer))>0) {
                                destino.write(buffer,0,len);

                        }
                    }
                }
//                Implementar el else de respuesta 
                 
//            }
            
            
//            END TRY
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
