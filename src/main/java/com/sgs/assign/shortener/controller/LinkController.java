package com.sgs.assign.shortener.controller;

import com.sgs.assign.shortener.*;
import com.sgs.assign.shortener.repository.LinkRepository;
import com.sgs.assign.shortener.vo.LinkVO;
import com.sgs.assign.shortener.others.Base62;
import com.sgs.assign.shortener.others.UseModelAndView;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class LinkController {


    private LinkRepository linkRepository;


    //main 페이지 가져오는 Thymeleaf 컨트롤러
    @GetMapping("/")
    public ModelAndView getShorten(@ModelAttribute LinkVO linkVO, UseModelAndView model, HttpServletRequest request) {

        return new ModelAndView("main");
    }


    //Shorten해주는 POST 컨트롤러
    @PostMapping("/")
    public @ResponseBody
    UseModelAndView test(@ModelAttribute LinkVO linkVO, UseModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.setViewName("main");

        long beforeTime = System.currentTimeMillis();
        String url = linkVO.getUrl();

        //URL 형식 오류 검출
        UrlValidator urlValidator = new UrlValidator();

        if (!urlValidator.isValid(url)) {
            LinkVO failedLink = new LinkVO(null, null);
            failedLink.setCodeAndMessage(400, "fail");
            model.addObject("link", failedLink);
            return model;
        }


        Link link = linkRepository.findByUrl(url);
        String encoded;

        //link가 null인 경우
        if (link == null) {
            Link saveLink = new Link(url, null);
            linkRepository.updateCacheAndSave(saveLink);
            link = linkRepository.findByUrl(url);
            encoded = new Base62().encoding(link.getId());
            link.setEncoded(encoded);
            linkRepository.save(link);
        } else {
            encoded = new Base62().encoding(link.getId());
        }


        String preUrl = "http://localhost/";
        LinkVO newLinkVO = new LinkVO(link.getUrl(), preUrl + new Base62().encoding(link.getId()));

//        Cookie myCookie = new Cookie("url", newLinkVO.getUrl());
//        myCookie.setMaxAge(1000000);
//        myCookie.setPath("/");
//
//        response.addCookie(myCookie);


        model.addObject("link", newLinkVO);

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
        System.out.println("시간차이(m) : " + secDiffTime);

        return model;
    }


    //리다익렉트 GET 컨트롤러
    @GetMapping("/{encoded}")
    public ModelAndView redirect(@PathVariable String encoded) {

        Link link = linkRepository.findByEncoded(encoded);
        String url = link.getUrl();

        return new ModelAndView("redirect:" + url);
    }
}
