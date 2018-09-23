package io.dveamer.sample.menu;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("MenuServiceImpl")
public class MenuServiceImpl implements MenuService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Menu> retrieveMenus() throws Exception {

        List<Menu> menus = retrieveMenus(Auth.MEMBER);
        menus.addAll(retrieveMenus(Auth.ADMIN));
        menus = menus.stream().distinct().collect(Collectors.toList());
        return menus;
    }

    @Override
    @Cacheable(value = "menuCache", key = "#auth")
    public List<Menu> retrieveMenus(Auth auth) throws Exception {

        logger.info("menu of {} is loading", auth);

        List<Menu> menus = null;

        switch (auth) {
            case ADMIN : menus = retrieveMenusOfAdmin(); break;
            case MEMBER : menus = retrieveMenusOfMember(); break;
        }

        return menus;
    }

    private List<Menu> retrieveMenusOfAdmin() throws Exception {

        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "menu00"));
        menus.add(new Menu(1, "menu01"));
        menus.add(new Menu(2, "menu02"));
        menus.add(new Menu(3, "menu03"));

        return menus;
    }

    private List<Menu> retrieveMenusOfMember() throws Exception {

        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "menu00"));
        menus.add(new Menu(1, "menu01"));

        return menus;
    }

}
