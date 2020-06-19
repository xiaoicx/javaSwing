package com.xiaoqi.java;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;

/**
 * @version v1.0
 * @description: 界面布局类
 * @author: xiaoqi
 * @create: 2020-06-09 19:21
 **/
public class EditFrame extends JFrame {
    private JTextArea mainTextArea;//文本域
    private Font font = new Font("微软雅黑", Font.PLAIN, 12);//字体对象

    //卷曲面板
    private JScrollPane jScrollPane;

    //全局事件监听器
    private ActionMenuItemListener actionMenuItemListener = new ActionMenuItemListener();

    //我也不知道我为什么要定义全局指向,应为tm的内部类要使用父类
    private Component globalThis = this;

    //弹出菜单
    private JPopupMenu mainPopupMenu;



    public EditFrame(String title) throws HeadlessException {
        super(title);

        //获取布局容器
        Container mainContentPane = getContentPane();
        mainContentPane.setLayout(new BorderLayout());

        //创建菜单
        createMenuToMainFrame(mainContentPane);

        //创建文本域
        createTexTMain(mainContentPane);

        //创建弹出菜单
        createPopMenu(mainContentPane,mainTextArea);

    }

    /**
     * @Description 创建右键菜单
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-15 09:08
     * @param mainContentPan
 * @param mainTextArea
     * @return void
     */
    private void createPopMenu(Container mainContentPan,JTextArea mainTextArea){
        //创建菜单
        mainPopupMenu = new JPopupMenu();
        JMenuItem copy = createMenuItem("复制", "copy");
        JMenuItem paste = createMenuItem("粘贴", "paste");
        JMenuItem repalce = createMenuItem("查找替换", "repalce");
        JMenuItem cut = createMenuItem("剪切", "cut");

        mainPopupMenu.add(copy);
        mainPopupMenu.add(paste);
        mainPopupMenu.addSeparator();
        mainPopupMenu.add(repalce);
        mainPopupMenu.addSeparator();
        mainPopupMenu.add(cut);

        //添加事件
        copy.addActionListener(actionMenuItemListener);
        paste.addActionListener(actionMenuItemListener);
        repalce.addActionListener(actionMenuItemListener);
        cut.addActionListener(actionMenuItemListener);


        //添加菜单弹出事件
        mainTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //判断鼠标右键点击
                if (e.getButton() == MouseEvent.BUTTON3){
                    mainPopupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

    }

    /**
     * @param mainContentPan
     * @return void
     * @Description 创建文本域
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 21:34
     */
    private void createTexTMain(Container mainContentPan) {
        mainTextArea = new JTextArea();
        mainTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); //设置鼠标在文本域的样式
        mainTextArea.setLineWrap(true);//自动换行
        mainTextArea.setFont(font);//设置文本域全局字体
        mainTextArea.setDisabledTextColor(Color.BLACK);

        //使用JScrollPan才有滚动条
        jScrollPane = new JScrollPane();
        mainContentPan.add(jScrollPane, BorderLayout.CENTER);
        jScrollPane.setViewportView(mainTextArea);

//        mainContentPan.add(mainTextArea);
    }

    /**
     * @param mainContentPane
     * @return void
     * @Description 创建菜单
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 21:22
     */
    private void createMenuToMainFrame(Container mainContentPane) {

        //创建菜单
        JMenuBar mainMenuBar = new JMenuBar();
        setJMenuBar(mainMenuBar);

        //创建文件菜单
        JMenu fileMenu = new JMenu("文件(file)");
        mainMenuBar.add(fileMenu);

        //创建子菜单
        JMenuItem openFile = createMenuItem("打开     (open)", "open");
        JMenuItem saveFile = createMenuItem("保存     (save)", "save");
        JMenuItem savaAs = createMenuItem("另存为   (save as)", "saveAs");
        JMenuItem exit = createMenuItem("退出     (Exit)", "exit");

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(savaAs);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        //添加菜单事件
        openFile.addActionListener(actionMenuItemListener);
        saveFile.addActionListener(actionMenuItemListener);
        savaAs.addActionListener(actionMenuItemListener);
        exit.addActionListener(actionMenuItemListener);

        //创建编辑菜单
        JMenu editMrnu = new JMenu("编辑(edit)");
        JMenuItem copy = createMenuItem("复制(copy)", "copy");
        JMenuItem paste = createMenuItem("粘贴(paste)", "paste");
        JMenuItem repalce = createMenuItem("查找替换(replace)", "repalce");
        JMenuItem cut = createMenuItem("剪切(cut)", "cut");

        editMrnu.add(copy);
        editMrnu.add(paste);
        editMrnu.addSeparator();
        editMrnu.add(repalce);
        editMrnu.addSeparator();
        editMrnu.add(cut);

        mainMenuBar.add(editMrnu);

        //添加事件
        copy.addActionListener(actionMenuItemListener);
        paste.addActionListener(actionMenuItemListener);
        repalce.addActionListener(actionMenuItemListener);
        cut.addActionListener(actionMenuItemListener);



        //创建帮助菜单
        JMenu helpMenu = new JMenu("帮助(help)");
        mainMenuBar.add(helpMenu);

        //创建子菜单
        JMenuItem help = createMenuItem("帮助 (help)", "help");
        JMenuItem about = createMenuItem("关于 (about)", "about");

        helpMenu.add(help);
        helpMenu.addSeparator();
        helpMenu.add(about);

        //添加子菜单事件
        help.addActionListener(actionMenuItemListener);
        about.addActionListener(actionMenuItemListener);

    }

