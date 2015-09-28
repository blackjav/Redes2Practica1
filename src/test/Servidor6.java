/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Servidor6 {
        public static void main(String[] args) {
                try {
                        ServerSocket servidor=new ServerSocket(4000);
//                        Scanner s=new Scanner(System.in);
//                        System.out.println("Ingrese el path donde desea escribir el archivo: " );
//                        String path=s.nextLine();
                        Socket w=servidor.accept();
                        InputStream llegada = w.getInputStream();
                        FileOutputStream destino=new FileOutputStream("/home/javier/archivito.jpg");
                        byte[] buffer = new byte[1024];
                        int len;
                        while((len=llegada.read(buffer))>0) {
                                destino.write(buffer,0,len);
                        }
                } catch(IOException e) {
                        e.printStackTrace();
                }
        }
}