package com.github.fahjulian.windoweventsystem.window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_6;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_7;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_8;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.windoweventsystem.event.EventDispatcher;
import com.github.fahjulian.windoweventsystem.event.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.windoweventsystem.event.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.windoweventsystem.event.mouse.MouseDraggedEvent;
import com.github.fahjulian.windoweventsystem.event.mouse.MouseMovedEvent;
import com.github.fahjulian.windoweventsystem.event.mouse.MouseScrolledEvent;

public class Mouse {

    private float x;
    private float y;
    
    private final Window window;
    private final boolean[] pressedButtons;

    public final EventDispatcher<MouseButtonPressedEvent> onMouseButtonPressed;
    public final EventDispatcher<MouseButtonReleasedEvent> onMouseButtonReleased;
    public final EventDispatcher<MouseMovedEvent> onMouseMoved;
    public final EventDispatcher<MouseDraggedEvent> onMouseDragged;
    public final EventDispatcher<MouseScrolledEvent> onMouseScrolled;

    Mouse(Window window) {
        this.window = window;

        this.pressedButtons = new boolean[Button.highestID + 1];
        this.onMouseButtonPressed = new EventDispatcher<>();
        this.onMouseButtonReleased = new EventDispatcher<>();
        this.onMouseMoved = new EventDispatcher<>();
        this.onMouseDragged = new EventDispatcher<>();
        this.onMouseScrolled = new EventDispatcher<>();
    }

    public boolean isButtonPressed(Button button) {
        return pressedButtons[button.ID];
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    void cursorPosCallback(long window, double posX, double posY) {
        posY = this.window.getHeight() - posY;

        float deltaX = (float) posX - this.x;
        float deltaY = (float) posY - this.y;
        this.x = (float) posX;
        this.y = (float) posY;

        for (int buttonID = 0; buttonID < pressedButtons.length; buttonID++) {
            if (pressedButtons[buttonID]) {
                Button button = Button.allButtons.get(buttonID);
                onMouseDragged.dispatch(new MouseDraggedEvent(button, this.x, this.y, deltaX, deltaY));
            }
        }

        onMouseMoved.dispatch(new MouseMovedEvent(this.x, this.y, deltaX, deltaY));
    }

    void scrollCallback(long window, double scrollX, double scrollY) {
        onMouseScrolled.dispatch(new MouseScrolledEvent((float) scrollX, (float) scrollY, this.x, this.y));
    }

    void buttonCallback(long window, int buttonID, int action, int mods) {
        if (action == GLFW_PRESS) {
            Button button = Button.allButtons.get(buttonID);
            pressedButtons[buttonID] = true;
            onMouseButtonPressed.dispatch(new MouseButtonPressedEvent(button, this.x, this.y));
        } else if (action == GLFW_RELEASE) {
            Button button = Button.allButtons.get(buttonID);
            pressedButtons[buttonID] = false;
            onMouseButtonReleased.dispatch(new MouseButtonReleasedEvent(button, this.x, this.y));
        }
    }

    public static enum Button {
        LEFT(GLFW_MOUSE_BUTTON_1),
        RIGHT(GLFW_MOUSE_BUTTON_2),
        MIDDLE(GLFW_MOUSE_BUTTON_3),
        BUTTON_4(GLFW_MOUSE_BUTTON_4),
        BUTTON_5(GLFW_MOUSE_BUTTON_5),
        BUTTON_6(GLFW_MOUSE_BUTTON_6),
        BUTTON_7(GLFW_MOUSE_BUTTON_7),
        BUTTON_8(GLFW_MOUSE_BUTTON_8);

        private final int ID;

        private static Map<Integer, Button> allButtons;
        private static int highestID;

        private Button(int ID) {
            this.ID = ID;

            Button.register(this);
        }

        private static void register(Button button) {
            if (allButtons == null) allButtons = new HashMap<>();
            allButtons.put(button.ID, button);
            if (button.ID > highestID) highestID = button.ID;
        }
    }
}
