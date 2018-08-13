/**
 * FileName: PageResult
 * Author:   coldwind
 * Date:     2018/8/5 21:35
 * Description: 传递分页数据
 * History:
 * <author>          <time>          <version>          <desc>
 */
package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈传递分页数据〉
 *
 * @author coldwind
 * @create 2018/8/5
 * @since 1.0.0
 */
public class PageResult implements Serializable {
    //总记录数
    private long total;
    //当前页结果
    private List rows;
    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
