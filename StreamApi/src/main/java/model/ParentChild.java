package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-08    01:28
 * @description
 **/
@Data
@AllArgsConstructor
public class ParentChild {

    private int parentId;
    private int childId;

}
