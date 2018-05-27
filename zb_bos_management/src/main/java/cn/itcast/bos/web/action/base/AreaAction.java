package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {
    //模型驱动
    private Area area = new Area();

    @Autowired
    private AreaService areaService;
    @Override
    public Area getModel() {
        return area;
    }

    //接收上传文件
    private File file;


    public void setFile(File file) {
        this.file = file;
    }

    //批量数据导入
    @Action(value = "area_batchImport")
    public String batchImport() throws IOException {
        System.out.println(file);
        List<Area> areas = new ArrayList<>();

        //注入service

        //编写解析代码逻辑
        //基于.xls格式解析HSSF
        //1.加载Excel文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        //2.读取第一个sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //3.读取sheet中的每一行
        for (Row row : sheet
                ) {
            //一行数据对应一个区域对象
            if (row.getRowNum() == 0) {
                continue;//第一行跳过
            }
            //跳过空行
            if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                    continue;
            }
                Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            //将数据存入集合
            areas.add(area);
        }
        //调用业务层
        areaService.saveBatch(areas);
        return NONE;
    }
}
