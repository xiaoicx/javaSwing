package com.cal.java;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @version v1.0
 * @description:
 * @author: xiaoqi
 * @create: 2020-06-08 16:49
 **/
public class Calculator {

    public static void createGUI() {
        cFrameMain calculator = new cFrameMain("计算器");
        calculator.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        calculator.setResizable(false); //不可手动调节
        calculator.setLocationRelativeTo(null); //在屏幕中央显示
        calculator.setSize(280, 350);
        calculator.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeAndWait(() -> Calculator.createGUI());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

/*
 *              <-
 * 7    9   9   /
 * 4    5   6   *
 * 1    2   3   +
 * .    0   -   =
 */



