package sample;

public class Main {
    public ChatServer server = new ChatServer();
    public ChatClient client = new ChatClient();

    public static void main(String[] args) {
        if(args[0].toLowerCase().contains("help")){
            System.out.println("Usage: ChatNinja [<server ip address>]");
        }

        if(args.length == 0){
            server.start();
        } else if (args.length == 1){
            client.connect(args[0]);
        }
    }
}
