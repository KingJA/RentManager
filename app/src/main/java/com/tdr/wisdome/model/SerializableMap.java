package com.tdr.wisdome.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Linus_Xie on 2016/8/21.
 */
public class SerializableMap implements Serializable {
    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
