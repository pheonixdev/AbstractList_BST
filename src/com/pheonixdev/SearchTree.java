package com.pheonixdev;

/**
 * Created by SAB on 4/5/2018.
 */
public class SearchTree implements NodeList {

    private ListItem root = null;

    public SearchTree(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return this.root;
    }

    @Override
    public boolean addItem(ListItem newItem) {
        if(this.root == null){
            //tree was empty , so our items becomes the head of the tree
            this.root = newItem;
            return true;
        }
        //otherwise, start comparing from the head of the tree
        ListItem currentItem = this.root;
        while (currentItem != null){
            int comparison = currentItem.compareTo(newItem);
            if(comparison < 0){
                //newItem is greater, move right if possible
                if(currentItem.next() != null){
                    currentItem = currentItem.next();
                }
                else {
                    //there is no node to the right, so add at this point
                    currentItem.setNext(newItem);
                    return true;
                }
            }
            else if(comparison > 0){
                //newItem is less, move left if possible
                if(currentItem.previous() != null){
                    currentItem = currentItem.previous();
                }
                else{
                    // there is no node to the left, so add at this point
                    currentItem.setPrevious(newItem);
                    return true;
                }
            }
            else{
                //equal , so don't add
                System.out.println(newItem.getValue() + " is alrady present.");
                return false;
            }
        }
        //We can't actually get here, but Java complains if there is no return :P
        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {
        if(item != null){
            System.out.println("Deleting item " +item.getValue());
        }

        ListItem currentItem = this.root;
        ListItem parentItem = currentItem;

        while (currentItem != null){
            int comparison = currentItem.compareTo(item);
            if(comparison < 0){
                parentItem = currentItem;
                currentItem = currentItem.next();
            }
            else if(comparison > 0){
                parentItem = currentItem;
                currentItem = currentItem.previous();
            }
            else{ //equal, found the item to be removed
                performRemoval(currentItem,parentItem);
                return true;
            }
        }
        //Reached end of the list without finding the item to delete
        return false;
    }

    private void performRemoval(ListItem item, ListItem parent){
        //remove item from the tree
        if(item.next() == null){
            //no right tree, so make parent point to left tree(maybe null)
            if(parent.next() == item){
                //item is right child of it's parent
                parent.setNext(item.previous());
            }
            else if(parent.previous() == item){
                //item is left child of it's parent
                parent.setPrevious(item.previous());
            }
            else{
                //parent must be item,which means we were looking at root of tree
                this.root = item.previous();
            }
        }
        else if(item.previous() == null){
            //no left tree, so make parent point to right tree(maybe null)
            if(parent.next() == item){
                //item is right child of it's parent
                parent.setNext(item.next());
            }
            else if(parent.previous() == item){
                //item is left child of it's parent
                parent.setPrevious(item.next());
            }
            else{
                //again deleting the root
                this.root = item.next();
            }
        }
        else{
            //neither right nor left are null, deletion is a lot trickier!
            //From the right sub tree, find the smallest value(leftmost)
            ListItem current = item.next();
            ListItem leftmostParent = item;
            while (current.previous() != null){
                leftmostParent = current;
                current = current.previous();
            }
            //Now put the smallest value into our node to be deleted
            item.setValue(current.getValue());
            //and delete the smallest
            if(leftmostParent == item){
                //there was no leftmost node, so the current points to the smallest node(to be deleted)
                item.setNext(current.next());
            }
            else{
                //set the smallest node's parent to point to the smallest node's right child(maybe null)
                leftmostParent.setPrevious(current.next());
            }
        }
    }

    @Override
    public void traverse(ListItem root) {
        //recursive method
        if(root != null){
            traverse(root.previous());
            System.out.println(root.getValue());
            traverse(root.next());
        }

    }
}