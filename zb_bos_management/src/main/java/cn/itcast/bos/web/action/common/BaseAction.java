package cn.itcast.bos.web.action.common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽取Action的公共代码，简化开发
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    //模型驱动
    protected T model;
    @Override
    public T getModel() {
        return model;
    }

    //构造器完成模型驱动

    public BaseAction() {
        //构造子类Action对象
        //AreaAction extends BaseAction<Area>
        //BaseAction<Area>
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型中第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        try {
            model = modelClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("模型驱动失败...");
        }
    }

    //属性驱动
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //将分页查询结果数据压栈的方法
    protected void pushPageDataToValueStack(Page<T> pageData){
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());
        ActionContext.getContext().getValueStack().push(map);
        System.out.println(map);
    }
}
