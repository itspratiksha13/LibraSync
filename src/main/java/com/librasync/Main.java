package com.librasync;

public class Main {
    public static void main(String[] args) {
        new DatabaseInitializer().initialize();
        new ConsoleUI().start();
    }
}
