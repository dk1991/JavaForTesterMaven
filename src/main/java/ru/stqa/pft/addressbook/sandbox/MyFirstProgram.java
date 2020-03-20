package ru.stqa.pft.addressbook.sandbox;

class MyFirstProgram {
	public static void main(String[] args) {
		hello("Oleg");
		hello("Bob");

		Square square = new Square(5);
		System.out.println("Area = " + square.area());

		Rectangle rectangle = new Rectangle(6,7);
		System.out.println("Area = " + rectangle.area());
	}

	public static void hello(String somebody) {
		System.out.println("Hello, " + somebody + "!");
	}
}	