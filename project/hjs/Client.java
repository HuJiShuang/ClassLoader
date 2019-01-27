package edu.bit.hjs;

public class Client {
    public static void main(String[] args) throws Exception {
        FileSystemClassLoader fc1 = new FileSystemClassLoader("E:/FileSystemClassLoader");
        FileSystemClassLoader fc2 = new FileSystemClassLoader("E:/FileSystemClassLoader");
        Class<?> fcClass1 = fc1.loadClass("javatest.HelloWorld");
        Class<?> fcClass2 = fc2.loadClass("javatest.HelloWorld");
        System.out.println(fcClass1==fcClass2);
        System.out.println(fcClass1);
        System.out.println(fcClass1.hashCode());
        System.out.println(fcClass2.hashCode());

    }
}
