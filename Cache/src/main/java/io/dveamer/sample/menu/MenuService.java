package io.dveamer.sample.menu;

import java.util.List;

public interface MenuService {

    List<Menu> retrieveMenus() throws Exception;

    List<Menu> retrieveMenus(Auth auth) throws Exception;

}
