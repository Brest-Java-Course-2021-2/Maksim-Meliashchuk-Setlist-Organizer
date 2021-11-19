package com.epam.brest.web_app.exception;

import com.epam.brest.dao.exception.NotUniqueException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private final Logger logger = LogManager.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleDataIntegrityViolationException(HttpServletRequest req, Exception e) {
        logger.error("Request: " + req.getRequestURL() + " raised " + e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(NotUniqueException.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("Request: " + req.getRequestURL() + " raised " + e);
        ModelAndView mav = new ModelAndView();
        System.out.println(e.getClass());
        mav.addObject("exception", e);
        mav.addObject("isNotUniqueException", true);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
