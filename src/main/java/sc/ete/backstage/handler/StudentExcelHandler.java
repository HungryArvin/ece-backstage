package sc.ete.backstage.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import sc.ete.backstage.entity.VO.StudentInfoVO;
import sc.ete.backstage.service.StudentInfoService;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：StudentExcelHandler
 * @date ：2022/3/20 23:35
 */
public class StudentExcelHandler extends AnalysisEventListener<StudentInfoVO> {
    private StudentInfoService studentInfoService;

    public StudentExcelHandler(StudentInfoService studentInfoService){
        this.studentInfoService = studentInfoService;
    }
    @Override
    public void invoke(StudentInfoVO data, AnalysisContext context) {
        studentInfoService.addStudentInfo(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
