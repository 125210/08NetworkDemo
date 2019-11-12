/**
  * Copyright 2019 bejson.com 
  */
package com.example.networkdemo.Bean;

import java.io.Serializable;

/**
 * Auto-generated: 2019-11-07 9:6:11
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Ip implements Serializable {

    private int code;
    private IpData data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(IpData data) {
         this.data = data;
     }
     public IpData getData() {
         return data;
     }

}