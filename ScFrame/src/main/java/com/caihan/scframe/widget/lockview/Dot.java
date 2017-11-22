package com.caihan.scframe.widget.lockview;

import java.io.Serializable;

/**
 * 作者：caihan
 * 创建时间：2017/11/20
 * 邮箱：93234929@qq.com
 * 说明：
 * 画点
 */
public class Dot implements Serializable {

    //圆心坐标
    private float x;
    private float y;

    //当前状态,是否选中
    private boolean isSelected;

    //下标
    private int index;

    public Dot(float x, float y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
