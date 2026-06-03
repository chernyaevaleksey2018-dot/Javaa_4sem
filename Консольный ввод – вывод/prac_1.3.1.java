import java.util.Scanner;

public class HelloApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите имя: ");
        String name = scan.next();
        System.out.println("Привет, " + name);
        scan.close();
    }
}
