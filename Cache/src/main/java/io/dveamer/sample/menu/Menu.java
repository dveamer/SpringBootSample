package io.dveamer.sample.menu;

public class Menu {

    private final long id;
    private final String menuName;

    public Menu(long id, String content) {
        this.id = id;
        this.menuName = content;
    }

    public long getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return id == menu.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
