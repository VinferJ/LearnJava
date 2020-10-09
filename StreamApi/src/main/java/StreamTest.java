
import model.ParentChild;
import model.Permission;
import stream.*;
import utils.ListGenerateUtil;

import java.util.*;


/**
 * @author Vinfer
 * @date 2020-10-09    11:28
 * @description
 **/
public class StreamTest {

    public static void main(String[] args) {

        /*
        * Stream-流:
        *   特点：1.对集合、数组等类型的数据集，在不改变原数据源的情况下对数据源进行一系列的操作，产生新的流或其他结构的数据类型
        *        2.流是一个数据渠道，用于操作数据源所生成的元素序列，集合讲的数据，而流讲究的是处理、计算等
        *        3.流自身是不会储存元素的
        *        4.流不会改变源对象，相反，他们会返回一个持有新结果的新流
        *        5.流的操作是延迟执行的，这意味着他们会等到需要结果时才会执行
        *   好处：通过Stream API可以非常简单地对java数组或集合做类似于sql一样的过滤、排序、分片、属性抽取等等中间操作
        *        可以减轻sql操作的复杂度，将复杂的数据筛选工作转移到业务逻辑层实现，对数据库中的数据只查询最简单的数据集
        *
        * 两种类型的流：
        *   串行流：.stream()
        *   并行流：.parallelStream()（某种程度上可以替代jdk1.7中的ForkJoin）
        *
        *
        * */
        CreateStreamTest.createStream();
        FilterSliceStreamTest.filterAndSliceStream();
        MapStreamTest.mapStream();
        SortStreamTest.sortStream();
        EndStreamTest.endStream();

    }



    public static void initData(Map<Integer,Permission> permissionMap, List<ParentChild> parentChildList){
        /*
        * 生成权限树
        * 初始两个数据集：permissionList，parentChildList
        * 数据封装：将permissionList封装成permissionMap，key是pid
        * */
        for (int i = 1; i <= 18; i++) {
            permissionMap.put(i,new Permission(i, "/path" + i));
        }
        parentChildList = ListGenerateUtil.generateParentChildList();
    }

    public static Map<Integer, Permission> generatePermissionTable(){
        /*
        * TODO  用流生成权限树？
        * */
        return null;
    }



    public static Map<Integer, Permission> generatePermission(){
        Map<Integer,Permission> permissionMap = new HashMap<>(16);
        List<ParentChild> parentChildList = new ArrayList<>();
        initData(permissionMap,parentChildList);
        /*
        * 权限树生成：
        *   1. 遍历parentChildList
        *   2. 取出pid和cid，通过permissionMap和id，获取到对应的
        *      Permission对象：parent和children
        *   3. 对parent的Permission对象调用addChildren方法，添加子权限
        *   4. 移除非permissionMap中不是最顶级的权限，只保留根权限
        * 由于对象的特性，只要找到对应的父权限对象和子权限对象，那么调用addChildren方法时
        * 根据对象地址进行绑定，所以不管权限树结构如何复杂，都只需要执行下面的逻辑即可
        *
        * */
        for (ParentChild parentChild : parentChildList) {
            int pId = parentChild.getParentId();
            int cId = parentChild.getChildId();
            Permission parent = permissionMap.get(pId);
            Permission children = permissionMap.get(cId);
            parent.addChildren(children);
        }
        for (ParentChild parentChild : parentChildList) {
            permissionMap.remove(parentChild.getChildId());
        }
        return permissionMap;
    }



}
