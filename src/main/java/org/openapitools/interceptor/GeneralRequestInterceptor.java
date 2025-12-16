package org.openapitools.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openapitools.exception.ExpectedException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.List;

/**
 * 通用请求拦截器，用于校验请求是否合法。执行顺序在 JwtRequestInterceptor 之前。
 */

@Component
public class GeneralRequestInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler) throws Exception {
    Boolean pass = false;

    // 如果是 POST 请求，那么 body 不应该为空，且应该是 JSON
    if ("POST".equals(request.getMethod())) {
      String contentType = request.getContentType();
      if (contentType != null
              && (contentType.contains("application/json") ||contentType.contains("multipart/form-data"))) {
        // 暂时没有校验 JSON 的合法性，因为 body 读取后无法再次被读取，
        // 如果在这里先保存完整 body，可能会占用比较多内存
        pass = true;
      }
    } else if("GET".equals(request.getMethod())){
      pass=true;
      Enumeration<String> parameterNames = request.getParameterNames();
      while (parameterNames.hasMoreElements()) {
        String paramName = parameterNames.nextElement();
        if(request.getParameter(paramName).isEmpty()){
          pass=false;
          break;
        }
      }
    }else {
      pass = true;
    }

    if (!pass) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"message\":\"请求不合法\"}");
      return false;
    }
    return true;
  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler,
      @Nullable Exception ex) throws Exception {
    if (ex != null) {
      // If is ExpectedException
      if (ex instanceof ExpectedException) {
        response.setStatus(((ExpectedException) ex).getStatusCode().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\":\"" + ex.getMessage() + "\"}");
      } else {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
