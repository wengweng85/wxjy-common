package com.insigma.common.excel;

import com.insigma.mvc.model.SysExcelBatch;
import com.insigma.resolver.AppException;

import java.util.List;


/**
 * excel״̬service
 * @author admin
 *
 */
public interface ExcelVS {
	
   public void executeListToTemp(List<String[]> list, SysExcelBatch sExcelBatch) throws AppException;
   public void executeProc(SysExcelBatch sExcelBatch)throws AppException;

}
