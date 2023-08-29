import util.NetworkUtil;

public class ReadThreadClient extends Thread {
    private NetworkUtil networkUtil;
    private String clientName;

    public ReadThreadClient(NetworkUtil networkUtil, String clientName) {
        this.networkUtil = networkUtil;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message receivedMessage = (Message) networkUtil.read();
                String sender = receivedMessage.getFrom();
                String messageBody = receivedMessage.getText();

                if (sender.equals("Server")) {
                    String[] messages = messageBody.split("~");
                    System.out.println("Your Inbox:");
                    for (String message : messages) {
                        System.out.println(message);
                    }
                } else {
                    System.out.println("From: " + sender + " Message: " + messageBody);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
