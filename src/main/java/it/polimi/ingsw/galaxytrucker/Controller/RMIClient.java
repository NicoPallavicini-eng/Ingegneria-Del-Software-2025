import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class RMIClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    public RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    public void run() throws RemoteException {
        this.server.connect(this);
        this.runCl();
    }
    private void runCl(){
        Scanner scanner = new Scanner(System.in);
        String input = new String("0");
        while(!input.equals("exit")){
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            System.out.println("Server command: " + input);
            try{
                server.showMessage(input);
            }catch(RemoteException e){
                e.printStackTrace();
            }
        }
        System.out.println("Exited Loop");
        System.exit(0);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "AdderServer";

        Registry registry = LocateRegistry.getRegistry("localhost", 1090);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RMIClient(server).run();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
    //
}
