package com.pheonixdev;

/**
 * Created by SAB on 4/5/2018.
 */
public interface NodeList {
    ListItem getRoot();
    boolean addItem(ListItem item);
    boolean removeItem(ListItem item);
    void traverse(ListItem root);

}
