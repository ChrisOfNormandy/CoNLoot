package com.github.chrisofnormandy.conloot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public Client() {

    }

    public static void Send(String message) throws IOException {
        Socket socket = new Socket("localhost", 25580);
        
        OutputStream output = socket.getOutputStream();

        DataOutputStream dataOutput = new DataOutputStream(output);

        dataOutput.writeUTF(message);
        dataOutput.flush();
        dataOutput.close();

        socket.close();
    }
}
