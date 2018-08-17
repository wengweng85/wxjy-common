package com.insigma.weixin.menu;

/**
 * 表示菜单类的基类，一级菜单和二级菜单的Entity都继承此Entity
 * @author xujiyu
 *
 */
public class Button {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
