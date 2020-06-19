package com.xiaoqi.java;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @version v1.0 JEdit by:xiaoqi
 * @description:
 * @author: xiaoqi
 * @create: 2020-06-09 19:20
 **/
public class JEditMain {
    public static void createGUI() {
        EditFrame jEdit = new EditFrame("JEdit by:xiaoqi");
        jEdit.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jEdit.setSize(750, 450);
        jEdit.setLocationRelativeTo(null);
        jEdit.setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //直接使用方法引用少了行代码
            SwingUtilities.invokeAndWait(JEditMain::createGUI);

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
