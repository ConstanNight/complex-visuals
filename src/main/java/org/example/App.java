package org.example;

public class App{
    private AppWindow window;

    public App(String title, int width, int height) {
        window = new AppWindow(title, width, height);
        window.buildWindow();
    }
}
