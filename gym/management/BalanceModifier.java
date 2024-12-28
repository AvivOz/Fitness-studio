package gym.management;

public class BalanceModifier {

    private double balance;

    public BalanceModifier(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void modifyBalance(double balance) {
        this.balance += balance;
    }
}
