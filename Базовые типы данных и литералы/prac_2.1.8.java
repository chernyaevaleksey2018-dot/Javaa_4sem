
// Практика #8
//Правильный термин — Type Inference (выведение типов). В Java эта возможность появилась в версии 10. Она позволяет опустить явное указание типа 
//переменной, если компилятор может сам догадаться, какой тип данных там находится, глядя на правую часть выражения (значение).

public class Main {
    public static void main(String[] args) 
    {
        var name = "Java";       // Вместо String name = "Java";
        var version = 17;        // Вместо int version = 17;
        var list = new ArrayList<String>();         // Вместо List<String> list = new ArrayList<>();

        System.out.println("Язык: " + name + ", Версия: " + version);
    }
}
