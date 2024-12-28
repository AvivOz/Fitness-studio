package gym.customers;

import gym.management.BalanceModifier;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Person {

    private static int idCounter = 1111;
    private final int id;
    private String name;
    private BalanceModifier balance;
    private Gender gender;
    private String birthDate;

    public Person(Person person) {
        this.name = person.name;
        this.balance = person.balance;
        this.gender = person.gender;
        this.birthDate = person.birthDate;
        this.id = person.id;
    }

    public Person(String name, double balance, Gender gender, String birthDate) {
        this.id = idCounter++;
        this.name = name;
        this.balance = new BalanceModifier(balance);
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance.getBalance();
    }

    public Gender getGender() {
        return gender;
    }

    public String getBirthDate(){
        return birthDate;
    }

    public int getAge() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDateParsed = LocalDate.parse(birthDate, formatter);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDateParsed, currentDate).getYears();
    }

    public void setBalance(double balance) {
        this.balance.setBalance(balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Person other = (Person) obj;
        return name.equals(other.name) &&
                gender == other.gender &&
                birthDate.equals(other.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, birthDate);
    }

}
