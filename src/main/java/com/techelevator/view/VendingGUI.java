package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingGUI {
    //making the window and panels
    static JFrame window = new JFrame();
    JPanel mainMenu = new JPanel();
    JPanel purchase = new JPanel();
    JPanel selectItem = new JPanel();
    JLabel cash = new JLabel();
    static JLabel cash2 = new JLabel();
    private boolean buttonMode = false;



    public VendingGUI() {
        window.setPreferredSize(new Dimension(1000, 750));

        window.add(mainMenu, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setTitle("Vendo-Matic 800");

        mainMenu.setLayout(new GridLayout(3, 1, 10, 10));
        mainMenu.setPreferredSize(new Dimension(1000, 750));
        mainMenu.setFocusable(true);
        mainMenu.addKeyListener(new MyKeyAdapter());
        mainMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        purchase.setLayout(new GridLayout(4, 1, 10, 10));
        purchase.setPreferredSize(new Dimension(1000, 750));
        purchase.setFocusable(true);
        purchase.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cash.setText("Current Money : $" + VendingMachineCLI.getCurrentMoney());
        cash.setHorizontalTextPosition(JLabel.CENTER);
        cash.setHorizontalAlignment(JLabel.CENTER);
        cash.setSize(new Dimension(250, 100));
        cash.setBorder(BorderFactory.createEtchedBorder());
        cash.setBackground(Color.DARK_GRAY);
        cash.setFont(new Font("Courier New", Font.BOLD, 20));

        purchase.add(cash);

        List<JButton> test = new ArrayList<>();
        VendingMachineCLI.getCurrentInventory().entrySet().forEach(e -> test.add(new JButton(e.getValue().getName()+": $" +e.getValue().getPrice()+ " "+ e.getKey())));
        test.forEach(button -> button.addActionListener(e->updateCash()));
        test.forEach(button -> button.addActionListener(e->Menu.selectItemGUI(button.getText().substring(button.getText().length()-2),VendingMachineCLI.getCurrentInventory(),window)));

        test.forEach(button -> selectItem.add(button));

        selectItem.setLayout(new GridLayout(5,4,15,15));
        JButton backButton = new JButton("Go Back");
        selectItem.add(backButton);
        selectItem.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setContentPane(purchase);
                window.invalidate();
                window.validate();
                cash.setText("Current Money : $" + VendingMachineCLI.getCurrentMoney());
            }
        });

        selectItem.add(cash2);
        cash2.setPreferredSize(new Dimension(500,75));
        cash2.setText("Current Money: $" + VendingMachineCLI.getCurrentMoney());
        cash2.setVerticalAlignment(JLabel.CENTER);
        cash2.setHorizontalAlignment(JLabel.TRAILING);
        cash2.setFont(new Font("Courier New", Font.BOLD, 17));


        //adding buttons and their uses
        JButton displayStuff = new JButton("Display Items");
        displayStuff.setFocusable(false);
        displayStuff.addActionListener(e -> JOptionPane.showMessageDialog(window, VendingMachineCLI.displayCurrentInventoryString(VendingMachineCLI.getCurrentInventory()),
                "Vendo-Matic 800 Inventory", JOptionPane.INFORMATION_MESSAGE));
        JButton purchaseMenuButton = new JButton("Purchase Menu");
        purchaseMenuButton.setFocusable(false);
        purchaseMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setContentPane(purchase);
                window.invalidate();
                window.validate();
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        JButton feedButton = new JButton("Feed Money");
        feedButton.setFocusable(false);
        feedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int passedMoney = Integer.parseInt(JOptionPane.showInputDialog(window,
                            "Please Enter Amount Of Money To Deposit", "Feed Money", JOptionPane.QUESTION_MESSAGE));
                    Menu.feedMoneyGUI(passedMoney, window);
                    Log.theftLog("Feed Money", new BigDecimal(passedMoney), VendingMachineCLI.getCurrentMoney());
                    System.out.println("Current Money: $" + VendingMachineCLI.getCurrentMoney());
                    cash.setText("Current Money : $" + VendingMachineCLI.getCurrentMoney());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(window, "Please enter a whole number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton selectButton = new JButton("Select Item");
        selectButton.setFocusable(false);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*String item = JOptionPane.showInputDialog(window, VendingMachineCLI.displayCurrentInventoryString(VendingMachineCLI.getCurrentInventory()),
                        "Please enter the Location", JOptionPane.QUESTION_MESSAGE);
                Menu.selectItemGUI(item, VendingMachineCLI.getCurrentInventory(), window);*/
                window.setContentPane(selectItem);
                window.invalidate();
                window.validate();


                cash.setText("Current Money : $" + VendingMachineCLI.getCurrentMoney());
            }
        });
        JButton finishedButton = new JButton("Finish Transaction");
        finishedButton.setFocusable(false);
        finishedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(window, Menu.remainingChange(VendingMachineCLI.getCurrentMoney()),
                        "Returned Change", JOptionPane.INFORMATION_MESSAGE);
                cash.setText("Current Money : $" + VendingMachineCLI.getCurrentMoney());
                window.setContentPane(mainMenu);
                window.invalidate();
                window.validate();
                mainMenu.requestFocusInWindow();


            }
        });

        //Adding buttons to panels
        mainMenu.add(displayStuff);
        mainMenu.add(purchaseMenuButton);
        mainMenu.add(exitButton);
        purchase.add(feedButton);
        purchase.add(selectButton);
        purchase.add(finishedButton);

        window.pack();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent key) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    window.dispose();
                    VendingMachineCLI.alternateRun();
                    break;
                case KeyEvent.VK_4:
                    Log.salesReport(VendingMachineCLI.getCurrentInventory());
                    break;
            }
        }
    }
    public static void updateCash(){
        cash2.setText("Current Money: $" + VendingMachineCLI.getCurrentMoney());
    }
}