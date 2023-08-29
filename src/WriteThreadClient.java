import util.NetworkUtil;

import java.util.Scanner;

public class WriteThreadClient extends Thread {
    private NetworkUtil networkUtil;
    private String clientName;

    public WriteThreadClient(NetworkUtil networkUtil, String clientName) {
        this.networkUtil = networkUtil;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String input = scanner.nextLine();

                    String[] parts = input.split(",", 2);
                    if (parts.length == 2) {
                        String receiver = parts[0];
                        String message = parts[1];
                        Message sendMessage = new Message(clientName, receiver, message);
                        networkUtil.write(sendMessage);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}