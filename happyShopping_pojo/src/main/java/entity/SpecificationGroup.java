/**
 * FileName: SpecificationGroup
 * Author:   coldwind
 * Date:     2018/8/10 17:22
 * Description: 整合规格和规格选项的实体类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package entity;

import com.happyShopping.model.Specification;
import com.happyShopping.model.SpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈整合规格和规格选项的实体类〉
 *
 * @author coldwind
 * @create 2018/8/10
 * @since 1.0.0
 */
//要实现序列化接口,因为要传输
public class SpecificationGroup implements Serializable{
    private Specification specification;
    private List<SpecificationOption> specificationOptionList;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
