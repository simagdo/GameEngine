package de.simagdo.gameEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Game {

    private long window;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public void run() {
        GLFWErrorCallback.createPrint(System.err).set();
        GLFW.glfwInit();
        this.window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Engine", 0, 0);
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(this.window, (vidMode.width() / 2 - (WIDTH / 2)), (vidMode.height() / 2 - (HEIGHT / 2)));
        GLFW.glfwMakeContextCurrent(this.window);
        GLFW.glfwSwapInterval(1);

        while (!GLFW.glfwWindowShouldClose(this.window)) {
            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();
        }

        //Exit
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();

    }

}
