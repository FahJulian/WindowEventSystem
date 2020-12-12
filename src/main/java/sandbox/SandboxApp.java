package sandbox;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import com.github.fahjulian.windoweventsystem.window.Window;

public class SandboxApp {

    private final Window mainWindow;
    private final Window window2;
    private boolean running;

    protected SandboxApp(String title, int width, int height) {
        this.mainWindow = new Window(title, width, height);
        this.mainWindow.setTerminatesAppOnClose(true);
        this.mainWindow.setVisible(true);
        this.window2 = new Window("Window 2", 50, 50);
        this.window2.setVisible(true);
        this.init();
        this.run();
        this.destroy();
    }

    private void init() {
        Window.onApplicationTerminated.add((e) -> {
            System.out.println("Application terminated.");
            running = false;
        });

        mainWindow.mouse.onMouseMoved.add((e) -> System.out.printf("(Window1) Mouse moved to (%f, %f)%n", e.posX, e.posY));
        mainWindow.mouse.onMouseDragged.add((e) -> System.out.printf("(Window1) Mouse dragged with button %s to (%f, %f)%n", e.button, e.posX, e.posY));
        mainWindow.mouse.onMouseButtonPressed.add((e) -> System.out.printf("(Window1) Mouse button %s pressed at (%f, %f)%n", e.button, e.posX, e.posY));
        mainWindow.mouse.onMouseButtonReleased.add((e) -> System.out.printf("(Window1) Mouse button %s released at (%f, %f)%n", e.button, e.posX, e.posY));
        mainWindow.mouse.onMouseScrolled.add((e) -> System.out.printf("(Window1) Mouse scrolled by (%f, %f) at (%f, %f)%n", e.scrollX, e.scrollY, e.posX, e.posY));
        mainWindow.keyboard.onKeyPressed.add((e) -> System.out.printf("(Window1) Key %s pressed%n", e.key));
        mainWindow.keyboard.onKeyReleased.add((e) -> System.out.printf("(Window1) Key %s released%n", e.key));
        mainWindow.onWindowClosed.add((e) -> System.out.println("(Window1) Window closed."));
        mainWindow.onWindowResized.add((e) -> System.out.printf("(Window1) Window resized to (%f, %f)%n", e.width, e.height));

        window2.mouse.onMouseMoved.add((e) -> System.out.printf("(Window2) Mouse moved to (%f, %f)%n", e.posX, e.posY));
        window2.mouse.onMouseDragged.add((e) -> System.out.printf("(Window2) Mouse dragged with button %s to (%f, %f)%n", e.button, e.posX, e.posY));
        window2.mouse.onMouseButtonPressed.add((e) -> System.out.printf("(Window2) Mouse button %s pressed at (%f, %f)%n", e.button, e.posX, e.posY));
        window2.mouse.onMouseButtonReleased.add((e) -> System.out.printf("(Window2) Mouse button %s released at (%f, %f)%n", e.button, e.posX, e.posY));
        window2.mouse.onMouseScrolled.add((e) -> System.out.printf("(Window2) Mouse scrolled by (%f, %f) at (%f, %f)%n", e.scrollX, e.scrollY, e.posX, e.posY));
        window2.keyboard.onKeyPressed.add((e) -> System.out.printf("(Window2) Key %s pressed%n", e.key));
        window2.keyboard.onKeyReleased.add((e) -> System.out.printf("(Window2) Key %s released%n", e.key));
        window2.onWindowClosed.add((e) -> System.out.println("(Window2) Window closed."));
        window2.onWindowResized.add((e) -> System.out.printf("(Window2) Window resized to (%f, %f)%n", e.width, e.height));
    }

    private synchronized void run() {
        this.running = true;

        while (isRunning()) {
            if (!window2.isClosed()) {
                glfwMakeContextCurrent(window2.ID);
                glClear(GL_COLOR_BUFFER_BIT);
                glfwSwapBuffers(window2.ID);
            }
        }
    }

    private void destroy() {
        mainWindow.mouse.onMouseMoved.remove(this);
        mainWindow.mouse.onMouseDragged.remove(this);
        mainWindow.mouse.onMouseButtonPressed.remove(this);
        mainWindow.mouse.onMouseButtonReleased.remove(this);
        mainWindow.mouse.onMouseScrolled.remove(this);
        mainWindow.keyboard.onKeyPressed.remove(this);
        mainWindow.keyboard.onKeyReleased.remove(this);

        Window.terminateGLFW();
    }

    private boolean isRunning() {
        glfwPollEvents();
        return running;
    }

    public static void main(String... args) {
        new SandboxApp("SandboxApp", 500, 500);
    }
}
