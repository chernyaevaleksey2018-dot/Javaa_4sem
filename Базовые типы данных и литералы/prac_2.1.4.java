//Практика #4

public class StringConcat {
    public static void main(String[] args) {
        // 1) Соединение с базовыми типами
        int age = 20;
        double score = 4.8;
        boolean isScoreNorm = true;
       
        String info = "Возраст: " + age + ", Балл: " + score + ", Нормальный балл? " + isScoreNorm;
        System.out.println(info);
        // 2) Соединение с объектами
        Object obj = new Object();
        String objectInfo = "Мой объект: " + obj; // Выведет имя класса и хэш-код
        System.out.println(objectInfo);

        // 3) порядок вычислений слева направо
        System.out.println("Результат: " + 5 + 10);   // Выведет "Результат: 510" (склеивание)
        System.out.println("Результат: " + (5 + 10)); // Выведет "Результат: 15" (сначала сложение)
    }
}
