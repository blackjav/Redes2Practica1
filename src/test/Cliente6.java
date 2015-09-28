/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import javax.swing.JFileChooser;
public class Cliente6 {
        public static void main(String[] args) {
                try {
                        Socket w=new Socket("localhost",4000);
                        PrintStream envio=new PrintStream(w.getOutputStream());
                        
//                        FileInputStream origen=new FileInputStream("/home/javier/Imágenes/fondos-de-pantalla-high-definition.jpg");
//                        int a =origen.read();
//                        System.out.println("TAMAÑO"+a);
                        
                        JFileChooser selector = new JFileChooser();
                        selector.setMultiSelectionEnabled(true);
                        int respuesta = selector.showOpenDialog(null);

                        if (respuesta == JFileChooser.APPROVE_OPTION)
                        {
                            
                             File archivos[]=selector.getSelectedFiles();
                             System.out.println("LOCATION"+selector.getLocation());
                            System.out.println("Cantidad de archivosS "+archivos.length);
                            int i=1;
                            for(File f:archivos)
                            {
            //                    f.getName();
                                System.out.println("Nombre del archivo ["+i+"]"+f.getName());
                                System.out.println("Tamaño del archivo["+i+"]:" +f.length());
                                FileInputStream origen=new FileInputStream(f);
                                
                                byte[] buffer = new byte[1024];
                                int len;
                                while((len=origen.read(buffer))>0) {
                                        envio.write(buffer,0,len);
                                }
                                i++;
                            }
                        
                        }
                        
                } catch(IOException e) {
                        e.printStackTrace();
                }
        }
}