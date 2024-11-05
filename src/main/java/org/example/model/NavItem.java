package org.example.model;

import java.util.List;

public class NavItem {
    private int id;
    private String name;
    private List<DropdownItem> dropdownItems;

    public NavItem() {}

    public NavItem(int id, String name, List<DropdownItem> dropdownItems) {
        this.id = id;
        this.name = name;
        this.dropdownItems = dropdownItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DropdownItem> getDropdownItems() {
        return dropdownItems;
    }

    public void setDropdownItems(List<DropdownItem> dropdownItems) {
        this.dropdownItems = dropdownItems;
    }
}
