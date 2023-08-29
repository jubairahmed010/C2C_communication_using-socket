import java.util.ArrayList;
import java.util.HashMap;

public class ReadThreadServer extends Thread {
    private String clientName;
    private NetworkInformation networkInfo;
    private HashMap<String, NetworkInformation> clientNetworkInformationMap;

    public ReadThreadServer(String clientName, NetworkInformation networkInfo, HashMap<String, NetworkInformation> clientNetworkInformationMap) {
        this.clientName = clientName;
        this.networkInfo = networkInfo;
        this.clientNetworkInformationMap = clientNetworkInformationMap;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message receivedMessage = (Message) networkInfo.getNetworkUtil().read();
                String sender = receivedMessage.getFrom();
                String receiver = receivedMessage.getTo();
                String messageBody = receivedMessage.getText();

                if (receiver.equals("Server") && messageBody.equals("inbox")) {
                    ArrayList<String> inbox = networkInfo.getInbox();
                    StringBuilder inboxStringBuilder = new StringBuilder();
                    for (String item : inbox) {
                        if (inboxStringBuilder.length() > 0) {
                            inboxStringBuilder.append("~");
                        }
                        inboxStringBuilder.append(item);
                    }
                    String inboxString = inboxStringBuilder.toString();

                    Message inboxMessage = new Message("Server", clientName, inboxString);
                    networkInfo.getNetworkUtil().write(inboxMessage);
                } else {
                    NetworkInformation receiverInfo = clientNetworkInformationMap.get(receiver);
                    if (receiverInfo != null) {
                        receiverInfo.getInbox().add("From: " + sender + " Message: " + messageBody);
                        Message messageToSend = new Message(sender, receiver, messageBody);
                        receiverInfo.getNetworkUtil().write(messageToSend);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}