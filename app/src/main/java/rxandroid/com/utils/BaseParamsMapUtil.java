package rxandroid.com.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created on 2017/6/13.
 * Author by Aaron.Wang
 */

public class BaseParamsMapUtil {

    /**
     *  公共的参数集合
     * @param
     * @return
     */
    public static Map<String,String> getParamsMap(){
        Map<String,String> paramsMap = new LinkedHashMap<>() ;
        paramsMap.put("client_sys","android") ;
        paramsMap.put("aid","android1") ;
        paramsMap.put("time", System.currentTimeMillis()+"") ;
        return paramsMap ;
    }

}
