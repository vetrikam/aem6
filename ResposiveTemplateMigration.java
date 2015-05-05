package com.pb.aem.migration.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pb.aem.migration.beans.ItemBean;
import com.pb.aem.migration.beans.RecordBean;
import com.pb.aem.migration.beans.ValueBean;
import com.pb.aem.migration.parser.ContentParser;


public class ResposiveTemplateMigration {
	
	public static void main(String a[])
	{
		try
		{
			//String filename = "C:\\Users\\5076559\\Desktop\\dcr_3_col.xml";
			String filename = "C:\\WIPRO\\Extract\\aem_migration\\migration\\data\\dcrs\\fedex\\index_Tab_3_responsive.xml";
		
			BufferedReader br = new BufferedReader(new FileReader("C:\\\\WIPRO\\\\Fedex\\\\list\\\\AEMPath.txt"));
			ArrayList mList = new ArrayList();
	
	        String lID = "";
	        if(br != null)
	        {
	            while ((lID = br.readLine()) != null)
	            {
	                mList.add(lID);
	            }
	        }

		ContentParser  contentParser = new ContentParser();
		FedexMigration fedexMigration = new FedexMigration();
		
		RecordBean recordBean = contentParser.parse(filename);
		
		// Iterate IW beans i.e. Record, Item and Values Beans
		if (null != recordBean) 
		{
			List<ItemBean> itemBeanLevelOneList = recordBean.getEditorialItemBeans();

			Iterator<ItemBean> itemLevelOneListIterator = itemBeanLevelOneList.iterator();
			
			while (itemLevelOneListIterator.hasNext()) 
			{
				ItemBean itemLevelOneBean = (ItemBean) itemLevelOneListIterator.next();
				List<ValueBean> valueBeanLevelOneList = itemLevelOneBean.getEditorialValueItemBean();
				
				System.out.println("itemLevelOneBean NAME::"+itemLevelOneBean.getName());
				System.out.println("itemLevelOneBean VALUE::"+itemLevelOneBean.getValue());
				if("Tabs".equals(itemLevelOneBean.getName()))
				{
					Iterator<ValueBean> valueLevelOneIterator = valueBeanLevelOneList.iterator();
					ArrayList<HashMap<String,String>> columnData = new ArrayList<HashMap<String,String>>();
					
	
					while (valueLevelOneIterator.hasNext()) 
					{
						ValueBean tabValueBean = (ValueBean) valueLevelOneIterator.next();
						System.out.println(" TAB VALUE BEAN :::::: "+tabValueBean.getName());
						if("Tab_Header".equals(tabValueBean.getName()))
						{
							System.out.println(tabValueBean.getName()+" :::::: "+tabValueBean.getValue());
							// get Tab Header title
							
						}
						else if("Tab_Item_Details".equals(tabValueBean.getName()))
						{
							List<ItemBean> tabItemList = tabValueBean.getEditorialItemBeans();
							
							Iterator<ItemBean> tabItemiterator = tabItemList.iterator();
							//System.out.println("TOTAL :::::::::::::::::::::::::::::::::::::::::::::"+tabItemList.size());
						
							while (tabItemiterator.hasNext()) 
							{
								ItemBean tabItemValue = tabItemiterator.next();
								//System.out.println("J :::::::::::::::::::::::::::::::::"+(j++));
								System.out.println("Tab_Item_Details :: tabItemValue :::"+tabItemValue.getName());
								fedexMigration.getColumnData(tabItemValue);
								
								//System.out.println("tabItemValue :::"+tabItemValue.getName());
								//System.out.println("tabItemValue :::"+tabItemValue.getValue());
								
							}//end while tabValueIterator
							
						}
					}//endwhile
					
					//CreateComponent cc = new CreateComponent();
					//cc.create3ColComponent(mList.get(0).toString(), columnData);

				}
				//System.out.println("-------------------------------------------------------");
			}//end while
		}//endif
		
		}
		catch(Exception e)
		{
			System.out.println("Exception ::"+e);
		}
	}//end main

}
