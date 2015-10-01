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
import org.apache.axis.encoding.Base64;
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
//        Se crean archivos que se van a usar 
        String tamaño="";
        String nombre="";
        String can="";
        String archivo ="";
       try 
       {
           this.server = new ServerSocket(PUERTO);
//           System.out.println("Todo Funcionando...!");
           JOptionPane.showMessageDialog(null, "Servidor is ready run !!", "alert", JOptionPane.INFORMATION_MESSAGE);
           this.socket = server.accept();
           this.entradaSocket = new InputStreamReader(socket.getInputStream());
           this.salidaText = new PrintWriter(socket.getOutputStream(),true);
           this.entradaText = new BufferedReader(entradaSocket); 
           this.llegada = socket.getInputStream();
//           this.salidaText = new PrintWriter(socket.getOutputStream(),true);
           
           int i =1;
           int len= 0;  
           int cantidad;
//           Si se detecta un cliente 
           JOptionPane.showMessageDialog(null, "La ip : '"+socket.getInetAddress().getHostName()+"' ha entrado a la session ", "Conexión entrante!!!", JOptionPane.INFORMATION_MESSAGE);
           
//           COnstruimos el buffer de entrada y creamos hilo de espera
           buffer = new byte[1024];
            while(true)
            {
//                TODO SI algo falla regresa aqui!!!
                entradaText.readLine();
//                System.out.println(entrada);
                can = entradaText.readLine();
                cantidad= Integer.parseInt(can);
                
//                Le decimos al usuario la cantidad de archivos que va a arecibir
                int ax = JOptionPane.showConfirmDialog(null, "El '"+socket.getInetAddress().getHostName()+"' Quiere mandarte "+can+" Archivo(s)\n¿Aceptas?");
                if(ax == JOptionPane.YES_NO_OPTION)
                {
                    VentanaServidor.jpProgress.setMaximum(cantidad);
                    VentanaServidor.jpProgress.setMinimum(0);
                    
                    while(cantidad > 0)
                    {
//                            Lectura del tamañao el nombre y el archivo en tipo String 
                            tamaño =entradaText.readLine();
                            nombre = entradaText.readLine();
                            archivo = entradaText.readLine();
                            int au = JOptionPane.showConfirmDialog(null, "Archivo de entrada  '"+nombre+"' Con un tamaño de "+tamaño+"bytes \n¿Deseas Aceptar?");
                            if(au == JOptionPane.YES_NO_OPTION)
                            {
//                                Decodificamos el string del archivo y lo escribimos en el disdco duro
                                buffer = Base64.decode(archivo);
                                System.out.println(archivo);

                                this.selector = new JFileChooser();
                                selector.setSelectedFile(new File(nombre));
                                int respuesta = selector.showSaveDialog(null);

                                if (respuesta == JFileChooser.APPROVE_OPTION)
                                {
                                    File file = selector.getSelectedFile();
                                    destino=new FileOutputStream(file.getAbsolutePath());
                                    destino.write(buffer);
                                    salidaText.println("*Se ha aceptado el archivo con el nombre de : '" +nombre+" '");
//                                    salidaText.close();
                                }
                            }
                            
                            else
                            {
                                salidaText.println("*El host no ha aceptado su archivo : '" +nombre+" '");
                                    //salidaText.close();
                            }
                            VentanaServidor.jpProgress.setValue(i);
                        i++;
                        cantidad --;
                    }
                }
                    
            }
            
            
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
