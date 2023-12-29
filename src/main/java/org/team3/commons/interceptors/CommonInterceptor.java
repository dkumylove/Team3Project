package org.team3.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class CommonInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        checkDevice(request);

        return true;
    }

    /**
     * PC,MOBILE 수동 변경 처리
     *
     * // device - PC : PC 뷰, MOBILE : MOBILE 뷰
     * @param request
     */
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device");
        if(!StringUtils.hasText(device)) {
            return;
        }

        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        HttpSession session = request.getSession();
        session.setAttribute("device", device);

    }
}
