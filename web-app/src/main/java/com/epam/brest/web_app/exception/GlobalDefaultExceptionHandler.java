package com.epam.brest.web_app.exception;

import com.epam.brest.ApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private final Logger logger = LogManager.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler({ApiException.class})
    public ModelAndView handleDApiException(HttpServletRequest req, Exception e) {
        logger.error("Request: " + req.getRequestURL() + " raised " + e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", "ApiException");
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", LocalDateTime.now());
        mav.addObject("stackTrace",e.getStackTrace());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleDataIntegrityViolationException(HttpServletRequest req, Exception e) {
        logger.error("Request: " + req.getRequestURL() + " raised " + e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", LocalDateTime.now());
        mav.addObject("stackTrace",e.getStackTrace());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

}
