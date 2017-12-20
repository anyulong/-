package cn.com.taiji;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.Emp;
import cn.com.taiji.domain.User;
import cn.com.taiji.dto.DepartmentDto;
import cn.com.taiji.dto.EmpDto;
import cn.com.taiji.service.DepartmentService;
import cn.com.taiji.service.EmpService;
import cn.com.taiji.until.ChineseToEnglish;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InitExcel {
	  @Autowired
	private  EmpService empService;
	  @Autowired
	  private DepartmentService deptService;
	  
   public static void main(String[] args) throws Exception {
   //   String path = "/home/chixue/下载/智慧云SBU人员通讯录0811.xlsx";
	     String path = "D:\\file\\通讯录.xlsx";
	     initDept(print(path));
     }
   
   @Test
	public void sendQA() throws org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException{
	   String path = "D:\\file\\通讯录.xlsx";
	   Map deptUserMap = print(path);
	   List<Emp> emps = (List<Emp>) deptUserMap.get("emps");
	   List<String> deptNames = (List<String>) deptUserMap.get("deptNames");
	   System.out.println(deptNames);
	   List<Department> depts = new ArrayList<Department>();
	  /* for (String departmentName : deptNames) {
		Department d = new Department();
		d.setDepartmentName(departmentName);
		depts.add(d);
	   }*/
	 /*  for (Department department : depts) {
		   deptService.updDepartment(entity2Dto(department));
	}*/
	   for (Emp emp : emps) {
		   empService.updEmp(entity2Dto(emp));
		  // System.out.println(entity2Dto(emp));
	}
	  
   }
 
  @Autowired
public static   Map  print(String path) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException{
	  File file = new File(path);
	    XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(3);

	    int rowstart = xssfSheet.getFirstRowNum();
	    int rowEnd = xssfSheet.getLastRowNum();
	    Map deptUserMap=new HashMap();
	    List allDept = new ArrayList();
	    List dept=new ArrayList();
	    List emps = new ArrayList();
	   // Department fatherDept=new Department();
	   // fatherDept.setDepartmentName("智慧城市与云服务SBU—云服务事业部");
	    for(int i=rowstart+1;i<=rowEnd;i++)
	    {
	    	dept=new ArrayList();
	    	Emp emp=new Emp();
	        XSSFRow row = xssfSheet.getRow(i);
	        if(null == row) continue;
	        int cellStart = row.getFirstCellNum();
	        int cellEnd = row.getLastCellNum();
	        String deptName="longda";
	        for(int k=cellStart;k<=cellEnd;k++)
	        {
	            XSSFCell cell = row.getCell(k);
	            if(null==cell) continue;
	            String str="";
	        
	            switch (cell.getCellType())
	            {
	                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
	                	str=cell.getNumericCellValue()+"";
	             //       System.out.print(cell.getNumericCellValue()      + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_STRING: // 字符串\
	                	str=cell.getStringCellValue()+"";
	                //    System.out.print(cell.getStringCellValue() + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
	                	str=cell.getBooleanCellValue()+"";
	                    System.out.println(cell.getBooleanCellValue()  + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_FORMULA: // 公式
	                	str=cell.getCellFormula()+"";
	                //    System.out.print(cell.getCellFormula() + "   ");
	                    break;
	                case HSSFCell.CELL_TYPE_BLANK: // 空值
	                //    System.out.println(" ");
	                    break;
	                case HSSFCell.CELL_TYPE_ERROR: // 故障
	                    System.out.println(" ");
	                    break;
	                default:
	                 //   System.out.print("未知类型   ");
	                    break;
	            }
	            
	           
	            if(k==0){
	            	emp.setEmpNumber(str);
	            	
	            }else  if(k==1){
	            	emp.setEmpName(str);
	            	
	            }else  if(k==2){
	            	emp.setGender(str);
	            	
	            }else  if(k==3){
	            	emp.setEmpType(str);
	            	
	            }else  if(k==4){
	            	emp.setEmpCity(str);
	            	
	            }else  if(k==9){
	            	emp.setPositionName(str);
	            	
	            }else  if(k==8){
	            	emp.setPositionLevel(str);
	            	
	            }else  if(k==7){
	            	emp.setPositionSequence(str);
	            	
	            }else  if(k==5) {
	            	
	            }else  if(k==6){
	            	Department d=new Department();
	            	d.setDepartmentName(str);
	            	dept.add(d);
	            	emp.setDepartments(dept);;
	            	allDept.add(str);
	            }
	            
	        }
	        emps.add(emp); 
	    }
	    
	    deptUserMap.put("emps",emps); 
	   
	    allDept=removeDuplicate(allDept);
	    deptUserMap.put("deptNames", allDept);
	    System.out.println(deptUserMap);
	    	return deptUserMap;
     }
/**
   * 去掉重复
   * @param list
   * @return
   */
  public static  List<String> removeDuplicate(List<String> list) {        
		 HashSet<String> h = new  HashSet<String>(list);        
		 list.clear();        
		 list.addAll(h);        
		 return list;     
  }  
  
  
  
  
  public static void initDept(Map deptUserMap){
      Set set = deptUserMap.entrySet();         
      Iterator item = set.iterator();         
      List<DepartmentDto> deptList=new ArrayList();
      //初始化一级目录菜单
      Map parentDept=new HashMap();
      
      while(item.hasNext()){      
           Map.Entry<String, List> entry1=(Map.Entry<String, List>)item.next();    
         
           String deptName=entry1.getKey();
           DepartmentDto dto=new DepartmentDto();
           dto.setDepartmentName(entry1.getKey());
           List list=entry1.getValue();
           Iterator it1 = list.iterator();
           while(it1.hasNext()){
              System.out.println(it1.next());
             // System.out.println(empService);
        	  // empService.updEmp(entity2Dto((Emp)it1.next()));
           }
      }  
  }
  private static EmpDto entity2Dto(Emp emp) {
		EmpDto empDto = new EmpDto();
		BeanUtils.copyProperties(emp,empDto);
		return empDto;
	}
  private static DepartmentDto entity2Dto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		//System.out.println(department);
		BeanUtils.copyProperties(department,departmentDto);
		return departmentDto;
	}
}