/**
 * FileName: Result
 * Author:   coldwind
 * Date:     2018/8/7 14:38
 * Description: 返回信息
 * History:
 * <author>          <time>          <version>          <desc>
 */
package entity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈返回信息〉
 *
 * @author coldwind
 * @create 2018/8/7
 * @since 1.0.0
 */
public class Result {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
