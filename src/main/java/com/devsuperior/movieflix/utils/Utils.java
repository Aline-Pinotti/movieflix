package com.devsuperior.movieflix.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devsuperior.movieflix.projections.IdProjection;

public class Utils {

    public static <ID> List<? extends IdProjection<ID>> reorder(List<? extends IdProjection<ID>> ordered,
            List<? extends IdProjection<ID>> unordered) {
        
        Map<ID, IdProjection<ID>> map = new HashMap<>(); 
        for (IdProjection<ID> obj : unordered) {
            map.put(obj.getId(), obj);
        }
                       
        List<IdProjection<ID>> result = new ArrayList<>();
        for (IdProjection<ID> obj : ordered) {
            result.add(map.get(obj.getId()));
        }

        return result;
    }

}
