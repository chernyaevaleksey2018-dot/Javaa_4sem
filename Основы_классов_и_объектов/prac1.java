//Принцип Liskov Substitution Principle (LSP) звучит так: функции, которые используют ссылки на базовые классы, 
// должны иметь возможность использовать
// объекты производных классов, не зная об этом

interface Shape {
    int getArea();
}

class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}

// Использование
public class Main {
    public static void printArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());
    }

    public static void main(String[] args) {
        printArea(new Rectangle(10, 5));
        printArea(new Square(5));
    }
}
