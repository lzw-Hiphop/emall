package com.lzw.emall.controller;

import com.lzw.emall.bean.PageBean;
import com.lzw.emall.bean.Product;
import com.lzw.emall.bean.extend.ProductExt;
import com.lzw.emall.service.CategoryService;
import com.lzw.emall.service.ProductService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/home")
    public String selectHotProduct(Model model,HttpServletRequest request) {

        model.addAttribute("category",categoryService.selectCategory());
        model.addAttribute("hotProduct",productService.selectHotProduct());
        model.addAttribute("newProduct",productService.selectNewProduct());
        return "home";
    }

    @RequestMapping("/search")
    public String search(HttpServletRequest request,Model model) throws Exception{
        //和solr服务器创建连接，参数：solr服务器的地址
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
        //创建一个query对象
        SolrQuery query = new SolrQuery();
        //获得查询内容
        String searchProduct = request.getParameter("searchProduct");
        //设置查询条件
        query.setQuery(searchProduct);
        //过滤条件
        query.setFilterQueries("category_name:*");
        //排序条件
        query.setSort("product_price", SolrQuery.ORDER.asc);
        //分页处理
        int pageSize = 8;//页面大小
        int pageIndex = 1;//默认是第一页
        query.setStart(0);
        query.setRows(pageSize);
        //设置默认搜索域
        query.set("df", "product_keywords");
        //高亮显示
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("product_name");
        //高亮显示的前缀
        query.setHighlightSimplePre("<span style='color: red'>");
        //高亮显示的后缀
        query.setHighlightSimplePost("</span>");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        List<ProductExt> products = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            //取高亮显示
            String productName = "";
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
            //判断是否有高亮内容
            if (null != list) {
                productName = list.get(0);
            } else {
                productName = (String) solrDocument.get("product_name");
            }
            ProductExt productExt = new ProductExt();
            productExt.setProductId(Integer.parseInt((String)solrDocument.get("id")));
            productExt.setProductName(productName);
            Object product_price = solrDocument.get("product_price");
            BigDecimal bigDecimal = new BigDecimal(product_price.toString());
            productExt.setProductPrice(bigDecimal);
            productExt.setProductInfo((String)solrDocument.get("product_info"));
            productExt.setProductImage((String)solrDocument.get("product_image"));
            productExt.setCategoryName((String)solrDocument.get("category_name"));
            products.add(productExt);
        }
        PageBean<ProductExt> pageUtil = new PageBean<>();
        pageUtil.setPageIndex(pageIndex);
        pageUtil.setPageCount((int)Math.ceil((double)solrDocumentList.getNumFound()/pageSize));
        pageUtil.setPageNumber((int)solrDocumentList.getNumFound());
        pageUtil.setPageSize(pageSize);
        pageUtil.setList(products);
        model.addAttribute("pageUtil",pageUtil);
        //查询内容
        model.addAttribute("searchProduct",searchProduct);
        return "search_result";
    }

    //solr搜索换页
    @RequestMapping("change_page")
    public String change_page(HttpServletRequest request,Model model) throws Exception{
        //和solr服务器创建连接，参数：solr服务器的地址
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
        //创建一个query对象
        SolrQuery query = new SolrQuery();
        //获得查询内容
        String searchProduct = new String(request.getParameter("searchProduct").getBytes("ISO-8859-1"),"UTF-8");
        //设置查询条件
        query.setQuery(searchProduct);
        //过滤条件
        query.setFilterQueries("category_name:*");
        //排序条件
        query.setSort("product_price", SolrQuery.ORDER.asc);
        //分页处理
        int pageSize = 8;//页面大小
        int pageIndex = 1;//默认是第一页
        if(request.getParameter("pageIndex")!=null){
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        query.setStart((pageIndex-1)*pageSize);
        query.setRows(pageSize);
        //设置默认搜索域
        query.set("df", "product_keywords");
        //高亮显示
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("product_name");
        //高亮显示的前缀
        query.setHighlightSimplePre("<span style='color: red'>");
        //高亮显示的后缀
        query.setHighlightSimplePost("</span>");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        List<ProductExt> products = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            //取高亮显示
            String productName = "";
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
            //判断是否有高亮内容
            if (null != list) {
                productName = list.get(0);
            } else {
                productName = (String) solrDocument.get("product_name");
            }
            ProductExt productExt = new ProductExt();
            productExt.setProductId(Integer.parseInt((String)solrDocument.get("id")));
            productExt.setProductName(productName);
            Object product_price = solrDocument.get("product_price");
            BigDecimal bigDecimal = new BigDecimal(product_price.toString());
            productExt.setProductPrice(bigDecimal);
            productExt.setProductInfo((String)solrDocument.get("product_info"));
            productExt.setProductImage((String)solrDocument.get("product_image"));
            productExt.setCategoryName((String)solrDocument.get("category_name"));
            products.add(productExt);
        }
        PageBean<ProductExt> pageUtil = new PageBean<>();
        pageUtil.setPageIndex(pageIndex);
        pageUtil.setPageCount((int)Math.ceil((double)solrDocumentList.getNumFound()/pageSize));
        pageUtil.setPageNumber((int)solrDocumentList.getNumFound());
        pageUtil.setPageSize(pageSize);
        pageUtil.setList(products);
        model.addAttribute("pageUtil",pageUtil);
        //查询内容
        model.addAttribute("searchProduct",searchProduct);
        return "search_result";
    }

    @RequestMapping("/product_by_category")
    public String product_by_category(HttpServletRequest request,Model model){
        //分页展示
        int cid = Integer.parseInt(request.getParameter("categoryid"));
        //当前页
        Integer pageIndex = 1;
        //每页记录条数
        int pageSize = 4;
        //总记录数
        int countIndex = productService.countIndex(cid);
        //总页数（向上取整）
        int pageCount = (int)Math.ceil((double)countIndex/pageSize);
        if(request.getParameter("pageIndex")!=null){
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        //index代表偏移量
        int index = (pageIndex-1)*pageSize;
        //当前页的数据
        List<Product> list = productService.showlist(cid,index,pageSize);
        PageBean<Product> pageUtil = new PageBean<>();
        pageUtil.setPageIndex(pageIndex);
        pageUtil.setPageCount(pageCount);
        pageUtil.setPageNumber(countIndex);
        pageUtil.setPageSize(pageSize);
        pageUtil.setList(list);
        model.addAttribute("pageUtil",pageUtil);
        //model.addAttribute("category",categoryService.selectCategory());
        return "product_by_category";
    }

    @RequestMapping("/product_detail")
    public String product_detail(HttpServletRequest request, HttpServletResponse response, Model model){
        //浏览历史放进cookie
        String pid = request.getParameter("productid");
        String pids = pid;
        Cookie[] cookies = request.getCookies();
        if(cookies.length!=0){
            for (Cookie cookie:cookies) {
                if("pids".equals(cookie.getName())){
                    String[] split = cookie.getValue().split("-");
                    List<String> strings = Arrays.asList(split);
                    LinkedList<String> list=new LinkedList<>(strings);
                    if(list.contains(pid)){
                        list.remove(pid);
                        list.addFirst(pid);
                    }else {
                        list.addFirst(pid);
                    }
                    //将集合【2，1，3】转成字符串2-1-3
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < list.size()&&i<6; i++) {
                        stringBuilder.append(list.get(i));
                        stringBuilder.append("-");
                    }
                    pids = stringBuilder.toString();
                }
            }
        }
        //将pid写到客户端的cookie
        Cookie cookie_pids=new Cookie("pids",pids);
        response.addCookie(cookie_pids);

        model.addAttribute("product",productService.product_detail(request));
        return "product_detail";
    }

}
