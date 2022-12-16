package com.techelevator.view;

public enum MenuTextOptions {
    MAIN_MENU_DISPLAY_ITEMS("Display Vending Machine Items"),
    MAIN_MENU_OPTION_PURCHASE("Purchase"),
    MAIN_MENU_OPTION_EXIT("Exit"),
    MAIN_MENU_DISPLAY_SECRET("Secret"),
    PURCHASE_MENU_FEED_MONEY("Feed Money"),
    PURCHASE_MENU_SELECT_PRODUCT("Select Product"),
    PURCHASE_MENU_FINISH_TRANSACTION("Finish Transaction"),
    MAIN_MENU_GUI("GUI");
    final String text;
    MenuTextOptions(String text){this.text = text;}
    public static String[] mainMenu(){
        return new String[]{MAIN_MENU_DISPLAY_ITEMS.text, MAIN_MENU_OPTION_PURCHASE.text, MAIN_MENU_OPTION_EXIT.text, MAIN_MENU_DISPLAY_SECRET.text, MAIN_MENU_GUI.text};
    }
    public static String[] purchaseMenu(){
        return new String[]{PURCHASE_MENU_FEED_MONEY.text, PURCHASE_MENU_SELECT_PRODUCT.text, PURCHASE_MENU_FINISH_TRANSACTION.text};
    }
    public String getText(){
        return this.text;
    }
}
