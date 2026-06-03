import java.io.*;
import java.util.*;

public class HuffmanApp {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Использование: java HuffmanApp <-c|-d> <входной файл> <выходной файл>");
            System.out.println("  -c : кодирование (compress)");
            System.out.println("  -d : декодирование (decompress)");
            return;
        }

        String mode = args[0];
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);

        try {
            long startTime = System.currentTimeMillis();
            if (mode.equals("-c")) {
                compress(inputFile, outputFile);
                System.out.println("Файл успешно закодирован за " + (System.currentTimeMillis() - startTime) + " мс");
            } else if (mode.equals("-d")) {
                decompress(inputFile, outputFile);
                System.out.println("Файл успешно декодирован за " + (System.currentTimeMillis() - startTime) + " мс");
            } else {
                System.out.println("Неизвестный флаг: " + mode);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлами: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Класс Узла Дерева 
    private static class Node implements Comparable<Node> {
        byte value;
        int frequency;
        Node left;
        Node right;

        // Конструктор для листьев
        Node(byte value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        // Конструктор для внутренних узлов
        Node(Node left, Node right) {
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }
        
        //проверка на лист
        boolean isLeaf() { 
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.frequency, o.frequency);
        }
    }

    // метод компрессии
    private static void compress(File src, File dest) throws IOException {
        byte[] inputData = readFileToByteArray(src);
        if (inputData.length == 0) {
            // пустой файл - записываем пустой архив
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(dest))) {
                out.writeInt(0); // 0 уникальных символов
                out.writeLong(0); // 0 байт данных
            }
            return;
        }

        // подсчет частот
        int[] frequencies = new int[256];
        for (byte b : inputData) {
            frequencies[b & 0xFF]++;
        }

        // построение дерева Хаффмана через приоритетную очередь
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int uniqueChars = 0;
        for (int i = 0; i < 256; i++) {
            if (frequencies[i] > 0) {
                pq.add(new Node((byte) i, frequencies[i]));
                uniqueChars++;
            }
        }

        // краевой случай: в файле только 1 уникальный символ 
        if (pq.size() == 1) {
            Node single = pq.poll();
            Node root = new Node((byte) 0, single.frequency);
            root.left = single; // искусственно делаем его левым потомком (код будет '0')
            pq.add(root);
        } else {
            while (pq.size() > 1) {
                Node left = pq.poll();
                Node right = pq.poll();
                pq.add(new Node(left, right));
            }
        }

        Node root = pq.peek();

        // генерация кодовой таблицы 
        Map<Byte, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);

        // запись в файл структуры данных
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(dest))) {
            out.writeInt(uniqueChars); // количество элементов в словаре
            
            // записываем таблицу частот (словарь)
            for (int i = 0; i < 256; i++) {
                if (frequencies[i] > 0) {
                    out.writeByte((byte) i);
                    out.writeInt(frequencies[i]);
                }
            }

            // Записываем количество исходных байт (для контроля конца декодирования)
            out.writeLong(inputData.length);

            // побитовая запись закодированных данных
            BitOutputStream bitOut = new BitOutputStream(out);
            for (byte b : inputData) {
                String code = huffmanCodes.get(b);
                for (char bit : code.toCharArray()) {
                    bitOut.writeBit(bit == '1' ? 1 : 0);
                }
            }
            bitOut.flush();
        }
    }

    // метод декомпрессии
    private static void decompress(File src, File dest) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(src));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {

            if (in.available() == 0) return; // Пустой файл

            int uniqueChars = in.readInt();
            if (uniqueChars == 0) return;

            // восстановление таблицы частот
            int[] frequencies = new int[256];
            for (int i = 0; i < uniqueChars; i++) {
                int b = in.readByte() & 0xFF;
                int freq = in.readInt();
                frequencies[b] = freq;
            }

            long totalBytesToDecode = in.readLong();

            // восстановление дерева Хаффмана 
            PriorityQueue<Node> pq = new PriorityQueue<>();
            for (int i = 0; i < 256; i++) {
                if (frequencies[i] > 0) {
                    pq.add(new Node((byte) i, frequencies[i]));
                }
            }

            if (pq.size() == 1) {
                Node single = pq.poll();
                Node root = new Node((byte) 0, single.frequency);
                root.left = single;
                pq.add(root);
            } else {
                while (pq.size() > 1) {
                    Node left = pq.poll();
                    Node right = pq.poll();
                    pq.add(new Node(left, right));
                }
            }

            Node root = pq.peek();

            // декодирование битового потока по дереву
            BitInputStream bitIn = new BitInputStream(in);
            Node current = root;
            long decodedBytesCount = 0;

            while (decodedBytesCount < totalBytesToDecode) {
                int bit = bitIn.readBit();
                if (bit == -1) {
                    throw new EOFException("Неожиданный конец файла при декодировании.");
                }

                current = (bit == 0) ? current.left : current.right;

                if (current.isLeaf()) {
                    out.write(current.value);
                    decodedBytesCount++;
                    current = root; // возвращаемся в корень для следующего символа
                }
            }
        }
    }

    // рекурсивный обход дерева для генерации путей 
    private static void generateCodes(Node node, String code, Map<Byte, String> huffmanCodes) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.value, code);
            return;
        }
        generateCodes(node.left, code + "0", huffmanCodes);
        generateCodes(node.right, code + "1", huffmanCodes);
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            int bytesRead = 0;
            while (bytesRead < data.length) {
                int read = fis.read(data, bytesRead, data.length - bytesRead);
                if (read == -1) break;
                bytesRead += read;
            }
            return data;
        }
    }

    // вспомогательные штуки
    private static class BitOutputStream {
        private final OutputStream out;
        private int currentByte = 0;
        private int numBits = 0;

        BitOutputStream(OutputStream out) {
            this.out = out;
        }

        void writeBit(int bit) throws IOException {
            currentByte = (currentByte << 1) | (bit & 1);
            numBits++;
            if (numBits == 8) {
                out.write(currentByte);
                currentByte = 0;
                numBits = 0;
            }
        }

        void flush() throws IOException {
            if (numBits > 0) {
                currentByte = currentByte << (8 - numBits); // Выравнивание последнего байта нулями вправо
                out.write(currentByte);
            }
        }
    }

    private static class BitInputStream {
        private final InputStream in;
        private int currentByte = 0;
        private int numBits = 0;

        BitInputStream(InputStream in) {
            this.in = in;
        }

        int readBit() throws IOException {
            if (numBits == 0) {
                currentByte = in.read();
                if (currentByte == -1) return -1; // EOF
                numBits = 8;
            }
            int bit = (currentByte >> (numBits - 1)) & 1;
            numBits--;
            return bit;
        }
    }
}

/*
тесты:
echo -n "1111111111" > test1.txt &&
java HuffmanApp -c test1.txt compressed1.huff &&
java HuffmanApp -d compressed1.huff restored1.txt
diff test1.txt restored1.txt


echo -n "11111111112222233333" > test2.txt &&
java HuffmanApp -c test2.txt compressed2.huff &&
java HuffmanApp -d compressed2.huff restored2.txt
diff test2.txt restored2.txt

java HuffmanApp -c HuffmanApp.class compressed_class.huff &&
java HuffmanApp -d compressed_class.huff restored_class.class
md5 HuffmanApp.class restored_class.class
*/
