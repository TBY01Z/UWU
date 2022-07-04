package com.prog2.uwugroup;

import com.prog2.uwugroup.packets.RemovePlayerPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class Client implements Runnable {

    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running = false;
    private Listener listener;

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    //verbindung mit server aufbauen
    public void connect(){
        try{
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new Listener();
            new Thread(this).start();
        } catch(ConnectException e){
            e.printStackTrace();
            System.out.print("\nEs konnte keine Verbindung zum Server hergestellt werden.");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //verbindung schliessen
    public void close(){
        try {
            running = false;
            RemovePlayerPacket packet = new RemovePlayerPacket();
            sendObject(packet);
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //daten zu server senden
    public void sendObject(Object packet){
        try{
            out.writeObject(packet);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            running = true;
            while(running){
                try{
                    Object data = in.readObject();
                    listener.received(data);
                } catch(ClassNotFoundException e){
                    e.printStackTrace();
                } catch(SocketException e){
                    e.printStackTrace();
                    close();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}