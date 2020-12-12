package com.github.fahjulian.windoweventsystem.window;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
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

import com.github.fahjulian.windoweventsystem.event.EventDispatcher;
import com.github.fahjulian.windoweventsystem.event.window.ApplicationTerminatedEvent;
import com.github.fahjulian.windoweventsystem.event.window.WindowClosedEvent;
import com.github.fahjulian.windoweventsystem.event.window.WindowResizedEvent;

import org.lwjgl.glfw.GLFWErrorCallback;

public final class Window {
    
    public final long ID;
    public final Mouse mouse;
    public final Keyboard keyboard;
    public final EventDispatcher<WindowClosedEvent> onWindowClosed;
    public final EventDispatcher<WindowResizedEvent> onWindowResized;

    private String title;
    private int width;
    private int height;
    private boolean terminatesAppOnClose;
    private boolean visible;
    private boolean closed;

    public static EventDispatcher<ApplicationTerminatedEvent> onApplicationTerminated;

    private static final Map<Long, Window> allWindows;
    
    static {
        allWindows = new HashMap<>();
        onApplicationTerminated = new EventDispatcher<>();
    }

    public Window(String title, int width, int height) {
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        
        this.ID = glfwCreateWindow(width, height, title, NULL, NULL);
        this.width = width;
        this.height = height;
        this.mouse = new Mouse(this);
        this.keyboard = new Keyboard();
        this.onWindowClosed = new EventDispatcher<>();
        this.onWindowResized = new EventDispatcher<>();

        glfwMakeContextCurrent(this.ID);
        glfwSwapInterval(1);
        createCapabilities();
        glClearColor(0.2f, 0.2f, 0.2f, 0.2f);

        glfwSetCursorPosCallback(this.ID, this.mouse::cursorPosCallback);
        glfwSetMouseButtonCallback(this.ID, this.mouse::buttonCallback);
        glfwSetScrollCallback(this.ID, this.mouse::scrollCallback);
        glfwSetKeyCallback(this.ID, this.keyboard::keyCallback);
        glfwSetWindowSizeCallback(this.ID, this::windowSizeCallback);
        glfwSetWindowCloseCallback(this.ID, this::windowCloseCallback);

        allWindows.put(this.ID, this);
    }

    public void destroy() {
        glfwFreeCallbacks(this.ID);
        glfwDestroyWindow(this.ID);
        allWindows.remove(this.ID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(int width) {
        glfwSetWindowSize(this.ID, width, this.height);
    }

    public void setHeight(int height) {
        glfwSetWindowSize(this.ID, this.width, height);
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

    public void setTerminatesAppOnClose(boolean terminatesAppOnClose) {
        this.terminatesAppOnClose = terminatesAppOnClose;
    }

    public void setClearColor(float r, float g, float b, float a) {
        glfwMakeContextCurrent(this.ID);
        glClearColor(r, g, b, a);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setVisible(boolean b) {
        if (b && !this.visible)
            glfwShowWindow(this.ID);
        if (!b && this.visible)
            glfwHideWindow(this.ID);
        this.visible = b;
    }

    public static void terminateGLFW() {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void windowSizeCallback(long window, int width, int height) {
        this.onWindowResized.dispatch(new WindowResizedEvent(width, height));
    }

    private void windowCloseCallback(long window) {
        this.onWindowClosed.dispatch(new WindowClosedEvent());
        this.closed = true;
        this.destroy();
        
        if (this.terminatesAppOnClose || allWindows.size() == 0) {
            Window.onApplicationTerminated.dispatch(new ApplicationTerminatedEvent());
        }
    }
}
