package com.github.manevolent.ts3j;

import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.client.ClientConnectionState;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import com.github.manevolent.ts3j.util.Ts3Debugging;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ServerConnectionTest  {
    public static void main(String[] args) throws Exception {
        Ts3Debugging.setEnabled(true);

        LocalIdentity identity = LocalIdentity.read(new File("ident.ini"));

        LocalTeamspeakClientSocket client = new LocalTeamspeakClientSocket();
        client.setIdentity(identity);
        client.setNickname(ServerConnectionTest.class.getSimpleName());
        client.setHWID("TestTestTest");

        try {
            client.connect(
                    "teamlixo.net",
                    10000L
            );
            client.getChannels().get().forEach(x -> System.err.println(x.getTopic()));

            assertEquals(client.getState(), ClientConnectionState.CONNECTED);

            client.setDescription("Heyo");

            client.subscribeAll();

            Thread.sleep(100000);

            client.disconnect("BYE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread.sleep(3000L);
    }
}