    /**
     * @param title
     * @param command
     * @return javax.swing.JMenuItem
     * @Description 创建子菜单项
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 21:22
     */
    private JMenuItem createMenuItem(String title, String command) {
        JMenuItem item = new JMenuItem(title);
        item.setActionCommand(command);
//        item.setFont(font);
        return item;
    }

    private class ActionMenuItemListener implements ActionListener {

        /**
         * @Description 事件分发
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-11 17:36
         * @param e
         * @return void
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //子菜单标识
            String actionCommand = e.getActionCommand();
            System.out.println(actionCommand);

            if (actionCommand.equals("save")){
                showSaveWindow(globalThis);
            }else if (actionCommand.equals("open")){
                showOpenWindow(globalThis);
            }else if (actionCommand.equals("saveAs")){
                showSaveWindow(globalThis);
            }else if (actionCommand.equals("exit")){
                System.exit(0);
            }else if (actionCommand.equals("help") || actionCommand.equals("about")){
                JOptionPane.showMessageDialog(globalThis,"author: xiaoqi\nblog: https://i7dom.cn\n","提示:", PLAIN_MESSAGE);
            }else if (actionCommand.equals("copy")){
                mainTextArea.copy();
            }else if (actionCommand.equals("paste")){
                mainTextArea.paste();
            }else if (actionCommand.equals("repalce")){

                HashMap<String, String> replaceStringHashMap = createReplaceD();
                if (null != replaceStringHashMap) {
                    String mainTextAreaText = mainTextArea.getText();
                    String replaceAllString = mainTextAreaText.replaceAll(replaceStringHashMap.get("src"), replaceStringHashMap.get("rep"));
                    mainTextArea.setText(replaceAllString);
                }
            }else if (actionCommand.equals("cut")){
                mainTextArea.cut();
            }
        }

        /**
         * @Description 弹出输入框获取替换字符
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-15 09:22
         * @param
         * @return java.lang.String
         */
        private HashMap<String, String> createReplaceD(){
            String inputValue = JOptionPane.showInputDialog(globalThis, "输入要替换的文本: 被替换的文本/替换文本", "提示:", PLAIN_MESSAGE);
            if ("".equals(inputValue) || inputValue.isEmpty()) return null;
            String[] split = inputValue.split("/");
            HashMap<String, String> repalceStringHashMap = new HashMap<>();
            if (split.length != 2){
                return null;
            }
            repalceStringHashMap.put("src",split[0]);
            repalceStringHashMap.put("rep",split[1]);
            return repalceStringHashMap;
        }


        /**
         * @Description 打开选着文件对话框
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-09 22:55
         * @param This
         * @return void
         */
        public void showOpenWindow(Component This){
            //创建文件选着框
            JFileChooser selectFileChooser = new JFileChooser();

            //创建文件过滤器并设置到文件选则框
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("文本文件", "txt", "java", "js", "html", "json", "xml", "ini", "conf","md");
            selectFileChooser.setFileFilter(fileNameExtensionFilter);

            //获取状态码
            int selectStatus = selectFileChooser.showOpenDialog(This);

            if (selectStatus == JFileChooser.APPROVE_OPTION){
                File selectedFile = selectFileChooser.getSelectedFile();
                //读入文件到全局文本域
                readFileToAreaText(selectedFile.getAbsolutePath(),mainTextArea);
            }

        }

        /**
         * @Description 打开保存文件对话框
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-09 22:50
         * @param This
         * @return void
         */
        private void showSaveWindow(Component This){

            //创建文件选着框
            JFileChooser saveFileChooser = new JFileChooser();

            //创建文件过滤器并设置到文件选则框
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("文本文件", "txt", "java", "js", "html", "json", "xml", "ini", "conf","md");
            saveFileChooser.setFileFilter(fileNameExtensionFilter);

            int openStatus = saveFileChooser.showSaveDialog(This);

            if (openStatus == JFileChooser.APPROVE_OPTION){
                //获取文件的绝对路径写出文件
                File selectedFile = saveFileChooser.getSelectedFile();
                writeFile(selectedFile.getAbsolutePath(),mainTextArea);
            }
        }

        /**
         * @Description 创建一个文件读入流,读入到全局文本域中
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-09 22:58
         * @param filePath
         * @param mainTextArea
         * @return void
         */
        public void readFileToAreaText(String filePath,JTextArea mainTextArea){

            BufferedReader bufferedReader = null;
            try {

                bufferedReader = new BufferedReader(new FileReader(filePath));

                char[] buffer = new char[1024];
                int len = 0;
                while ((len = bufferedReader.read(buffer)) != -1) {
                    String str = new String(buffer, 0, len);
                    mainTextArea.append(new String(str));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * @Description 创建写出文件流
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-09 22:48
         * @param filePath
         * @param mainTextArea
         * @return void
         */
        public void writeFile(String filePath,JTextArea mainTextArea) {
            BufferedOutputStream bufferedOutputStream = null;
            try {

                //创建一个OutputStream
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                String areaText = mainTextArea.getText();
                bufferedOutputStream.write(areaText.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedOutputStream != null){
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
