package externalnet;

import java.util.List;

/**
 * @author vinfer
 * @date 2021-07-14 7:54
 */
public interface GenericSyncResult {

    /**
     * 房源是否在平台上线成功
     * @return      true-上线成功，false-上线失败
     */
    default boolean isOnlineSuccess(){
        return false;
    };

    /**
     * 房源是否同步中台成功
     * @return      true-同步成功，false-同步失败
     */
    boolean isSyncSuccess();

    /**
     * 获取房源状态
     * @return      房源状态
     */
    default Integer getHouseStatus(){
        return null;
    };

    /**
     * 获取同步失败原因
     * @return      同步失败原因
     */
    String getSyncFailReason();

    /**
     * 获取最后一次同步的http请求数据
     * @return      请求数据
     */
    Object getLastSyncHttpRequest();

    /**
     * 获取最后一次同步的http响应数据
     * @return      响应数据
     */
    Object getLastSyncHttpResponse();

}
