package io.dveamer.sample.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {

    @Resource(name="MenuServiceImpl")
    MenuService menuService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/menus/members", method = {RequestMethod.GET})
    public List<Menu> retrieveMenusOfMembers() throws Exception {

        return menuService.retrieveMenus(Auth.MEMBER);
    }

    @RequestMapping(value = "/menus/admin", method = {RequestMethod.GET})
    public List<Menu> retrieveMenusOfAdmin() throws Exception {

        return menuService.retrieveMenus(Auth.ADMIN);
    }

    @RequestMapping(value = "/menus", method = {RequestMethod.GET})
    public List<Menu> retrieveMenus() throws Exception {

        return menuService.retrieveMenus();
    }

}
