package io.dveamer.sample.menu;

import java.net.URL;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("Menu4JavaServiceImpl")
public class Menu4JavaServiceImpl implements MenuService {

    Logger logger = LoggerFactory.getLogger(getClass());

    static CacheManager cacheManager;

    public Menu4JavaServiceImpl(){
        URL configFile = this.getClass().getResource("/ehcache.xml");
        cacheManager = new CacheManager(configFile);
    }

    @Override
    public List<Menu> retrieveMenus() throws Exception {

        List<Menu> menus = retrieveMenusOfMember();
        menus.addAll(retrieveMenusOfAdmin());
        menus = menus.stream().distinct().collect(Collectors.toList());

        return menus;
    }

    @Override
    public List<Menu> retrieveMenus(Auth auth) throws Exception {

        final Cache cache = cacheManager.getCache("menuCache");

        List<Menu> menus = null;
        try{
            Element element = cache.get(auth);
            if(element!=null){
                return (List<Menu>) element.getObjectValue();
            }
        }catch(Exception ex){
            logger.info(ex.getMessage());
        }

        logger.info("menu of {} is loading", auth);

        switch (auth) {
            case ADMIN : menus = retrieveMenusOfAdmin(); break;
            case MEMBER : menus = retrieveMenusOfMember(); break;
        }

        cache.put(new Element(auth, menus));

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
