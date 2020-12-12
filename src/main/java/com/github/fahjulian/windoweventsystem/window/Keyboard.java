package com.github.fahjulian.windoweventsystem.window;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.windoweventsystem.event.EventDispatcher;
import com.github.fahjulian.windoweventsystem.event.key.KeyPressedEvent;
import com.github.fahjulian.windoweventsystem.event.key.KeyReleasedEvent;

public class Keyboard {
    
    private final boolean[] pressedKeys;
    public final EventDispatcher<KeyPressedEvent> onKeyPressed;
    public final EventDispatcher<KeyReleasedEvent> onKeyReleased;

    Keyboard() {
        this.pressedKeys = new boolean[Key.highestID + 1];
        this.onKeyPressed = new EventDispatcher<>();
        this.onKeyReleased = new EventDispatcher<>();
    }

    public boolean isKeyPressed(Key key) {
        return pressedKeys[key.ID];
    }

    void keyCallback(long window, int keyID, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            Key key = Key.allKeys.get(keyID);
            pressedKeys[keyID] = true;
            onKeyPressed.dispatch(new KeyPressedEvent(key));
        } else if (action == GLFW_RELEASE) {
            Key key = Key.allKeys.get(keyID);
            pressedKeys[keyID] = false;
            onKeyReleased.dispatch(new KeyReleasedEvent(key));
        }
    }

    public static enum Key {
        A(GLFW_KEY_A),
        B(GLFW_KEY_B),
        C(GLFW_KEY_C),
        D(GLFW_KEY_D),
        E(GLFW_KEY_E),
        F(GLFW_KEY_F),
        G(GLFW_KEY_G),
        H(GLFW_KEY_H),
        I(GLFW_KEY_I),
        J(GLFW_KEY_J),
        K(GLFW_KEY_K),
        L(GLFW_KEY_L),
        M(GLFW_KEY_M),
        N(GLFW_KEY_N),
        O(GLFW_KEY_O),
        P(GLFW_KEY_P),
        Q(GLFW_KEY_Q),
        R(GLFW_KEY_R),
        S(GLFW_KEY_S),
        T(GLFW_KEY_T),
        U(GLFW_KEY_U),
        V(GLFW_KEY_V),
        W(GLFW_KEY_W),
        X(GLFW_KEY_X),
        Y(GLFW_KEY_Y),
        Z(GLFW_KEY_Z),
        _0(GLFW_KEY_0),
        _1(GLFW_KEY_1),
        _2(GLFW_KEY_2),
        _3(GLFW_KEY_3),
        _4(GLFW_KEY_4),
        _5(GLFW_KEY_5),
        _6(GLFW_KEY_6),
        _7(GLFW_KEY_7),
        _8(GLFW_KEY_8),
        _9(GLFW_KEY_9),
        F1(GLFW_KEY_F1),
        F2(GLFW_KEY_F2),
        F3(GLFW_KEY_F3),
        F4(GLFW_KEY_F4),
        F5(GLFW_KEY_F5),
        F6(GLFW_KEY_F6),
        F7(GLFW_KEY_F7),
        F8(GLFW_KEY_F8),
        F9(GLFW_KEY_F9),
        F10(GLFW_KEY_F10),
        F11(GLFW_KEY_F11),
        F12(GLFW_KEY_F12),
        F13(GLFW_KEY_F13),
        F14(GLFW_KEY_F14),
        F15(GLFW_KEY_F15),
        F16(GLFW_KEY_F16),
        F17(GLFW_KEY_F17),
        F18(GLFW_KEY_F18),
        F19(GLFW_KEY_F19),
        F20(GLFW_KEY_F20),
        F21(GLFW_KEY_F21),
        F22(GLFW_KEY_F22),
        F23(GLFW_KEY_F23),
        F24(GLFW_KEY_F24),
        F25(GLFW_KEY_F25),
        NUMPAD_0(GLFW_KEY_KP_0),
        NUMPAD_1(GLFW_KEY_KP_1),
        NUMPAD_2(GLFW_KEY_KP_2),
        NUMPAD_3(GLFW_KEY_KP_3),
        NUMPAD_4(GLFW_KEY_KP_4),
        NUMPAD_5(GLFW_KEY_KP_5),
        NUMPAD_6(GLFW_KEY_KP_6),
        NUMPAD_7(GLFW_KEY_KP_7),
        NUMPAD_8(GLFW_KEY_KP_8),
        NUMPAD_9(GLFW_KEY_KP_9),
        NUMPAD_DIVIDE(GLFW_KEY_KP_DIVIDE),
        NUMPAD_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
        NUMPAD_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
        NUMPAD_ADD(GLFW_KEY_KP_ADD),
        NUMPAD_ENTER(GLFW_KEY_KP_ENTER),
        NUMPAD_DECIMAL(GLFW_KEY_KP_DECIMAL),
        NUMPAD_EQUAL_SIGN(GLFW_KEY_KP_EQUAL),
        SPACE(GLFW_KEY_SPACE),
        TAB(GLFW_KEY_TAB),
        UP(GLFW_KEY_UP),
        LEFT(GLFW_KEY_LEFT),
        DOWN(GLFW_KEY_DOWN),
        RIGHT(GLFW_KEY_RIGHT),
        CONTROL_LEFT(GLFW_KEY_LEFT_CONTROL),
        CONTROL_RIGHT(GLFW_KEY_RIGHT_CONTROL),
        SHIFT_LEFT(GLFW_KEY_LEFT_SHIFT),
        SHIFT_RIGHT(GLFW_KEY_RIGHT_SHIFT),
        ALT_LEFT(GLFW_KEY_LEFT_ALT),
        ALT_RIGHT(GLFW_KEY_RIGHT_ALT),
        SUPER_LEFT(GLFW_KEY_LEFT_SUPER),
        SUPER_RIGHT(GLFW_KEY_RIGHT_SUPER),
        CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
        ESCAPE(GLFW_KEY_ESCAPE),
        BACKSPACE(GLFW_KEY_BACKSPACE),
        ENTER(GLFW_KEY_ENTER),
        COMMA(GLFW_KEY_COMMA),
        PERIOD(GLFW_KEY_PERIOD),
        LESS_THAN(GLFW_KEY_WORLD_1),
        MENU(GLFW_KEY_MENU),
        SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
        PAUSE(GLFW_KEY_PAUSE),
        INSERT(GLFW_KEY_INSERT),
        DELETE(GLFW_KEY_DELETE),
        END(GLFW_KEY_END),
        PAGE_UP(GLFW_KEY_PAGE_UP),
        PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
        NUM_LOCK(GLFW_KEY_NUM_LOCK),
        PRINT(GLFW_KEY_PRINT_SCREEN),
        QWERTZ_Ä(GLFW_KEY_APOSTROPHE),
        QWERTY_APOSTROPHE(GLFW_KEY_APOSTROPHE),
        QWERTZ_Ö(GLFW_KEY_SEMICOLON),
        QWERTY_SEMICOLON(GLFW_KEY_SEMICOLON),
        QWERTZ_Ü(GLFW_KEY_LEFT_BRACKET),
        QWERTY_BRACKET_LEFT(GLFW_KEY_LEFT_BRACKET),
        QWERTZ_PLUS(GLFW_KEY_RIGHT_BRACKET),
        QWERTY_BRACKET_RIGHT(GLFW_KEY_RIGHT_BRACKET),
        QWERTZ_HASHTAG(GLFW_KEY_BACKSLASH),
        QWERTY_BACKSLASH(GLFW_KEY_BACKSLASH),
        QWERTZ_ß(GLFW_KEY_MINUS),
        QWERTY_MINUS(GLFW_KEY_MINUS),
        QWERTZ_ACCENT_RIGHT(GLFW_KEY_EQUAL),
        QWERTY_EQUAL_SIGN(GLFW_KEY_EQUAL),
        QWERTZ_CIRCUMFLEX(GLFW_KEY_GRAVE_ACCENT),
        QWERTY_ACCENT_LEFT(GLFW_KEY_GRAVE_ACCENT),
        QWERTZ_MINUS(GLFW_KEY_SLASH),
        QWERTY_SLASH(GLFW_KEY_SLASH),
        QWERTZ_POS_1(GLFW_KEY_HOME),
        QWERTY_HOME(GLFW_KEY_HOME),
        UNKOWN(GLFW_KEY_UNKNOWN);

        private final int ID;

        private static Map<Integer, Key> allKeys;
        private static int highestID;

        private Key(int ID) {
            this.ID = ID;

            Key.register(this);
        }

        private static void register(Key key) {
            if (allKeys == null) allKeys = new HashMap<>();
            allKeys.put(key.ID, key);
            if (key.ID > highestID) highestID = key.ID;
        }
    }
}
