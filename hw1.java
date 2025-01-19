import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;

// Sunucuyu temsil eden sınıf
class Server implements Comparable<Server> {
    int id;
    int activeRequests;

    public Server(int id, int activeRequests) {
        this.id = id;
        this.activeRequests = activeRequests;
    }

    @Override
    public int compareTo(Server other) {
        return Integer.compare(this.activeRequests, other.activeRequests);
    }

    @Override
    public String toString() {
        return "Server{id=" + id + ", activeRequests=" + activeRequests + '}';
    }
}

// Yük dengeleyici sınıfı
class LoadBalancer {
    private PriorityQueue<Server> servers;

    public LoadBalancer(int numServers) {
        servers = new PriorityQueue<>();
        for (int i = 0; i < numServers; i++) {
            servers.add(new Server(i, 0));
        }
    }

    // İşlem talebi atama
    public void assignRequest() {
        Server leastBusy = servers.poll();
        if (leastBusy != null) {
            leastBusy.activeRequests++;
            servers.add(leastBusy);
            System.out.println("Assigned request to " + leastBusy);
        }
    }

    // İşlem tamamlandıktan sonra güncelleme
    public void completeRequest(int serverId) {
        for (Server server : servers) {
            if (server.id == serverId) {
                servers.remove(server);
                server.activeRequests--;
                servers.add(server);
                System.out.println("Completed request on Server " + serverId);
                break;
            }
        }
    }

    public void displayServers() {
        System.out.println("Server states: " + servers);
    }
}

// Öncelikli yük dengeleyici sınıfı
class PriorityLoadBalancer extends LoadBalancer {
    private Queue<Integer> highPriorityQueue;

    public PriorityLoadBalancer(int numServers) {
        super(numServers);
        highPriorityQueue = new LinkedList<>();
    }

    public void addHighPriorityRequest(int requestId) {
        highPriorityQueue.add(requestId);
        System.out.println("Added high-priority request: " + requestId);
    }

    @Override
    public void assignRequest() {
        if (!highPriorityQueue.isEmpty()) {
            int highPriorityRequest = highPriorityQueue.poll();
            System.out.println("Processing high-priority request: " + highPriorityRequest);
        } else {
            super.assignRequest();
        }
    }
}

// Ana sınıf (main metodu içerir)
public class Main {
    public static void main(String[] args) {
        // Normal yük dengeleyiciyi test et
        System.out.println("Testing LoadBalancer...");
        LoadBalancer loadBalancer = new LoadBalancer(3);
        loadBalancer.assignRequest();
        loadBalancer.assignRequest();
        loadBalancer.completeRequest(0);
        loadBalancer.displayServers();

        // Öncelikli yük dengeleyiciyi test et
        System.out.println("\nTesting PriorityLoadBalancer...");
        PriorityLoadBalancer priorityLoadBalancer = new PriorityLoadBalancer(3);
        priorityLoadBalancer.addHighPriorityRequest(101);
        priorityLoadBalancer.addHighPriorityRequest(102);
        priorityLoadBalancer.assignRequest();
        priorityLoadBalancer.assignRequest();
        priorityLoadBalancer.displayServers();
    }
}
