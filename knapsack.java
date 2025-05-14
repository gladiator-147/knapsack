class Item {
    int value, weight;
    double ratio;

    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.ratio = (double) value / weight;
    }
}

class Node {
    int level, profit, weight;
    double bound;
}

public class knapsack {

    static void sortItems(Item[] items) {
        java.util.Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
    }

    static double getBound(Node node, int W, Item[] items) {
        if (node.weight >= W) return 0;

        double profit = node.profit;
        int weight = node.weight;
        int i = node.level + 1;

        while (i < items.length && weight + items[i].weight <= W) {
            weight += items[i].weight;
            profit += items[i].value;
            i++;
        }

        if (i < items.length)
            profit += (W - weight) * items[i].ratio;

        return profit;
    }

    static void knapsack(Item[] items, int W) {
        sortItems(items);
        java.util.Queue<Node> q = new java.util.LinkedList<>();

        Node root = new Node();
        root.level = -1;
        root.profit = 0;
        root.weight = 0;
        root.bound = getBound(root, W, items);
        q.add(root);

        int maxProfit = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.level == items.length - 1) continue;

            int i = node.level + 1;

            Node left = new Node();
            left.level = i;
            left.weight = node.weight + items[i].weight;
            left.profit = node.profit + items[i].value;
            left.bound = getBound(left, W, items);

            if (left.weight <= W && left.profit > maxProfit)
                maxProfit = left.profit;
            if (left.bound > maxProfit)
                q.add(left);

            Node right = new Node();
            right.level = i;
            right.weight = node.weight;
            right.profit = node.profit;
            right.bound = getBound(right, W, items);

            if (right.bound > maxProfit)
                q.add(right);
        }

        System.out.println("Maximum profit: " + maxProfit);
    }

    public static void main(String[] args) throws Exception {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter value and weight for item " + (i + 1) + ": ");
            items[i] = new Item(sc.nextInt(), sc.nextInt());
        }

        System.out.print("Enter knapsack capacity: ");
        int W = sc.nextInt();

        knapsack(items, W);
    }
}
