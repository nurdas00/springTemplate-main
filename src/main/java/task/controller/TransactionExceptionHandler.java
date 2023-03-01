package task.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import task.Exception.TransactionException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(value = TransactionException.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
