package com.example.demo.controller.webcontroller;

import com.example.demo.pojo.Blog;
import com.example.demo.pojo.Type;
import com.example.demo.service.BlogService;
import com.example.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: antigenMHC
 * @Date: 2020/2/24 9:28
 * @Version: 1.0
 **/
@Controller
public class TypesController {

    @Autowired
    TypeService typeService;
    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @PageableDefault(size = 4,
                        sort = {"updateTime"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        //偷个懒，只要数值足够大，我就可以足够显示2333
        List<Type> listType = typeService.getTop(10000);
        //点击导航栏跳转到该页面时，不知道第一个type的id是多少，因此传递一个-1
        if(id == -1 && listType.size()!=0){
            //默认显示第一个id下的所有blog
            id=listType.get(0).getId();
        }

        model.addAttribute("types", listType);
        //根据分页和id展示blog
        model.addAttribute("page", blogService.typeToBlog(pageable, id));
        //返回当前分类id，用于样式展示
        model.addAttribute("typeId", id);
        model.addAttribute("recommendBlog", blogService.getTop(4));
        return "typess";
    }


}
