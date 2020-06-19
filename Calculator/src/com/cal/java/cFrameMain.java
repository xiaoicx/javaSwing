package com.cal.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @version v1.0
 *
 * @description: 界面实现
 * @author: xiaoqi
 * @create: 2020-06-08 16:59
 **/
public class cFrameMain extends JFrame {
    private JTextField showJTextContent; //计算器显示器
    private Font font; //全局字体
    private JButton[] numberButtons = new JButton[16];  //控制按钮
    private BtnActionListener btnActionListener = new BtnActionListener(); //全局事件监听器

    //计算数字与运算符
    private ArrayList<String> clickNumberArray = new ArrayList<>();
    private ArrayList<String> symbols = new ArrayList<>();

    //默认计算标识为 !:标识无运算 =:标识有运算
    private String actionFlage = "!";

    //判断是否按下过数字防止多次叠加运算符
    private boolean isactionFlage = false;


    public cFrameMain(String title) {
        super(title);

        //主界面使用边界布局,上下两行 PAGE_START CENTER
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(1, 5));

        //创建两个容器且设置容器布局方式
        JPanel showPanel = new JPanel();
        JPanel clickPanel = new JPanel();
        showPanel.setLayout(new BorderLayout());//第一个容器使用边界布局
        clickPanel.setLayout(new GridLayout(4, 4, 5, 5));//第二个容器使用网格布局

        //创建字体对象
        font = new Font("Times Roman", Font.BOLD, 20);

        //主容器添加布局容器
        contentPane.add(BorderLayout.NORTH, showPanel);
        contentPane.add(BorderLayout.CENTER, clickPanel);

        //创建显示器
        this.createShowTextPanle(showPanel);
        this.createButtonC(showPanel);

        //创建按钮
        this.createNumberBtn(clickPanel);

    }

    /**
     * @param clickPanel
     * @return void
     * @Description 创建按钮控件
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 16:41
     */
    private void createNumberBtn(JPanel clickPanel) {
        String[] clickStr = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "+", ".", "0", "-", "="};
        for (int i = 0; i < this.numberButtons.length; i++) {
            numberButtons[i] = new JButton(clickStr[i]);
            numberButtons[i].setFont(font);
            numberButtons[i].setActionCommand(clickStr[i]); //设置按钮标识
            clickPanel.add(numberButtons[i]); //添加到按钮容器区

            //添加按钮事件
            numberButtons[i].addActionListener(this.btnActionListener);
        }
    }

    /**
     * @Description 创建清除按钮
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 19:13
     * @param showPanel
     * @return void
     */
    private void createButtonC(JPanel showPanel) {
        JButton btnClear = new JButton("C");
        btnClear.setFont(font);
        btnClear.setActionCommand("C"); //添加标识
        showPanel.add(BorderLayout.EAST, btnClear);

        //添加监听事件
        btnClear.addActionListener(this.btnActionListener);
    }

    /**
     * @param showPanel
     * @return void
     * @Description 创建显示器控件
     * @author xiaoqi
     * @Email 1846627226@qq.com
     * @date 2020-06-09 15:39
     */
    private void createShowTextPanle(JPanel showPanel) {
        showJTextContent = new JTextField();
        showJTextContent.setFont(font);
        //设置不可修改
        showJTextContent.setEnabled(false);
        showJTextContent.setPreferredSize(new Dimension(0, 50)); //设置showJTextContent空间高度.宽度自适应
        showPanel.add(BorderLayout.CENTER, showJTextContent);//设置为中心对齐
    }

    //内部类实现事件监听器
    private class BtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();

            if (actionCommand.equals("C")) { //判断按钮标识是不是C,是则清除
                this.clearShowJTextContent(showJTextContent);

                //计算标识不存在
                actionFlage = "!";

            } else if ("1234567890.".indexOf(actionCommand) != -1) { //判断按下的是否是数字
//                System.out.println(actionCommand);

                //存在计算标识清除旧计算list与重置显示器
                if (actionFlage.equals("=")){
                    clickNumberArray.clear();
                    symbols.clear();
                    showJTextContent.setText("");
                    actionFlage = "!";
                }

                String text = showJTextContent.getText();
                showJTextContent.setText(text + actionCommand);

                //置按下标识
                isactionFlage = true;

            } else if ("+-*/".indexOf(actionCommand) != -1) {//判断计算符号
//                System.out.println(actionCommand);

                //判断是否按下数字防止按下运算符导致运算拼接出错
                if (!"".equals(showJTextContent.getText())) {

                    //记录计算的数字
                    clickNumberArray.add(showJTextContent.getText());
                    showJTextContent.setText("");

                    if (isactionFlage) {
                        //记录运算符
                        symbols.add(actionCommand);

                        //置按下标识
                        isactionFlage = false;
                    }
                }

            } else if ("=".equals(actionCommand)) {//判断是否是计算

                //添加最后一次计算数值
                clickNumberArray.add(showJTextContent.getText());

                //如果计算不存在就清除显示器,存在就计算
                if (actionFlage.equals("!")){
                    listToCalFormat(showJTextContent);
                }else {
                    clearShowJTextContent(showJTextContent);
                }

                //置计算标识
                actionFlage = "=";

                //置按下标识
                isactionFlage = false;
            }
        }

        public void listToCalFormat(JTextField showJTextContent) {

            //防止计算拼接出现错误
            if (clickNumberArray.size() - symbols.size() != 1) return;

            //修改计算标识
            actionFlage = "=";

            //取出第一个ArrayList中的数
            Double addAsSum = Double.valueOf(clickNumberArray.get(0)).doubleValue();
            String jSymbol = null;

            //使用symbols的数量遍历
            for (int i = 0; i < symbols.size();i++) {
                jSymbol = symbols.get(i);

                addAsSum = listToCal(addAsSum,Double.valueOf(clickNumberArray.get(i+1)).doubleValue(),jSymbol);
            }

            //判断是不是小数
            String result = Double.toString(addAsSum);
            if (result.endsWith(".0")){
                //取整显示计算结果
                showJTextContent.setText(String.valueOf(addAsSum.intValue()));
            }else {
                //不取整
                showJTextContent.setText(addAsSum.toString());
            }


            System.out.println(addAsSum+"----"+result);
        }

        public double listToCal(double n1,double n2,String symbol){
            double sum = 0;
            if (symbol.equals("+")){
                sum = n1 + n2;
            }else if (symbol.equals("-")){
                sum = n1 - n2;
            }else if (symbol.equals("*")){
                sum = n1 * n2;
            }else if (symbol.equals("/")){
                if (n2 < 0){
                    sum = 0;
                }else {
                    sum = n1 / n2;
                }
            }
            return sum;
        }

        /**
         * @param showJTextContent
         * @return void
         * @Description 初始化计算标识以及数组
         * @author xiaoqi
         * @Email 1846627226@qq.com
         * @date 2020-06-09 17:11
         */
        private void clearShowJTextContent(JTextField showJTextContent) {
            showJTextContent.setText("");
            actionFlage = "=";
            clickNumberArray.clear();
            symbols.clear();
            isactionFlage = false;
        }

    }

}

