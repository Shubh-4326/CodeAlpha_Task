import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

class Transaction {
    String type; // BUY or SELL
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }
}

class User {
    String name;
    double balance;
    Map<String, Integer> portfolio = new HashMap<>();
    List<Transaction> history = new ArrayList<>();

    User(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }

    void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
            history.add(new Transaction("BUY", stock.symbol, quantity, stock.price));
            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough balance to complete purchase.");
        }
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (owned >= quantity) {
            balance += stock.price * quantity;
            portfolio.put(stock.symbol, owned - quantity);
            history.add(new Transaction("SELL", stock.symbol, quantity, stock.price));
            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n=== Portfolio for " + name + " ===");
        System.out.printf("Cash Balance: $%.2f\n", balance);
        double totalValue = balance;

        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            if (qty > 0) {
                Stock stock = market.get(symbol);
                double value = qty * stock.price;
                totalValue += value;
                System.out.printf("%s (%s): %d shares, Value: $%.2f\n", stock.name, stock.symbol, qty, value);
            }
        }

        System.out.printf("Total Portfolio Value: $%.2f\n", totalValue);
    }

    void showTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        for (Transaction t : history) {
            System.out.printf("%s %d x %s @ $%.2f\n", t.type, t.quantity, t.stockSymbol, t.price);
        }
    }
}

public class StockTradingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample market data
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 175.00));
        market.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2800.00));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 750.00));

        // User creation
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name, 10000); // $10,000 starting balance

        int choice;
        do {
            System.out.println("\n=== Stock Trading Menu ===");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock stock : market.values()) {
                        System.out.printf("%s (%s): $%.2f\n", stock.name, stock.symbol, stock.price);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQty = scanner.nextInt();
                    scanner.nextLine();
                    if (market.containsKey(buySymbol)) {
                        user.buyStock(market.get(buySymbol), buyQty);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQty = scanner.nextInt();
                    scanner.nextLine();
                    if (market.containsKey(sellSymbol)) {
                        user.sellStock(market.get(sellSymbol), sellQty);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 4:
                    user.showPortfolio(market);
                    break;
                case 5:
                    user.showTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for trading!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}

output :PS D:\Programming\java_pra> cd "d:\Programming\java_pra\" ; if ($?) { javac StockTradingApp.java } ; if ($?) { java StockTradingApp }
Enter your name: Shubh

=== Stock Trading Menu ===
1. View Market Data
2. Buy Stock
3. Sell Stock
4. View Portfolio
5. Transaction History
6. Exit
Enter choice: 4

=== Portfolio for Shubh ===
Cash Balance: $10000.00
Total Portfolio Value: $10000.00

=== Stock Trading Menu ===
1. View Market Data
2. Buy Stock
3. Sell Stock
4. View Portfolio
5. Transaction History
6. Exit
Enter choice: 6
Thank you for trading!
PS D:\Programming\java_pra> 
