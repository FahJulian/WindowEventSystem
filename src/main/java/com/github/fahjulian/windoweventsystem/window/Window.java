package com.github.fahjulian.windoweventsystem.window;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.windoweventsystem.util.Color;

import org.lwjgl.glfw.GLFWErrorCallback;

public final class Window {
    
    private final long ID;
    private final Mouse MOUSE;
    private final Keyboard KEYBOARD;

    private String title;
    private int width;
    private int height;

    private static final Map<Long, Window> allWindows;
    
    static {
        allWindows = new HashMap<>();
    }

    public Window(String title, int width, int height, Color clearColor) {
        if (allWindows.size() == 0) {   // glfw has not been initialized
            glfwSetErrorCallback((error, description) -> {
                System.out.printf("GLFW Error: %s%n", GLFWErrorCallback.getDescription(description));
            });
            if (!glfwInit()) {
                throw new IllegalStateException("Could not initialize GLFW");
            }
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        
        this.ID = glfwCreateWindow(width, height, title, NULL, NULL);
        this.width = width;
        this.height = height;
        this.MOUSE = new Mouse();
        this.KEYBOARD = new Keyboard();

        glfwMakeContextCurrent(this.ID);
        glfwSwapInterval(1);
        createCapabilities();
        glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        glfwShowWindow(this.ID);

        glfwSetCursorPosCallback(this.ID, this.MOUSE::cursorPosCallback);
        glfwSetMouseButtonCallback(this.ID, this.MOUSE::buttonCallback);
        glfwSetScrollCallback(this.ID, this.MOUSE::scrollCallback);
        glfwSetKeyCallback(this.ID, this.KEYBOARD::keyCallback);
        glfwSetWindowSizeCallback(this.ID, this::windowSizeCallback);
        glfwSetWindowPosCallback(this.ID, this::windowPosCallback);
        glfwSetWindowCloseCallback(this.ID, this::windowCloseCallback);

        allWindows.put(this.ID, this);
    }

    public void destroy() {
        glfwFreeCallbacks(this.ID);
        glfwDestroyWindow(this.ID);

        allWindows.remove(this.ID);
        if (allWindows.size() == 0) {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        glfwSetWindowSize(this.ID, width, height);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(this.ID, title);
    }

    public long getID() {
        return this.ID;
    }

    private void windowSizeCallback(long window, int width, int height) {
        
    }

    private void windowPosCallback(long window, int posX, int posY) {

    }

    private void windowCloseCallback(long window) {

    }

    public static Window getWindowByID(long id) {
        return allWindows.get(id);
    }
}
