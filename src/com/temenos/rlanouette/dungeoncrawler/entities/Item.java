package com.temenos.rlanouette.dungeoncrawler.entities;

public class Item {
    private String name;
    private Action defeatingAction;
    private ItemType type;

    public Item(String name, Action action, ItemType type) {
        this.setName(name);
        this.setDefeatingAction(action);
        this.setType(type);
    }

    // Override the equals method so items can be compared by name
    @Override
    public boolean equals(Object o) {
        if((o instanceof Item) && (this.getType() == null) && this.getDefeatingAction() == null) {
            // The item to check only has a name, so compare just on that
            return this.name.equals(((Item) o).getName());
        }

        // Perform standard comparison
        return this == o;
    }

    //region Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Action getDefeatingAction() {
        return defeatingAction;
    }

    public void setDefeatingAction(Action defeatingAction) {
        this.defeatingAction = defeatingAction;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
    //endregion
}
