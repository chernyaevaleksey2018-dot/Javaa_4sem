//Практика #1

public class PrimitiveTypes {
    public static void main(String[] args) {
        // 1. byte (8 бит): от -128 до 127
        byte b = 100;
        byte bZero = 0;
        // 2. short (16 бит): от -32 768 до 32 767
        short s = 30000;
        short sZero = 0;
        // 3. int (32 бита): основной тип для целых чисел
        int i = 2147483647;
        int iZero = 0;
        // 4. long (64 бита): добавляем суффикс L
        long l = 9223372036854775807L;
        long lZero = 0L;
        // 5. float (32 бита): точность около 7 знаков после запятой, обязателен суффикс f
        float f = 3.14f;
        float fZero = 0.0f;
        // 6. double (64 бита): точность около 15 знаков после запятой, основной для дробных
        double d = 3.1415926535;
        double dZero = 0.0d;
        // 7. char (16 бит): хранит один символ Unicode (в одинарных кавычках)
        char c = 'A';
        char cZero = '\u0000'; // Нулевой символ 
        // 8. boolean (true или false)
        boolean isTrue = true;
        boolean isFalse = false; // Нулевое значение для логического типа
        System.out.println("Int: " + i + ", Boolean: " + isFalse); //пример и его вывод
    }
}
