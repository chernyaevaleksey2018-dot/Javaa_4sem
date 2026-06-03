
// Практика #6
// 6.1 Число не округляется по правилам математики, а просто обрезается. Все, что после запятой, пропадает.

double pi = 3.14159;
int integerPi = (int) pi; 

System.out.println(integerPi); // Выведет 3

// 6.2 Поскольку 3 миллиарда не влезают в 32 бита int, Java отсекает старшие биты. На выходе получается совершенно другое (часто отрицательное) число.

long bigNumber = 3_000_000_000L;
int narrowNumber = (int) bigNumber;

System.out.println(narrowNumber); // Выведет -1294967296

// 6.3  Число преобразуется в символ, соответствующий этому коду в таблице

int code = 65;
char letter = (char) code;

System.out.println(letter); // Выведет 'A'
