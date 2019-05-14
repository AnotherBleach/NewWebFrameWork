package cn.edu.bupt.bean;

import cn.edu.bupt.Initializer;
import cn.edu.bupt.util.ClassParser;
import cn.edu.bupt.util.HandlerMappingUtil;
import cn.edu.bupt.util.JSONUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DispatherServlet extends HttpServlet {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void init(ServletConfig config) throws ServletException {

        Initializer.init();

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  System.out.println(ClassParser.getClasses().size()+" <== total ");
        // 用户请求  方法+路径---》处理对象的对应方法
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        System.out.println(requestMethod + " " + requestPath);

        System.out.println(ClassParser.getControllerClassSet().size() + "  <==  controller");
        Request request = new Request();
        request.setMethod(requestMethod);
        request.setPath(requestPath);
        Handler handler = HandlerMappingUtil.getHandler(request);
        if (handler == null) {

            defaultHandler(req, resp);

        } else {
            // 解析查询数据
            Args args = new Args();
            Enumeration<String> reqParamNames = req.getParameterNames();
            Map<String, Object> params = new HashMap<>();
            while (reqParamNames.hasMoreElements()) {

                String reqParamName = reqParamNames.nextElement();
                String reqParamValue = req.getParameter(reqParamName);
                params.put(reqParamName, URLDecoder.decode(reqParamValue, "UTF8"));

            }


            args.setParams(params);
            handler.setArgs(args);

            /**
             *     修改调用handler invoke的逻辑
             *
             */

            AsyncContext asyncContext = req.startAsync();  // 开启异步支持
            CompletableFuture<Object> future = CompletableFuture.supplyAsync(handler::invoke, executorService);
            Future<Object> f = future.whenComplete((v, e) -> {
                System.out.println("MutiThread " + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
                String result2 = JSONUtil.toJson(v);
                renderResp((HttpServletResponse) asyncContext.getResponse(), result2);
                asyncContext.complete();
            });


        }


    }

    private void defaultHandler(HttpServletRequest req, HttpServletResponse resp) {

        Data data = new Data();
        data.setData("Empty Response");
        String result = JSONUtil.toJson(data);
        renderResp(resp, result);
    }

    private void renderResp(HttpServletResponse resp, String data) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write(data);
            resp.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // render

    }
}
