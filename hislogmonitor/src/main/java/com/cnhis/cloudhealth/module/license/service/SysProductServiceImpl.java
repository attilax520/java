package com.cnhis.cloudhealth.module.license.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnhis.cloudhealth.common.utils.GsonUtils;
import com.cnhis.cloudhealth.module.license.dao.SysProductDao;
import com.cnhis.cloudhealth.module.license.entity.SysProduct;
import com.cnhis.cloudhealth.module.license.vo.ZtreeNode;


@Service
public class SysProductServiceImpl implements SysProductService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysProductDao sysProductDao;
	
	
	/**
	 * 获取当前医院授权产品
	 * @Title: getLicenseProduct
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public String getZNodes(Integer corporationId) {
		List<ZtreeNode> listTree = new ArrayList<ZtreeNode>();
		List<SysProduct> list = sysProductDao.findAll(corporationId);
		for (SysProduct product : list) {
			ZtreeNode ztree = new ZtreeNode();
			if(product.getParentid() == 0){
				ztree.setOpen(true);
				ztree.setParent(true);
			}
			ztree.setChecked(product.getIsChecked());
			ztree.setId(product.getId());
			ztree.setName(product.getProductName());
			ztree.setpId(product.getParentid());
			listTree.add(ztree);
		}
		
		return GsonUtils.toJson(listTree);
	}

	/**
	 * 获取当前医院已授权产品
	 * @Title: getLicenseProduct
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> getLicenseProduct(Integer corporationId) {
		List<SysProduct> productList = sysProductDao.findParent();
		for (SysProduct sysProduct : productList) {
			List<SysProduct> list = sysProductDao.getLicenseProduct(corporationId,sysProduct.getId());
			sysProduct.setProductList(list);
		}
		return productList;
	}

	/**
	 * @Title: findLicenseProduct   
	 * @Description: 根据医院code以及医院名称查询授权模块API
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	public List<SysProduct> findLicenseProduct(String code, String name) {
		return sysProductDao.findLicenseProduct(code, name);
	}

}
