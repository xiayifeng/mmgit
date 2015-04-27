package com.self.tools;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class SelectorUtils {

    public static Drawable getValidateCodeDrawable(Drawable unclick,Drawable click){

        int pressed = android.R.attr.state_pressed;
        int window_focused = android.R.attr.state_window_focused;
        int focused = android.R.attr.state_focused;
        int selected = android.R.attr.state_selected;

        int enable = android.R.attr.enabled;

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{pressed,0x101009e}, click);
        stateListDrawable.addState(new int[]{0x101009e}, click);
        stateListDrawable.addState(StateSet.WILD_CARD, unclick);

        return stateListDrawable;
    }

    public static Drawable getButtonDrawable(Drawable unclick,Drawable click){

        int pressed = android.R.attr.state_pressed;
        int window_focused = android.R.attr.state_window_focused;
        int focused = android.R.attr.state_focused;
        int selected = android.R.attr.state_selected;

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{pressed}, click);
        stateListDrawable.addState(StateSet.WILD_CARD, unclick);

        return stateListDrawable;
    }

    public static Drawable getEditTextDrawable(Drawable defaulfDrawable,Drawable foucesDrawable){
        int focused = android.R.attr.state_focused;

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{focused}, foucesDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, defaulfDrawable);

        return stateListDrawable;
    }

}
