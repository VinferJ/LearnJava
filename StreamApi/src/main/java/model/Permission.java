package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-08    01:27
 * @description
 **/
@Data
@AllArgsConstructor
public class Permission {

    private int id;
    private String path;
    private Set<Permission> children = new HashSet<>();

    public void addChildren(Permission permission){
        children.add(permission);
    }

    public Permission(int id, String path) {
        this.id = id;
        this.path = path;
    }
}
